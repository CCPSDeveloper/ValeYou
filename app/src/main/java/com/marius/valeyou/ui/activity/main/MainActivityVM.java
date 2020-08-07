package com.marius.valeyou.ui.activity.main;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleActionEvent;
import com.marius.valeyou.util.event.SingleRequestEvent;

import javax.inject.Inject;

import dagger.android.support.DaggerApplication;

public class MainActivityVM extends BaseActivityViewModel {

    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    final SingleActionEvent<MenuItem> obrNavClick = new SingleActionEvent<>();
    final SingleRequestEvent<Integer> score = new SingleRequestEvent<>();
    final SingleRequestEvent<Void> playerResponse = new SingleRequestEvent<>();

    private final DaggerApplication context;
    final SingleActionEvent<long[]> saveData = new SingleActionEvent<>();

    @Inject
    public MainActivityVM(DaggerApplication context, SharedPref sharedPref, NetworkErrorHandler networkErrorHandler) {
        this.context = context;
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
    }


    public BottomNavigationView.OnNavigationItemSelectedListener getNavListioner() {
        return item -> {
            obrNavClick.setValue(item);
            return true;
        };
    }
}
