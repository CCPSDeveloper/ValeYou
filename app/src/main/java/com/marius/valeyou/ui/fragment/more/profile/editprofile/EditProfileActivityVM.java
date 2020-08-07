package com.marius.valeyou.ui.fragment.more.profile.editprofile;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivityVM extends BaseActivityViewModel {
    private final WelcomeRepo welcomeRepo;
    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public SingleRequestEvent<SignupData> singleRequestEvent = new SingleRequestEvent<>();

    @Inject
    public EditProfileActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void editProfile(Map<String, RequestBody> bodyMap, MultipartBody.Part body) {
        welcomeRepo.updateProfile(sharedPref.getUserData().getAuthKey(),bodyMap,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<SignupData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        singleRequestEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<SignupData> apiResponse) {
                        if (apiResponse.getStatus()== HttpURLConnection.HTTP_OK){
                            singleRequestEvent.setValue(Resource.success(apiResponse.getData(),apiResponse.getMsg()));
                        } else {
                            singleRequestEvent.setValue(Resource.warn(null,apiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        singleRequestEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }
}
