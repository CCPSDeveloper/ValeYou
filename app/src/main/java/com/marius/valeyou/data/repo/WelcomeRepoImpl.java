package com.marius.valeyou.data.repo;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.AddJobBean;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.beans.respbean.FaqBean;
import com.marius.valeyou.data.beans.respbean.FavoriteListBean;
import com.marius.valeyou.data.beans.respbean.GetAllJobBean;
import com.marius.valeyou.data.beans.respbean.GetCardBean;
import com.marius.valeyou.data.beans.respbean.GetNotificationList;
import com.marius.valeyou.data.beans.respbean.GetProfileBean;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.beans.respbean.PrivacyResponseBean;
import com.marius.valeyou.data.beans.respbean.ProviderDetails;
import com.marius.valeyou.data.beans.respbean.ProviderNearMe;
import com.marius.valeyou.data.beans.respbean.SubCategoryBean;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.api.WelcomeApi;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.util.Constants;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class WelcomeRepoImpl implements WelcomeRepo {

    private final SharedPref sharedPref;
    private final WelcomeApi welcomeApi;
    private final NetworkErrorHandler networkErrorHandler;

    public WelcomeRepoImpl(SharedPref sharedPref, WelcomeApi welcomeApi, NetworkErrorHandler networkErrorHandler) {
        this.sharedPref = sharedPref;
        this.welcomeApi = welcomeApi;
        this.networkErrorHandler = networkErrorHandler;
    }

    @Override
    public Single<ApiResponse<SignupData>> signupApi(Map<String, String> data, int device_type, int ngo) {
        return welcomeApi.signupApi(Constants.SECURITY_KEY, data, device_type, ngo).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                //sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });
    }

    @Override
    public Single<ApiResponse<SignupData>> loginApi(Map<String, String> map) {
        return welcomeApi.loginApi(Constants.SECURITY_KEY, map).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });
    }

    @Override
    public Single<SimpleApiResponse> sendOTP(String email) {
        return welcomeApi.sendOTP(Constants.SECURITY_KEY,email);
    }

    @Override
    public Single<SimpleApiResponse> verifyEmail(String email, String otp) {
        return welcomeApi.verifyEmail(Constants.SECURITY_KEY,email,otp);
    }

    @Override
    public Single<ApiResponse> logOut(int user_id, String auth_key) {
        return welcomeApi.logOut(Constants.SECURITY_KEY, user_id, auth_key);
    }

    @Override
    public Single<ApiResponse> userChangePassword(String auth_key, String old_password, String new_password) {
        return welcomeApi.userChangePassword(Constants.SECURITY_KEY, auth_key, old_password, new_password);
    }

    @Override
    public Single<ApiResponse<List<ProviderNearMe>>> getJobProvider(String auth_key, String latitude, String longitude, String limit, String page, String search) {
        return welcomeApi.getJobProvider(Constants.SECURITY_KEY, auth_key, latitude, longitude, limit, page, search);
    }

    @Override
    public Single<ApiResponse<List<CategoryListBean>>> getCategory(String auth_key, int type, String search) {
        return welcomeApi.getCategory(Constants.SECURITY_KEY, auth_key, type, search);
    }

    @Override
    public Single<ApiResponse<List<SubCategoryBean>>> getSubCategory(String auth_key, int category_id) {
        return welcomeApi.getSubCategory(Constants.SECURITY_KEY, auth_key, category_id);
    }

    @Override
    public Single<ApiResponse<PrivacyResponseBean>> getAllContent(String auth_key, int type) {
        return welcomeApi.getAllContent(Constants.SECURITY_KEY, auth_key, type);
    }

    @Override
    public Single<ApiResponse<GetProfileBean>> getProfileData(String auth_key, int user_id) {
        return welcomeApi.getProfileData(Constants.SECURITY_KEY, auth_key, user_id);
    }

    @Override
    public Single<ApiResponse<List<FaqBean>>> getFaq(String auth_key, int user_id) {
        return welcomeApi.getFaq(Constants.SECURITY_KEY, auth_key, user_id);
    }

    @Override
    public Single<SimpleApiResponse> forgetPassword(String email) {
        return welcomeApi.forgetPassword(email);
    }

    @Override
    public Single<SimpleApiResponse> deleteLanguage(String auth_key, int language_id) {
        return welcomeApi.deleteLanguage(Constants.SECURITY_KEY, auth_key, language_id);
    }

    @Override
    public Single<SimpleApiResponse> accountSettings(String auth_key, String type) {
        return welcomeApi.accountSettings(Constants.SECURITY_KEY, auth_key, type);
    }

    @Override
    public Single<SimpleApiResponse> addToFav(String auth_key, int provider_id, int status) {
        return welcomeApi.addToFav(Constants.SECURITY_KEY, auth_key, provider_id, status);
    }

    @Override
    public Single<ApiResponse<List<FavoriteListBean>>> getFavoriteList(String auth_key) {
        return welcomeApi.getFavoriteList(Constants.SECURITY_KEY, auth_key);
    }

    @Override
    public Single<ApiResponse<ProviderDetails>> getProviderDetails(String authkey, String provider_id) {
        return welcomeApi.getProviderDetails(Constants.SECURITY_KEY, authkey, provider_id);
    }

    @Override
    public Single<ApiResponse<AddJobBean>> createJob(String auth, RequestBody provider_id, RequestBody title,
                                                     RequestBody description, RequestBody estimationTime,
                                                     RequestBody estimationPrice, RequestBody location,
                                                     RequestBody latitude, RequestBody longitude, RequestBody state,
                                                     RequestBody zip_code, RequestBody city, RequestBody street,
                                                     RequestBody appartment, RequestBody date, RequestBody time,
                                                     RequestBody selected_data, RequestBody type, RequestBody startPrice,
                                                     RequestBody endPrice, MultipartBody.Part image, RequestBody jobType) {
        return welcomeApi.createJob(Constants.SECURITY_KEY, auth, provider_id, title, description, estimationTime, estimationPrice,
                location, latitude, longitude, state, zip_code, city, street, appartment, date, time, selected_data, type, startPrice, endPrice, jobType, image);
    }

    @Override
    public Single<ApiResponse<SignupData>> updateProfile(String auth, Map<String, RequestBody> data, MultipartBody.Part image) {
        return welcomeApi.updateProfile(Constants.SECURITY_KEY, auth, data, image).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });
    }

    @Override
    public Single<ApiResponse<List<GetAllJobBean>>> getAllJobList(String auth, Map<String, String> data) {
        return welcomeApi.getAllJobList(Constants.SECURITY_KEY, auth, data);
    }

    @Override
    public Single<ApiResponse<JobDetailsBean>> getJobDetails(String auth, String post_id) {
        return welcomeApi.getJobDetails(Constants.SECURITY_KEY, auth, post_id);
    }

    @Override
    public Single<SimpleApiResponse> acceptRejectBid(String auth, Map<String, Integer> data) {
        return welcomeApi.acceptRejectBid(Constants.SECURITY_KEY, auth, data);
    }

    @Override
    public Single<SimpleApiResponse> userAddCard(String auth, String name, long card_number, int expiry_year, int expiry_month) {
        return welcomeApi.userAddCard(Constants.SECURITY_KEY, auth, name, card_number, expiry_year, expiry_month);
    }

    @Override
    public Single<ApiResponse<List<GetCardBean>>> getUserCard(String auth) {
        return welcomeApi.getUserCard(Constants.SECURITY_KEY, auth);
    }

    @Override
    public Single<ApiResponse<List<GetNotificationList>>> getNotificationList(String auth) {
        return welcomeApi.getNotificationList(Constants.SECURITY_KEY, auth);
    }

    @Override
    public Single<SimpleApiResponse> readNotification(String auth, String type, String notification_id) {
        return welcomeApi.readNotification(Constants.SECURITY_KEY, auth, type, notification_id);
    }

    @Override
    public Single<ApiResponse<SignupData>> socialLogin(String social_id, String social_type, int device_type, Map<String, String> data) {
        return welcomeApi.socialLogin(Constants.SECURITY_KEY, social_id, social_type, device_type, data).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });
    }

    @Override
    public Single<SimpleApiResponse> addFilter(String auth, String category_id, int rating, int distance, String state, int total_jobs) {
        return welcomeApi.addFilter(Constants.SECURITY_KEY, auth, category_id, rating, distance, state, total_jobs);
    }

    @Override
    public Single<SimpleApiResponse> addIdentity(String auth_key, RequestBody type, RequestBody api_type, MultipartBody.Part frontImage, MultipartBody.Part backImage) {
        return welcomeApi.addIdentity(Constants.SECURITY_KEY, auth_key, type, api_type, frontImage, backImage);
    }

    @Override
    public Single<SimpleApiResponse> editIdentity(String auth_key, RequestBody identity_id, RequestBody type, RequestBody api_type, MultipartBody.Part frontImage, MultipartBody.Part backImage) {
        return welcomeApi.editIdentity(Constants.SECURITY_KEY, auth_key, identity_id,type, api_type, frontImage, backImage);
    }

    @Override
    public Single<SimpleApiResponse> deleteIdentity(String auth_key, int identity_id) {
        return welcomeApi.deleteIdentity(Constants.SECURITY_KEY, auth_key, identity_id);
    }
}
