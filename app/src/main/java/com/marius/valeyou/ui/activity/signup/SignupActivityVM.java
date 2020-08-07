package com.marius.valeyou.ui.activity.signup;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class SignupActivityVM extends BaseActivityViewModel {

    private final SharedPref sharedPref;
    public final LiveLocationDetecter liveLocationDetecter;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<SignupData> userBean = new SingleRequestEvent<>();

    @Inject
    public SignupActivityVM(SharedPref sharedPref, LiveLocationDetecter liveLocationDetecter, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {

        this.sharedPref = sharedPref;
        this.liveLocationDetecter = liveLocationDetecter;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void signup(Map<String, String> map, int device_type , int ngo) {
        welcomeRepo.signupApi(map,device_type,ngo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<SignupData>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                userBean.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<SignupData> signupDataApiResponse) {
                if (signupDataApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    userBean.setValue(Resource.success(signupDataApiResponse.getData(), signupDataApiResponse.getMsg()));
                } else {
                    userBean.setValue(Resource.error(null, signupDataApiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException exception = (HttpException) e;
                    if (exception.code() == 409){
                        userBean.setValue(Resource.error(null, "Email Already Exist."));
                    } else {
                        userBean.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                } else {
                    userBean.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                }
            }
        });
    }
}
