package com.marius.valeyou.ui.fragment.more.helpnsupport;

import android.content.Intent;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.FragmentHelpSupportBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.more.helpnsupport.faq.FAQActivity;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class HelpAndSupportFragment extends AppFragment<FragmentHelpSupportBinding,HelpAndSupportFragmentVM> {
    private MainActivity mainActivity;

    public static Fragment newInstance() {
        return new HelpAndSupportFragment();
    }

    @Override
    protected BindingFragment<HelpAndSupportFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_help_support,HelpAndSupportFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(HelpAndSupportFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader("Help and Support");
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                Intent intent ;
                switch (view.getId()){
                    case R.id.cv_faq:
                        intent = FAQActivity.newIntent(getActivity());
                        startNewActivity(intent);
                        break;
                }
            }
        });
    }
}
