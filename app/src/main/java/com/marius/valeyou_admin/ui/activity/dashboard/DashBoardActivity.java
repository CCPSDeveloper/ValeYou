package com.marius.valeyou_admin.ui.activity.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;

import com.marius.valeyou_admin.BR;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.base.MoreBean;
import com.marius.valeyou_admin.data.local.SharedPref;
import com.marius.valeyou_admin.databinding.ActivityDashboardBinding;
import com.marius.valeyou_admin.databinding.HolderMoreBinding;
import com.marius.valeyou_admin.databinding.HolderSettingsBinding;
import com.marius.valeyou_admin.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.aboutus.AboutUsFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.changepassword.ChangePasswordFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.helpnsupport.HelpAndSupportFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.jobhistory.JobHistoryFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.privacy_policy.PrivacyPolicyFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.ProfileActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.review.ReviewFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.review.sendreview.SendReviewActivity;
import com.marius.valeyou_admin.ui.activity.notification.NotificationActivity;
import com.marius.valeyou_admin.ui.fragment.message.ChatListFragment;
import com.marius.valeyou_admin.ui.fragment.myjob.MyJobFragment;
import com.marius.valeyou_admin.util.misc.BackStackManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashBoardActivity extends AppActivity<ActivityDashboardBinding, DashBoardActivityVM> {

    @Inject
    SharedPref sharedPref;
    private boolean exit = false;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<DashBoardActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_dashboard, DashBoardActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(DashBoardActivityVM vm) {
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                binding.drawerViw.openDrawer(Gravity.LEFT);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {
                    case R.id.img_notification:
                        if (binding.header.imgNotification.getTag().toString().equalsIgnoreCase("notification")) {
                            changeFragment(NotificationActivity.TAG);
                        } else {
                            Intent intent = SendReviewActivity.newIntent(DashBoardActivity.this);
                            startNewActivity(intent);
                        }
                        break;
                    case R.id.image_close:
                        binding.navView.setCheck(false);
                        break;
                }
            }
        });
        setNavRecyclerView();
        //changeFragment(BackStackManager.getInstance(this).getCurrentTab());
        changeFragment(MyJobFragment.TAG);
    }

    protected void changeFragment(@Nullable String tab) {
        binding.header.imgBack.setImageResource(R.drawable.ic_list_icon);
        binding.header.imgNotification.setImageResource(R.drawable.ic_notification_icon);
        binding.header.setCheck(true);
        binding.header.imgNotification.setTag("notification");
        switch (tab != null ? tab : MyJobFragment.TAG) {
            case MyJobFragment.TAG:
                setHeader("My Jobs");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, MyJobFragment.TAG, MyJobFragment.newInstance(), true);
                break;
            case JobHistoryFragment.TAG:
                setHeader("Jobs History");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, JobHistoryFragment.TAG, JobHistoryFragment.newInstance(), true);
                break;
            case ReviewFragment.TAG:
                setHeader("Review");
                binding.header.imgNotification.setTag("review");
                binding.header.imgNotification.setImageResource(R.drawable.ic_add_icon);
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, ReviewFragment.TAG, ReviewFragment.newInstance(), true);
                break;
            case ChatListFragment.TAG:
                setHeader("Message");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, ChatListFragment.TAG, ChatListFragment.newInstance(), true);
                break;
            case NotificationActivity.TAG:
                binding.header.setCheck(false);
                setHeader("Notification");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                //BackStackManager.getInstance(this).pushFragments(R.id.container, NotificationActivity.TAG, NotificationActivity.newIntent(), true);
                break;
            case PrivacyPolicyFragment.TAG:
                setHeader("Terms & Conditions");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, PrivacyPolicyFragment.TAG, PrivacyPolicyFragment.newInstance(), true);
                break;
            case AboutUsFragment.TAG:
                setHeader("About US");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, AboutUsFragment.TAG, AboutUsFragment.newInstance(), true);
                break;
            case HelpAndSupportFragment.TAG:
                setHeader("Help and Support");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, HelpAndSupportFragment.TAG, HelpAndSupportFragment.newInstance(), true);
                break;

            /*case HomeFragment.TAG:
                setHeader("Home");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                BackStackManager.getInstance(this).pushFragments(R.id.container, HomeFragment.TAG, HomeFragment.newInstance(), tab != null);
                break;
            case MyMFragment.TAG:
                setHeader("Favourite");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, MyMFragment.TAG, MyMFragment.newInstance(), true);
                break;
            case RestaurantFragment.TAG:
                setHeader("Restaurant");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, RestaurantFragment.TAG, RestaurantFragment.newInstance(), true);
                break;
            case MoreFragment.TAG:
                setHeader("");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, MoreFragment.TAG, MoreFragment.newInstance(), true);
                break;*/
        }
    }

    @Override
    public void onBackPressed() {
        if (backStepFragment()) {
            if (exit) {
                super.onBackPressed();
            } else {
                viewModel.info.setValue(R.string.back_press);
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 2000);
            }
        }
    }

    private boolean backStepFragment() {
        return BackStackManager.getInstance(this).removeFragment(R.id.container, true);
    }

    /*
     * set header text
     **/
    public void setHeader(String header) {
        binding.header.tvTitle.setText(header);
    }

    private void setNavRecyclerView() {
        binding.navView.rvDrawer.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        SimpleRecyclerViewAdapter<MoreBean, HolderMoreBinding> adapter = new SimpleRecyclerViewAdapter<>(R.layout.holder_more, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
            @Override
            public void onItemClick(View v, MoreBean moreBean) {
                switch (moreBean != null ? moreBean.getId() : 1) {
                    case 1:
                        changeFragment(MyJobFragment.TAG);
                        binding.drawerViw.closeDrawers();
                        break;
                    case 2:
                        changeFragment(JobHistoryFragment.TAG);
                        binding.drawerViw.closeDrawers();
                        break;
                    case 3:
                        changeFragment(ReviewFragment.TAG);
                        binding.drawerViw.closeDrawers();
                        break;
                    case 4:
                        changeFragment(ChatListFragment.TAG);
                        binding.drawerViw.closeDrawers();
                        break;
                    case 5:
                        changeFragment(NotificationActivity.TAG);
                        binding.drawerViw.closeDrawers();
                        break;
                    case 6:
                        binding.navView.setCheck(true);
                        break;
                    case 7:
                        binding.drawerViw.closeDrawers();
                        break;
                }
            }
        });
        binding.navView.rvDrawer.setAdapter(adapter);
        adapter.setList(getMoreData());
        setAccountRecyclerView();
    }

    private boolean check_privacy = true;

    private void setAccountRecyclerView() {
        binding.navView.rvMore.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        SimpleRecyclerViewAdapter<MoreBean, HolderSettingsBinding> adapter = new SimpleRecyclerViewAdapter<>(R.layout.holder_settings, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
            @Override
            public void onItemClick(View v, MoreBean moreBean) {
                Intent intent;
                switch (moreBean != null ? moreBean.getId() : 1) {
                    case 1:
                        intent = ProfileActivity.newIntent(DashBoardActivity.this);
                        startNewActivity(intent);
                        binding.drawerViw.closeDrawers();
                        break;
                    case 2:
                        intent = ChangePasswordFragment.newIntent(DashBoardActivity.this);
                        startNewActivity(intent);
                        binding.drawerViw.closeDrawers();
                        break;
                    case 3:
                        changeFragment(PrivacyPolicyFragment.TAG);
                        binding.drawerViw.closeDrawers();
                        break;
                    case 4:
                        changeFragment(AboutUsFragment.TAG);
                        binding.drawerViw.closeDrawers();
                        break;
                    case 5:
                        changeFragment(HelpAndSupportFragment.TAG);
                        binding.drawerViw.closeDrawers();
                        break;
                    case 6:
                        binding.navView.setCheck(true);
                        break;
                }
            }
        });
        binding.navView.rvMore.setAdapter(adapter);
        adapter.setList(getAccountData());
    }

    private List<MoreBean> getMoreData() {
        List<MoreBean> menuBeanList = new ArrayList<>();
        menuBeanList.add(new MoreBean(1, "Home", R.drawable.home_icon));
        menuBeanList.add(new MoreBean(2, "Job History", R.drawable.ic_job_icon));
        menuBeanList.add(new MoreBean(3, "Reviews", R.drawable.ic_review_sidemenu));
        menuBeanList.add(new MoreBean(4, "Chat", R.drawable.ic_chat_sidemenu));
        menuBeanList.add(new MoreBean(5, "Notifications", R.drawable.ic_notification_sidemenu));
        menuBeanList.add(new MoreBean(6, "Settings", R.drawable.ic_settings_icon));
        menuBeanList.add(new MoreBean(7, "Logout", R.drawable.ic_logout_icon));
        return menuBeanList;
    }

    private List<MoreBean> getAccountData() {
        List<MoreBean> menuBeanList = new ArrayList<>();
        menuBeanList.add(new MoreBean(1, "Profile", R.drawable.ic_profile_sidemenu_icon));
        menuBeanList.add(new MoreBean(2, "Change Password", R.drawable.ic_change_password_icon));
        menuBeanList.add(new MoreBean(3, "Terms & Conditions", R.drawable.ic_privacy_policy_icon));
        menuBeanList.add(new MoreBean(4, "About US", R.drawable.ic_about_us_icon));
        menuBeanList.add(new MoreBean(5, "Help And Support", R.drawable.ic_help_icon));
        menuBeanList.add(new MoreBean(6, "Logout", R.drawable.ic_logout_icon));
        return menuBeanList;
    }

}
