package com.marius.valeyou.ui.fragment.more.privacy_policy;

import android.os.Bundle;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.PrivacyResponseBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityPrivacyPolicyBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PrivacyPolicyFragment extends AppFragment<ActivityPrivacyPolicyBinding, PrivacyPolicyFragmentVM> {
    private static final String TITLE = "title";
    private static final String TYPE = "type";
    private MainActivity mainActivity;

    public static Fragment newInstance(String title, int type) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putInt(TYPE, type);
        PrivacyPolicyFragment privacyPolicyFragment = new PrivacyPolicyFragment();
        ;
        privacyPolicyFragment.setArguments(bundle);
        return privacyPolicyFragment;
    }

    @Override
    protected BindingFragment<PrivacyPolicyFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.activity_privacy_policy, PrivacyPolicyFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(PrivacyPolicyFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader(getArguments().getString(TITLE));
        binding.setTitle(getArguments().getString(TITLE));
        vm.getDetails(getArguments().getInt(TYPE));
        vm.requestEvent.observe(this, new SingleRequestEvent.RequestObserver<PrivacyResponseBean>() {
            @Override
            public void onRequestReceived(@NonNull Resource<PrivacyResponseBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        if (getArguments().getInt(TYPE) == 1) {
                            binding.setDescription(resource.data.getTerm());
                        } else {
                            binding.setDescription(resource.data.getPrivacy());
                        }
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });
    }
}
