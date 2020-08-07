package com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.respbean.GetCardBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PaymentActivityVM extends BaseActivityViewModel{
    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    SingleRequestEvent<List<GetCardBean>> singleRequestEvent = new SingleRequestEvent<>();
    @Inject
    public PaymentActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getUserCard() {
        welcomeRepo.getUserCard(sharedPref.getUserData().getAuthKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<List<GetCardBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        singleRequestEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<List<GetCardBean>> listApiResponse) {
                        if (listApiResponse.getStatus() == HttpURLConnection.HTTP_OK){
                            singleRequestEvent.setValue(Resource.success(listApiResponse.getData(),listApiResponse.getMsg()));
                        } else {
                            singleRequestEvent.setValue(Resource.warn(null,listApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        singleRequestEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }
}
