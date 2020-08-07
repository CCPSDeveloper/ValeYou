package com.marius.valeyou_admin.ui.fragment.home;

import androidx.databinding.ObservableField;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.marius.valeyou_admin.data.beans.base.ApiResponse;
import com.marius.valeyou_admin.data.beans.base.SimpleApiResponse;
import com.marius.valeyou_admin.data.beans.categoriesBean.CategoryDataBean;
import com.marius.valeyou_admin.data.beans.map.MapListModel;
import com.marius.valeyou_admin.data.beans.profilebean.ProfileModel;
import com.marius.valeyou_admin.data.local.SharedPref;
import com.marius.valeyou_admin.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.data.repo.WelcomeRepo;
import com.marius.valeyou_admin.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou_admin.util.event.SingleActionEvent;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;
import com.marius.valeyou_admin.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

public class HomeFragmentVM  extends BaseFragmentViewModel {

    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public final LiveLocationDetecter liveLocationDetecter;
    private final WelcomeRepo welcomeRepo;
    final SingleActionEvent<Integer> passIntent = new SingleActionEvent<>();
    public final ObservableField<String> field_Player1 = new ObservableField<>();
    final SingleRequestEvent<List<MapListModel>> mapListBean = new SingleRequestEvent<>();
    final SingleRequestEvent<List<CategoryDataBean>> categoriesbeandata = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> filterbeandata = new SingleRequestEvent<>();


    @Inject
    public HomeFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, LiveLocationDetecter liveLocationDetecter,WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
    }

    public void getMapList(String auth_key,String search){

        welcomeRepo.getMapList(auth_key,search).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<List<MapListModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                mapListBean.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<List<MapListModel>> mapApiResponse) {

                if (mapApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                    mapListBean.setValue(Resource.success(mapApiResponse.getData(), mapApiResponse.getMsg()));

                } else {

                    mapListBean.setValue(Resource.error(null, mapApiResponse.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                mapListBean.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

            }
        });
    }


    public void getCategories(int type){
        welcomeRepo.categoriesApi(type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<List<CategoryDataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        categoriesbeandata.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<List<CategoryDataBean>> categoriesApiResponse) {

                        if (categoriesApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                            categoriesbeandata.setValue(Resource.success(categoriesApiResponse.getData(), categoriesApiResponse.getMsg()));

                        } else {

                            categoriesbeandata.setValue(Resource.warn(null, categoriesApiResponse.getMsg()));

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        categoriesbeandata.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }

    public void filterApi(String auth_key,String category_id,String distance, String state){
        welcomeRepo.filterApi(auth_key,category_id,distance,state).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        filterbeandata.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse categoriesApiResponse) {

                        if (categoriesApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                            filterbeandata.setValue(Resource.success(null, categoriesApiResponse.getMsg()));

                        } else {

                            filterbeandata.setValue(Resource.warn(null, categoriesApiResponse.getMsg()));

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        filterbeandata.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }
}
