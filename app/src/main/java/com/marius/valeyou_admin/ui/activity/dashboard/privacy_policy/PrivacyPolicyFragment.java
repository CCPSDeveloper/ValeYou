package com.marius.valeyou_admin.ui.activity.dashboard.privacy_policy;

import android.content.Intent;
import android.os.Bundle;

import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.allcontent.AboutModel;
import com.marius.valeyou_admin.data.beans.allcontent.PrivacyModel;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.ActivityPrivacyPolicyBinding;
import com.marius.valeyou_admin.di.base.view.AppFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.ProfileActivity;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;
import com.marius.valeyou_admin.ui.activity.main.MainActivity;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class PrivacyPolicyFragment extends AppFragment<ActivityPrivacyPolicyBinding, PrivacyPolicyFragmentVM> {
    public static final String TAG = "PrivacyPolicyFragment";

    public static Fragment newInstance() {
        return new PrivacyPolicyFragment();
    }

    @Override
    protected BindingFragment<PrivacyPolicyFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.activity_privacy_policy, PrivacyPolicyFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(PrivacyPolicyFragmentVM vm) {


        vm.privacyApi(2);

        vm.privacyBean.observe(this, new Observer<Resource<PrivacyModel>>() {
            @Override
            public void onChanged(Resource<PrivacyModel> aboutModelResource) {
                switch (aboutModelResource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        String privacyStr = aboutModelResource.data.getPrivacy();
                        webViewContent(privacyStr);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(aboutModelResource.message);
                        if (aboutModelResource.message.equalsIgnoreCase("unauthorised")){
                            Intent intent1 = LoginActivity.newIntent(getActivity());
                            startNewActivity(intent1, true, true);

                        }
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(aboutModelResource.message);
                        break;
                }
            }
        });

    }

    public void webViewContent(String privacy){
        binding.wvLoad.requestFocus();
        binding.wvLoad.getSettings().setLightTouchEnabled(true);
        binding.wvLoad.getSettings().setJavaScriptEnabled(true);
        binding.wvLoad.getSettings().setGeolocationEnabled(true);
        binding.wvLoad.setSoundEffectsEnabled(true);
        binding.wvLoad.loadData(privacy,
                "text/html", "UTF-8");
    }
}
