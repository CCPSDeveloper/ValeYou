package com.marius.valeyou_admin.ui.activity.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.base.MoreBean;
import com.marius.valeyou_admin.data.beans.singninbean.SignInModel;
import com.marius.valeyou_admin.data.local.SharedPref;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.ActivityMainBinding;
import com.marius.valeyou_admin.databinding.DialogDeleteAccouontBinding;
import com.marius.valeyou_admin.databinding.HolderMoreBinding;
import com.marius.valeyou_admin.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou_admin.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.aboutus.AboutUsFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.changepassword.ChangePasswordFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.helpnsupport.HelpAndSupportFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.privacy_policy.PrivacyPolicyFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.ProfileActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.terms.TermsFragment;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;
import com.marius.valeyou_admin.ui.activity.notification.NotificationActivity;
import com.marius.valeyou_admin.ui.activity.profileproject.ProfileProjectActivity;
import com.marius.valeyou_admin.ui.fragment.home.HomeFragment;
import com.marius.valeyou_admin.ui.fragment.message.ChatListFragment;
import com.marius.valeyou_admin.ui.fragment.more.MoreFragment;
import com.marius.valeyou_admin.ui.fragment.favourite.MyMFragment;
import com.marius.valeyou_admin.ui.fragment.myjob.MyJobFragment;
import com.marius.valeyou_admin.ui.fragment.products.ProductFragment;
import com.marius.valeyou_admin.ui.fragment.restaurant.RestaurantFragment;
import com.marius.valeyou_admin.util.databinding.ImageViewBindingUtils;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;
import com.marius.valeyou_admin.util.location.LiveLocationDetecter;
import com.marius.valeyou_admin.util.misc.BackStackManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppActivity<ActivityMainBinding, MainActivityVM> {
    public static final String TAG = "MainActivity";
    @Inject
    SharedPref sharedPref;
    private boolean exit = false;
    @Inject
    LiveLocationDetecter liveLocationDetecter;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BindingActivity<MainActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_main, MainActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(final MainActivityVM vm) {
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                if (binding.header.imgBack.getAlpha() == 1)
                    backStepFragment();
            }
        });

        binding.drawer.closeDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDrawer();
            }
        });


        binding.drawer.tvViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ProfileActivity.newIntent(MainActivity.this);
                startNewActivity(intent);
                hideDrawer();
            }
        });

        binding.drawer.tvViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ProfileActivity.newIntent(MainActivity.this);
                startNewActivity(intent);
                hideDrawer();
            }
        });
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {
                case R.id.ll_menu:
                    changeFragment(MyJobFragment.TAG);
                    break;
                case R.id.ll_fav:
                    changeFragment(MyMFragment.TAG);
                    break;
                case R.id.cv_home:
                    changeFragment(HomeFragment.TAG);
                    break;
                case R.id.ll_settings:
                    //changeFragment(MoreFragment.TAG);
                    HomeFragment.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    break;
                case R.id.ll_message:
                    changeFragment(ChatListFragment.TAG);
                    break;
            }
        });


        vm.userBean.observe(this, new SingleRequestEvent.RequestObserver<SignInModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignInModel> resource) {
                switch (resource.status) {
                    case LOADING:
                       showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                      dismissProgressDialog();
                        vm.success.setValue(resource.message);
                        Intent intent1 = LoginActivity.newIntent(MainActivity.this);
                        startNewActivity(intent1, true, true);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorised")){
                            Intent intent = LoginActivity.newIntent(MainActivity.this);
                            startNewActivity(intent, true, true);

                        }
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });



        vm.obrNavClick.observe(this, menuItem -> {
            //  binding.header.ivProfile.setImageResource(R.drawable.ic_base_profile);
            switch (menuItem.getItemId()) {
                case R.id.nav_1:
                    changeFragment(HomeFragment.TAG);
                    break;
                case R.id.nav_2:
                    changeFragment(MyMFragment.TAG);
                    break;
                case R.id.nav_3:
                    changeFragment(ProductFragment.TAG);
                    break;
                case R.id.nav_4:
                    changeFragment(RestaurantFragment.TAG);
                    break;
                case R.id.nav_5:
                    changeFragment(MoreFragment.TAG);
                    break;

            }
        });

        BackStackManager.getInstance(this).setFragmentChangeListioner((tag, isSubFragment) -> {
            if (isSubFragment)
                showBackButton();
            else
                hideBackButton();
        });

        BackStackManager.getInstance(this).pushFragments(R.id.container, HomeFragment.TAG, HomeFragment.newInstance(),true);



        setRecyclerViewMore();

    }

    @Override
    protected void onResume() {
        super.onResume();

        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;
            binding.drawer.tvAppVersion.setText("App Version : "+versionNumber+"("+versionName+")");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



        String image = viewModel.sharedPref.getUserData().getImage();
        ImageViewBindingUtils.setProfilePicture(binding.drawer.progileImg,"http://3.17.254.50:4999/upload/"+image);

        String name = viewModel.sharedPref.getUserData().getFirstName()+" "+viewModel.sharedPref.getUserData().getLastName();

        binding.drawer.tvName.setText(name);



    }

    protected void changeFragment(@Nullable String tab) {
        switch (tab != null ? tab : HomeFragment.TAG) {
            case HomeFragment.TAG:
                BackStackManager.getInstance(this).pushFragments(R.id.container, HomeFragment.TAG, HomeFragment.newInstance(), tab != null);
                break;
            case MyMFragment.TAG:
                setHeader("Favourite");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, MyMFragment.TAG, MyMFragment.newInstance(), true);
                break;
            case MyJobFragment.TAG:
                setHeader("My Jobs");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, MyJobFragment.TAG, MyJobFragment.newInstance(), true);
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
                break;
            case ChatListFragment.TAG:
                setHeader("Message");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, ChatListFragment.TAG, ChatListFragment.newInstance(), true);
                break;
        }
    }


    public void addSubFragment(@NonNull String tab, Fragment fragment) {
        BackStackManager.getInstance(this).pushSubFragments(R.id.container, tab, fragment, true);
    }

    public void hideBackButton() {
        binding.header.imgBack.animate().alpha(0);
    }

    public void showDrawer(){
        binding.drawerView.setVisibility(View.VISIBLE);
        binding.drawerView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.right));
    }

    public void hideDrawer(){

        binding.drawerView.setVisibility(View.GONE);
    }



    public void showBackButton() {
        binding.header.imgBack.animate().alpha(1);
    }

    @Override
    public void onBackPressed() {
        setExitDialog();

    }

    private boolean backStepFragment() {
        return BackStackManager.getInstance(this).removeFragment(R.id.container, true);
    }

    /*
     * show or hide bottom navigation bar
     **/
    public void hideBottomNav(boolean bool) {

    }

    /*
     * set header text
     **/
    public void setHeader(String header) {
        binding.header.tvTitle.setText(header);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        liveLocationDetecter.onActivityResult(requestCode, resultCode);
    }


    private BaseCustomDialog<DialogDeleteAccouontBinding> exitDialog;
    private void setExitDialog() {
        exitDialog = new BaseCustomDialog<>(MainActivity.this, R.layout.exit_dialog, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            exitDialog.dismiss();
                            finish(true);
                            break;
                        case R.id.btn_cancel:
                            exitDialog.dismiss();
                            break;
                    }
                }
            }
        });

        exitDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        exitDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        exitDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        exitDialog.show();

    }


    /* load data in list */
    private List<MoreBean> getMoreData() {
        List<MoreBean> menuBeanList = new ArrayList<>();
        menuBeanList.add(new MoreBean(1, "Favourites", R.drawable.ic_favorite_icon));
        menuBeanList.add(new MoreBean(2, "Change Password", R.drawable.ic_change_password_icon));
        menuBeanList.add(new MoreBean(3, "Privacy Policy", R.drawable.ic_privacy_policy_icon));
        menuBeanList.add(new MoreBean(4, "Terms & Conditions", R.drawable.ic_privacy_policy_icon));
        menuBeanList.add(new MoreBean(5, "About US", R.drawable.ic_about_us_icon));
        menuBeanList.add(new MoreBean(6, "Help And Support", R.drawable.ic_help_icon));
        menuBeanList.add(new MoreBean(7, "Logout", R.drawable.ic_logout_icon));
        return menuBeanList;
    }

    private void setRecyclerViewMore() {
        binding.drawer.rvMenulist.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        SimpleRecyclerViewAdapter<MoreBean, HolderMoreBinding> adapter = new SimpleRecyclerViewAdapter<>(R.layout.holder_more, com.marius.valeyou_admin.BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
            @Override
            public void onItemClick(View v, MoreBean moreBean) {
                Intent intent;
                switch (moreBean != null ? moreBean.getId() : 1) {
                    case 1:
                        setHeader("Favourite");
                        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                        BackStackManager.getInstance(MainActivity.this).pushFragments(R.id.container, MyMFragment.TAG, MyMFragment.newInstance(),true);
                        hideDrawer();
                        break;

                    case 2:
                        intent = ChangePasswordFragment.newIntent(MainActivity.this);
                        startNewActivity(intent);
                        hideDrawer();
                        break;
                    case 3:
                        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                        BackStackManager.getInstance(MainActivity.this).pushFragments(R.id.container, PrivacyPolicyFragment.TAG, PrivacyPolicyFragment.newInstance(),true);
                        hideDrawer();
                        break;
                    case 4:
                        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                        BackStackManager.getInstance(MainActivity.this).pushFragments(R.id.container, TermsFragment.TAG, TermsFragment.newInstance(),true);
                        hideDrawer();
                        break;
                    case 5:
                        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                        BackStackManager.getInstance(MainActivity.this).pushFragments(R.id.container, AboutUsFragment.TAG, AboutUsFragment.newInstance(),true);
                        hideDrawer();
                        break;
                    case 6:
                        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                        BackStackManager.getInstance(MainActivity.this).pushFragments(R.id.container, HelpAndSupportFragment.TAG, HelpAndSupportFragment.newInstance(),true);
                        hideDrawer();
                        break;
                    case 7:
                        hideDrawer();
                       dialogLogout();

                        break;
                }
            }
        });
        binding.drawer.rvMenulist.setAdapter(adapter);
        adapter.setList(getMoreData());
    }


    private BaseCustomDialog<DialogDeleteAccouontBinding> dialogLogout;
    private void dialogLogout() {
        dialogLogout = new BaseCustomDialog<>(MainActivity.this, R.layout.logout_dialog, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            String id = String.valueOf(viewModel.sharedPref.getUserData().getId());
                            viewModel.logoutApi(id,viewModel.sharedPref.getUserData().getAuthKey());
                            dialogLogout.dismiss();
                            break;
                        case R.id.btn_cancel:
                            dialogLogout.dismiss();
                            break;
                    }
                }
            }
        });
        dialogLogout.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogLogout.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogLogout.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogLogout.show();
    }


}
