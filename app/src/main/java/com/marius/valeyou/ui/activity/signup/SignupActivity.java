package com.marius.valeyou.ui.activity.signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.reqbean.SocialDataBean;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivitySignupBinding;
import com.marius.valeyou.databinding.HolderNgoItemBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SignupActivity extends AppActivity<ActivitySignupBinding, SignupActivityVM> {

    private static final String SOCIAL_BEAN = "bean";
    private SocialDataBean socialDataBean;
    private String social_id = "";
    private String social_type = "";

    @Override
    protected BindingActivity<SignupActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_signup, SignupActivityVM.class);
    }

    public static Intent newIntent(Activity activity, SocialDataBean dataBean) {
        Intent intent = new Intent(activity, SignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(SOCIAL_BEAN, dataBean);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        );
    }

    @Override
    protected void subscribeToEvents(final SignupActivityVM vm) {
        socialDataBean = getIntent().getParcelableExtra(SOCIAL_BEAN);
        binding.tvTerms.setMovementMethod(LinkMovementMethod.getInstance());
        vm.base_back.observe(this, aVoid -> {
            finish(true);
        });

        if (socialDataBean != null) {
            social_id = socialDataBean.getSocial_id();
            social_type = socialDataBean.getSocial_type();
            setValueOnView();
        }

        vm.base_click.observe(this, view -> {
            if (view == null)
                return;
            switch (view != null ? view.getId() : 0) {
                case R.id.btnSignup:
                    /*switch (ThemeUtils.getInstance(SignupActivity.this).getCurrentTheme()) {
                        case BLUE:
                            changeAppTheme(ThemeUtils.Theme.YELLOW);
                            break;
                        case YELLOW:
                            changeAppTheme(ThemeUtils.Theme.BLUE);
                            break;
                    }*/
                    signup();
                    break;
                case R.id.et_ngo:
                    binding.setType(true);
                    break;
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
                        Intent intent1 = MainActivity.newIntent(SignupActivity.this,"signup");
                        startNewActivity(intent1, true, true);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });
        setNgoRecycler();
    }

    private void setValueOnView() {
        binding.etFirstName.setText(socialDataBean.getFirst_name());
        binding.etLastName.setText(socialDataBean.getLast_name());
        binding.etEmail.setText(socialDataBean.getEmail());
    }

    private void setNgoRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvNgo.setLayoutManager(layoutManager);
        binding.rvNgo.setAdapter(adapter);
        adapter.setList(getData());
    }

    private List<MoreBean> getData() {
        List<MoreBean> moreBeans = new ArrayList<>();
        moreBeans.add(new MoreBean(1, "Yes", 1));
        moreBeans.add(new MoreBean(1, "No", 0));
        return moreBeans;
    }

    SimpleRecyclerViewAdapter<MoreBean, HolderNgoItemBinding> adapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_ngo_item, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
                @Override
                public void onItemClick(View v, MoreBean bean) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.cv_item:
                            ngo = bean.getImage();
                            binding.etNgo.setText(bean.getName());
                            binding.setType(false);
                            break;
                    }
                }
            });

    private int ngo = 1;

    private void signup() {
        if (isEmptyField()) {
            if (binding.cbTerms.isChecked()) {
                Map<String, String> map = new HashMap<>();
                map.put("first_name", binding.etFirstName.getText().toString());
                map.put("last_name", binding.etLastName.getText().toString());
                map.put("email", binding.etEmail.getText().toString());
                map.put("phone", binding.etPhone.getText().toString());
                map.put("password", binding.etPassword.getText().toString());
                map.put("country_code", "+91");
                map.put("address", "");
                map.put("latitude", sharedPref.get(Constants.LATITUDE,""));
                map.put("longitude", sharedPref.get(Constants.LONGITUDE,""));
                map.put("social_id", social_id);
                map.put("social_type", social_type);
                map.put("device_token ", sharedPref.get(Constants.FCM_TOKEN, "abc"));
                viewModel.signup(map, 1, ngo);
            } else {
                viewModel.error.setValue("Please accept or terms and conditions.");
            }
        }
    }

    private boolean isEmptyField() {
        if (TextUtils.isEmpty(binding.etFirstName.getText().toString().trim())) {
            viewModel.error.setValue("Enter First Name");
            return false;
        }
        if (TextUtils.isEmpty(binding.etLastName.getText().toString().trim())) {
            viewModel.error.setValue("Enter Last Name");
            return false;
        }
        if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
            viewModel.error.setValue("Enter Email");
            return false;
        }

        if (!isValidEmail(binding.etEmail.getText().toString())) {
            viewModel.error.setValue("Enter valid Email");
            return false;
        }

        if (TextUtils.isEmpty(binding.etPhone.getText().toString().trim())) {
            viewModel.error.setValue("Enter Phone Number");
            return false;
        }
        if (social_id.equalsIgnoreCase("")) {
            if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
                viewModel.error.setValue("Enter Password");
                return false;
            }

            if (binding.etPassword.getText().toString().trim().length() < 6) {
                viewModel.error.setValue("You must have to enter atleast 6 characters");
                return false;
            }

            if (!binding.etCnfPassword.getText().toString().trim().matches(binding.etPassword.getText().toString().trim())) {
                viewModel.error.setValue("both password must be matched");
                return false;
            }
        }

        if (!binding.cbTerms.isChecked()) {
            viewModel.error.setValue("Please accept policy and terms");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        Intent intent = LoginActivity.newIntent(SignupActivity.this);
        startNewActivity(intent, true, true);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
