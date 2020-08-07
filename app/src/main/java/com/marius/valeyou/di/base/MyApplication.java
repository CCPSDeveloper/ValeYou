package com.marius.valeyou.di.base;

import androidx.multidex.MultiDex;

import com.marius.valeyou.di.component.DaggerAppComponent;
import com.marius.valeyou.util.misc.AppVisibilityDetector;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

public class MyApplication extends DaggerApplication {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        MultiDex.install(this);
        attachErrorHandler();
        AppVisibilityDetector.init(this, new AppVisibilityDetector.AppVisibilityCallback() {
            @Override
            public void onAppGotoForeground() {

              /*  if (sharedPref.contains("userdata")) {
                    long ct = System.currentTimeMillis() / 1000;
                    if (lastonlinetime != -1 && ct - lastonlinetime < DEFAULT_ONLINE_TIME_THRESHOLD) {
                        lastonlinetime = ct;
                    } else {
                        startActivity(SecurityActivity.newIntent(application));
                    }
                }*/
            }

            @Override
            public void onAppGotoBackground() {
                // lastonlinetime = System.currentTimeMillis() / 1000;
            }
        });


    }


    public static MyApplication getInstance() {
        return application;
    }

    public void restartApp() {
       /* Intent intent = SplashActivity.newIntent(this);
        intent.putExtra("reset", true);
        startActivity(intent);*/

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }


    private void attachErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable instanceof OnErrorNotImplementedException || throwable instanceof ProtocolViolationException) {

                }
            }
        });
    }


}
