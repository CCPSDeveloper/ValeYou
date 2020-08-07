package com.marius.valeyou.ui.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.marius.valeyou.R;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.ActivityMainBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.notification.NotificationActivity;
import com.marius.valeyou.ui.activity.selectcategory.SelectCategoryActivity;
import com.marius.valeyou.ui.fragment.home.HomeFragment;
import com.marius.valeyou.ui.fragment.message.ChatListFragment;
import com.marius.valeyou.ui.fragment.more.MoreFragment;
import com.marius.valeyou.ui.fragment.favourite.MyMFragment;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragment;
import com.marius.valeyou.ui.fragment.products.ProductFragment;
import com.marius.valeyou.ui.fragment.restaurant.RestaurantFragment;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.misc.BackStackManager;
import com.marius.valeyou.util.misc.RxBus;

import javax.inject.Inject;

public class MainActivity extends AppActivity<ActivityMainBinding, MainActivityVM> {

    @Inject
    SharedPref sharedPref;
    private boolean exit = false;
    @Inject
    LiveLocationDetecter liveLocationDetecter;
    @Inject
    RxBus rxBus;

    public static Intent newIntent(Activity activity,String comeFrom) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("comeFrom",comeFrom);
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
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {
                case R.id.ll_menu:
                    changeFragment(MyJobFragment.TAG);
                    break;
                case R.id.ll_fav:
                    changeFragment(MyMFragment.TAG);
                    break;
                case R.id.cv_home:
                    //changeFragment(HomeFragment.TAG);
                    Intent intent1 = SelectCategoryActivity.newIntent(MainActivity.this, 0);
                    startNewActivity(intent1);
                    break;
                case R.id.ll_settings:
                    if (check) {
                        rxBus.send(Constants.FILTER);
                    } else {
                        changeFragment(HomeFragment.TAG);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                rxBus.send(Constants.FILTER);
                            }
                        },1000);
                    }
                    break;
                case R.id.ll_message:
                    changeFragment(ChatListFragment.TAG);
                    break;
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


    }

    private boolean check = false;
    public void changeFragment(@Nullable String tab) {
        switch (tab != null ? tab : HomeFragment.TAG) {
            case HomeFragment.TAG:
                check = true;
                setHeader("");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                BackStackManager.getInstance(this).pushFragments(R.id.container, HomeFragment.TAG, HomeFragment.newInstance(), tab != null);
                break;
            case MyMFragment.TAG:
                check = false;
                setHeader("Favourite");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, MyMFragment.TAG, MyMFragment.newInstance(), true);
                break;
            case MyJobFragment.TAG:
                check = false;
                setHeader("My Jobs");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, MyJobFragment.TAG, MyJobFragment.newInstance(), true);
                break;
            case RestaurantFragment.TAG:
                check = false;
                setHeader("Restaurant");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, RestaurantFragment.TAG, RestaurantFragment.newInstance(), true);
                break;
            case MoreFragment.TAG:
                check = false;
                setHeader("");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                Intent intent = NotificationActivity.newIntent(MainActivity.this);
                startNewActivity(intent);
                //BackStackManager.getInstance(this).pushFragments(R.id.container, MoreFragment.TAG, MoreFragment.newInstance(), true);
                break;
            case ChatListFragment.TAG:
                check = false;
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

    public void showBackButton() {
        binding.header.imgBack.animate().alpha(1);
    }

    @Override
    public void onBackPressed() {
        if (backStepFragment()) {
            if (exit) {
                super.onBackPressed();
            } else {

             if (BackStackManager.getInstance(this).getCurrentTab() == "HomeFragment") {
                viewModel.info.setValue(R.string.back_press);
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 2000);

             }else{
                 changeFragment(HomeFragment.TAG);
             }

            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent!=null){
            String comeFrom = intent.getStringExtra("comeFrom");
            if (comeFrom.equalsIgnoreCase("jobs")){
                changeFragment(MyJobFragment.TAG);
            }else{
                changeFragment(HomeFragment.TAG);
            }
        }
    }
}
