package com.marius.valeyou.ui.activity.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.reqbean.SocialDataBean;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityLoginBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.forgot.ForgotPasswordActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.signup.SignupActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.location.LocCallback;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppActivity<ActivityLoginBinding, LoginActivityVM> {

    private static final int RC_SIGN_IN = 1;
    private Location mCurrentlocation;
    @Inject
    LiveLocationDetecter liveLocationDetecter;

    private GoogleSignInClient mGoogleSignInClient;
    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    private SocialDataBean socialDataBean;

    @Override
    protected BindingActivity<LoginActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_login, LoginActivityVM.class);
    }

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    @Override
    protected void subscribeToEvents(final LoginActivityVM vm) {
        callbackManager = CallbackManager.Factory.create();
        binding.loginButton.setReadPermissions(Arrays.asList(EMAIL));
        facebooklogin();
        vm.base_back.observe(this, aVoid -> {
            finish(true);
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        sharedPref.put(Constants.FCM_TOKEN, task.getResult().getToken());
                    }
                });

        vm.base_click.observe(this, view -> {
            if (view == null)
                return;
            switch (view != null ? view.getId() : 0) {
                case R.id.btn_login:
                    if (isEmptyValue()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("email", binding.etEmail.getText().toString());
                        map.put("password", binding.etPassword.getText().toString());
                        map.put("device_type", "1");
                        map.put("device_token", sharedPref.get(Constants.FCM_TOKEN, "abc"));
                        if (mCurrentlocation != null) {
                            map.put("latitude", String.valueOf(mCurrentlocation.getLatitude()));
                            map.put("longitude", String.valueOf(mCurrentlocation.getLongitude()));
                        } else {
                            map.put("latitude", "30.7046");
                            map.put("longitude", "76.7179");
                        }
                        vm.signIn(map);
                    }
                    break;
                case R.id.tv_signup:
                    socialDataBean = null;
                    Intent intent = SignupActivity.newIntent(LoginActivity.this, socialDataBean);
                    startNewActivity(intent, false, true);
                    break;

                case R.id.tv_forgot:
                    Intent intent2 = ForgotPasswordActivity.newIntent(LoginActivity.this);
                    startNewActivity(intent2, false, true);
                    break;

                case R.id.cv_facebook:
                    binding.loginButton.performClick();
                    break;

                case R.id.cv_google:
                    signInGoogle();
                    break;
            }
        });

        vm.liveLocationDetecter.observe(this, new Observer<LocCallback>() {
            @Override
            public void onChanged(LocCallback locCallback) {
                switch (locCallback.getType()) {
                    case STARTED:
                        break;
                    case ERROR:
                        break;
                    case STOPPED:
                        break;
                    case OPEN_GPS:
                        viewModel.info.setValue("Prompt open");
                        try {
                            locCallback.getApiException().startResolutionForResult(LoginActivity.this, LiveLocationDetecter.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case PROMPT_CANCEL:
                        viewModel.error.setValue("prompt cancel");
                        break;
                    case FOUND:
                        mCurrentlocation = locCallback.getLocation();
                        sharedPref.put(Constants.LATITUDE, String.valueOf(mCurrentlocation.getLatitude()));
                        sharedPref.put(Constants.LONGITUDE, String.valueOf(mCurrentlocation.getLongitude()));
                        vm.liveLocationDetecter.removeLocationUpdates();
                        break;
                }

            }
        });

        vm.userBean.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        Intent intent1 = MainActivity.newIntent(LoginActivity.this,"login");
                        startNewActivity(intent1, true, true);
                        break;
                    case ERROR:
                        binding.setCheck(false);

                        if (resource.message.equalsIgnoreCase("bad request")) {
                            vm.error.setValue("Incorrect Email and Password");
                        }

                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

        vm.socialBean.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        Intent intent1;
                        if (resource.data != null && resource.data.getStatus() == 1) {
                            intent1 = MainActivity.newIntent(LoginActivity.this,"login");
                            startNewActivity(intent1, true, true);
                        } else {
                            intent1 = SignupActivity.newIntent(LoginActivity.this, socialDataBean);
                            startNewActivity(intent1);
                        }
                        break;
                    case ERROR:
                        binding.setCheck(false);

                        if (resource.message.equalsIgnoreCase("bad request")) {
                            vm.error.setValue("Incorrect Email and Password");
                        }

                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

        googleSignIn();

    }

    private void facebooklogin() {
        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                getUserProfile(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                            /*viewModel.success.setValue("Success::" + id + "\n" + first_name + " " + last_name
                                    + "\n" + email + "\n" + image_url);*/
                            socialDataBean = new SocialDataBean(id, first_name, last_name, email, "1");
                            Map<String, String> data = new HashMap<>();
                            data.put("first_name", first_name);
                            data.put("last_name", last_name);
                            data.put("email", email);
                            data.put("phone", "");
                            data.put("country_code", "");
                            data.put("address", "");
                            data.put("latitude", String.valueOf(mCurrentlocation.getLatitude()));
                            data.put("longitude", String.valueOf(mCurrentlocation.getLongitude()));
                            data.put("device_token", sharedPref.get(Constants.FCM_TOKEN, "abc"));
                            viewModel.socialLogin(id, "1", 1, data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private boolean isEmptyValue() {
        if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
            viewModel.error.setValue("Enter email");
            return false;
        } else if (!isValidEmail(binding.etEmail.getText().toString())) {
            viewModel.error.setValue("Enter valid Email");
            return false;
        } else if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
            viewModel.error.setValue("Enter Password");
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                viewModel.liveLocationDetecter.startLocationUpdates();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        liveLocationDetecter.onActivityResult(requestCode, resultCode);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("TAG", "onError : " + e.getMessage());
        }
    }

    private void updateUI(GoogleSignInAccount account) {
      /*  viewModel.success.setValue("Success::" + account.getId() + "\n" + account.getDisplayName()
                + "\n" + account.getEmail() + "\n" + account.getPhotoUrl());*/
        socialDataBean = new SocialDataBean(account.getId(), account.getDisplayName(), account.getFamilyName(), account.getEmail(), "2");
        Map<String, String> data = new HashMap<>();
        data.put("first_name", account.getDisplayName());
        data.put("last_name", account.getFamilyName());
        data.put("email", account.getEmail());
        data.put("phone", "");
        data.put("country_code", "");
        data.put("address", "");
        data.put("latitude", String.valueOf(mCurrentlocation.getLatitude()));
        data.put("longitude", String.valueOf(mCurrentlocation.getLongitude()));
        data.put("device_token", sharedPref.get(Constants.FCM_TOKEN, "abc"));
        viewModel.socialLogin(account.getId(), "2", 1, data);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}