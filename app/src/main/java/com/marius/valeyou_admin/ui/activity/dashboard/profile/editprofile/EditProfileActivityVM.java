package com.marius.valeyou_admin.ui.activity.dashboard.profile.editprofile;

import android.view.View;

import com.marius.valeyou_admin.data.beans.base.ApiResponse;
import com.marius.valeyou_admin.data.beans.base.SimpleApiResponse;
import com.marius.valeyou_admin.data.beans.profilebean.ProfileModel;
import com.marius.valeyou_admin.data.beans.singninbean.SignInModel;
import com.marius.valeyou_admin.data.local.SharedPref;
import com.marius.valeyou_admin.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.data.repo.WelcomeRepo;
import com.marius.valeyou_admin.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou_admin.util.event.SingleActionEvent;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;
import com.marius.valeyou_admin.util.location.LiveLocationDetecter;

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

    public final SharedPref sharedPref;
    public final LiveLocationDetecter liveLocationDetecter;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    public final SingleActionEvent<View> base_click = new SingleActionEvent<>();

    final SingleRequestEvent<ApiResponse<SignInModel>> profilebeandata = new SingleRequestEvent<>();


    @Inject
    public EditProfileActivityVM(SharedPref sharedPref, LiveLocationDetecter liveLocationDetecter, NetworkErrorHandler networkErrorHandle, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
        this.networkErrorHandler = networkErrorHandle;

    }

    public void onClick(View view) {
        base_click.call(view);
    }

    public void editProfile(String auth_key, Map<String, RequestBody> map, MultipartBody.Part image){
        welcomeRepo.editProfile(auth_key, map,image).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<SignInModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        profilebeandata.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<SignInModel> profileApiResponse) {

                        if (profileApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                            profilebeandata.setValue(Resource.success(null, profileApiResponse.getMsg()));

                        } else {

                            profilebeandata.setValue(Resource.warn(null, profileApiResponse.getMsg()));

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        profilebeandata.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }


    public void editProfileSTr(String auth_key, Map<String, String> map){
        welcomeRepo.editProfileStr(auth_key, map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<SignInModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        profilebeandata.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<SignInModel> profileApiResponse) {

                        if (profileApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                            profilebeandata.setValue(Resource.success(null, profileApiResponse.getMsg()));

                        } else {

                            profilebeandata.setValue(Resource.warn(null, profileApiResponse.getMsg()));

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        profilebeandata.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }


}
