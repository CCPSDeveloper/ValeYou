package com.marius.valeyou_admin.ui.activity.signup;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.base.ApiResponse;
import com.marius.valeyou_admin.data.beans.base.SimpleApiResponse;
import com.marius.valeyou_admin.data.beans.singninbean.SignInModel;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.ActivitySignupBinding;
import com.marius.valeyou_admin.databinding.DialogDeleteAccouontBinding;
import com.marius.valeyou_admin.databinding.OtpBinding;
import com.marius.valeyou_admin.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.ProfileActivity;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;
import com.marius.valeyou_admin.ui.activity.signup.signuptwo.SignupTwoActivity;
import com.marius.valeyou_admin.ui.activity.signup.uploaddocument.UploadDocumentActivity;
import com.marius.valeyou_admin.util.Constants;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;
import com.marius.valeyou_admin.util.location.LiveLocationDetecter;
import com.marius.valeyou_admin.util.location.LocCallback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SignupActivity extends AppActivity<ActivitySignupBinding, SignupActivityVM> {


    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+.+[a-z]+";
    String name;
    String email;
    String first_name;
    String last_name;
    boolean social;
    Map<String, String> map;
    boolean isValid  = false;
    Location mCurrentlocation;
    String auth_key;

    @Override
    protected BindingActivity<SignupActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_signup, SignupActivityVM.class);
    }

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, SignupActivity.class);
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
    protected void subscribeToEvents(final SignupActivityVM vm) {
        binding.ccp.setCountryForNameCode("IN");
        binding.ccp.registerCarrierNumberEditText(binding.etPhone);

        binding.tvPolicyTerms.setMovementMethod(LinkMovementMethod.getInstance());

        Intent intent = getIntent();
        if (intent!=null){
            social = intent.getBooleanExtra("social",false);

            if (social) {

                name = intent.getStringExtra(Constants.NAME);
                email = intent.getStringExtra(Constants.EMAIL);
                binding.passwordLayout.setVisibility(View.GONE);
                binding.conpassLayout.setVisibility(View.GONE);

                if (!name.isEmpty() && name != null) {

                    String[] names = name.split(" ");
                    first_name = names[0];
                    last_name = names[1];

                    binding.etFirstName.setText(first_name);
                    binding.etLastname.setText(last_name);
                    if (email != null) {
                        binding.etEmail.setText(email);
                    }
                }

            }else{

                binding.passwordLayout.setVisibility(View.VISIBLE);
                binding.conpassLayout.setVisibility(View.VISIBLE);

            }
        }

        vm.base_back.observe(this, aVoid -> {
            finish(true);
        });

        vm.base_click.observe(this, view -> {

            switch (view != null ? view.getId() : 0) {
                case R.id.btnSignup:
                    if (social){
                        isValidFieldsForSocial(vm);
                    }else {
                        isValidFields(vm);
                    }
                    break;
            }
        });

        vm.sendOTPEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        vm.success.setValue(resource.message);
                        break;
                    case ERROR:
                        if (resource.message.equalsIgnoreCase("bad request")){
                            vm.error.setValue("Invalid OTP");
                        }else{
                            vm.error.setValue(resource.message);
                        }

                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });


        vm.verifyEmailEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        verificationDialog.dismiss();
                        vm.success.setValue(resource.message);

                        Intent intent = SignupTwoActivity.newIntent(SignupActivity.this);
                        intent.putExtra("auth_key",auth_key);
                        startNewActivity(intent,true);

                        break;
                    case ERROR:
                        if (resource.message.equalsIgnoreCase("bad request")){
                            vm.error.setValue("Invalid OTP");
                        }else{
                            vm.error.setValue(resource.message);
                        }
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

        vm.userBean.observe(this, new SingleRequestEvent.RequestObserver<SignInModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignInModel> resource) {
                switch (resource.status) {
                    case LOADING:
                       binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        auth_key = resource.data.getAuthKey();
                        Log.d("authkey : ",auth_key);
                        verifyDialog(resource.data.getEmail());
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        if (resource.message.equalsIgnoreCase("conflict")){
                            vm.error.setValue(resource.status+" : Email is already linked with another account");
                        }else {
                            vm.error.setValue(resource.status+" : "+resource.message);
                        }
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
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
                            locCallback.getApiException().startResolutionForResult(SignupActivity.this, LiveLocationDetecter.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case PROMPT_CANCEL:
                        viewModel.error.setValue("prompt cancel");
                        break;
                    case FOUND:
                        mCurrentlocation = locCallback.getLocation();
                        vm.liveLocationDetecter.removeLocationUpdates();
                        break;
                }

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void isValidFieldsForSocial(SignupActivityVM viewModel){
       String firstName = binding.etFirstName.getText().toString();

       String lastNAme = binding.etLastname.getText().toString();
       String email = binding.etEmail.getText().toString();
       String phone = binding.etPhone.getText().toString();
       String password = binding.etPassword.getText().toString();
       String confirmPassword = binding.etCnfPassword.getText().toString();

       if (firstName.isEmpty()){
            viewModel.error.setValue("Please enter first name");
       }else if (lastNAme.isEmpty()){
           viewModel.error.setValue("Please enter last name");
       } else if (email.isEmpty()) {
           viewModel.error.setValue("Please enter email");
       }else if (!email.matches(emailPattern)) {
           viewModel.error.setValue("Please enter valid email");
       }else if (phone.isEmpty()){
           viewModel.error.setValue("Please enter phone number");
       }else if (!binding.cvPolicyTerms.isChecked()){
           viewModel.error.setValue("Please accepts privacy policy");
       }else {

           map = new HashMap<>();
           map.put(Constants.FIRST_NAME,firstName);
           map.put(Constants.LAST_NAME,lastNAme);
           map.put(Constants.PHONE,phone);
           map.put(Constants.EMAIL,email);
           map.put(Constants.DEVICETYPE, "1");
           map.put(Constants.DEVICE_TOKEN,sharedPref.get(Constants.FCM_TOKEN, "xyz"));
           if (mCurrentlocation != null) {

               String latitude = String.valueOf(mCurrentlocation.getLatitude());
               String longitude = String.valueOf(mCurrentlocation.getLongitude());
               map.put(Constants.LATITUDE,latitude );
               map.put(Constants.LONGITUDE, longitude);

           } else {
               map.put(Constants.LATITUDE, "30.7046");
               map.put(Constants.LONGITUDE,"76.7179");
           }


           String socialId = sharedPref.get(Constants.SOCIAL_ID,"");

           if (!socialId.equals("")){

               map.put(Constants.SOCIAL_ID, socialId);
               viewModel.signupApi(map);

           }

       }
    }
    private void isValidFields(SignupActivityVM viewModel){
        String firstName = binding.etFirstName.getText().toString().trim();
        String lastNAme = binding.etLastname.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String confirmPassword = binding.etCnfPassword.getText().toString().trim();

        if (firstName.isEmpty()){
            viewModel.error.setValue("Please enter first name");
        }else if (lastNAme.isEmpty()){
            viewModel.error.setValue("Please enter last name");
        } else if (email.isEmpty()) {
            viewModel.error.setValue("Please enter email");
        }else if (!email.matches(emailPattern)) {
            viewModel.error.setValue("Please enter valid email");
        }else if (phone.isEmpty()){
            viewModel.error.setValue("Please enter phone number");
        }else if (password.isEmpty()){
            viewModel.error.setValue("Please enter password");
        }else if (password.length()<6){
            viewModel.error.setValue("Password must be of atleast 6 character");
        }else if (!password.matches(confirmPassword)){
            viewModel.error.setValue("Both password must be matched");
        }else if (!binding.cvPolicyTerms.isChecked()){
            viewModel.error.setValue("Please accepts privacy policy");
        }else {

            map = new HashMap<>();
            map.put(Constants.FIRST_NAME,firstName);
            map.put(Constants.LAST_NAME,lastNAme);
            map.put(Constants.PHONE,phone);
            map.put(Constants.EMAIL,email);
            map.put(Constants.PASSWORD,password);
            map.put(Constants.DEVICETYPE, "1");
            map.put(Constants.DEVICE_TOKEN,sharedPref.get(Constants.FCM_TOKEN, "xyz"));
            if (mCurrentlocation != null) {

                String latitude = String.valueOf(mCurrentlocation.getLatitude());
                String longitude = String.valueOf(mCurrentlocation.getLongitude());
                map.put(Constants.LATITUDE,latitude );
                map.put(Constants.LONGITUDE, longitude);

            } else {
                map.put(Constants.LATITUDE, "30.7046");
                map.put(Constants.LONGITUDE,"76.7179");
            }


            viewModel.signupApi(map);

        }

    }

    private BaseCustomDialog<OtpBinding> verificationDialog;
    private void verifyDialog(String email) {
        verificationDialog = new BaseCustomDialog<>(SignupActivity.this, R.layout.otp, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {

                        case R.id.iv_cancel:
                            verificationDialog.dismiss();
                            Intent intent = LoginActivity.newIntent(SignupActivity.this);
                            startNewActivity(intent,true);
                            finishAffinity();
                            break;
                        case R.id.btn_submit:
                            if (!verificationDialog.getBinding().etOtp.getText().toString().isEmpty()){
                                viewModel.verifyEmail(email,verificationDialog.getBinding().etOtp.getText().toString().trim());
                            }else{
                                viewModel.error.setValue("Enter OTP");
                            }
                            break;

                        case R.id.resend_otp:
                            if (!email.isEmpty()){
                                viewModel.sendOTP(email);
                            }else{
                                viewModel.error.setValue("Something went wrong");
                            }
                            break;

                    }
                }
            }
        });
        verificationDialog.getBinding().tvTwo.setText("Enter the OTP you recieve to \n"+email);
        verificationDialog.setCancelable(true);
        verificationDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        verificationDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        verificationDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        verificationDialog.show();

    }



}
