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

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface WelcomeRepo {
    Single<ApiResponse<SignupData>> signupApi(Map<String, String> map, int device_type, int ngo);

    Single<ApiResponse<SignupData>> loginApi(Map<String, String> map);

    Single<ApiResponse> logOut(int user_id, String auth_key);

    Single<ApiResponse> userChangePassword(String auth_key, String old_password, String new_password);

    Single<ApiResponse<List<ProviderNearMe>>> getJobProvider(String auth_key, String latitude, String longitude, String limit,
                                                             String page, String search);

    Single<ApiResponse<List<CategoryListBean>>> getCategory(String auth_key, int type,String search);

    Single<ApiResponse<List<SubCategoryBean>>> getSubCategory(String auth_key, int category_id);

    Single<ApiResponse<PrivacyResponseBean>> getAllContent(String auth_key, int type);

    Single<ApiResponse<GetProfileBean>> getProfileData(String auth_key, int user_id);

    Single<ApiResponse<List<FaqBean>>> getFaq(String auth_key, int user_id);

    Single<SimpleApiResponse> forgetPassword(String email);

    Single<SimpleApiResponse> deleteLanguage(String auth_key, int language_id);

    Single<SimpleApiResponse> accountSettings(String auth_key, String type);

    Single<SimpleApiResponse> addToFav(String auth_key, int provider_id, int status);

    Single<ApiResponse<List<FavoriteListBean>>> getFavoriteList(String auth_key);

    Single<ApiResponse<ProviderDetails>> getProviderDetails(String authkey, String provider_id);

    Single<ApiResponse<AddJobBean>> createJob(String auth,
                                              RequestBody provider_id,
                                              RequestBody title,
                                              RequestBody description,
                                              RequestBody estimationTime,
                                              RequestBody estimationPrice,
                                              RequestBody location,
                                              RequestBody latitude,
                                              RequestBody longitude,
                                              RequestBody state,
                                              RequestBody zip_code,
                                              RequestBody city,
                                              RequestBody street,
                                              RequestBody appartment,
                                              RequestBody date,
                                              RequestBody time,
                                              RequestBody selected_data,
                                              RequestBody type,
                                              RequestBody startPrice,
                                              RequestBody endPrice,
                                              MultipartBody.Part image,
                                              RequestBody jobType);

    Single<ApiResponse<SignupData>> updateProfile(String auth, Map<String, RequestBody> data, MultipartBody.Part image);

    Single<ApiResponse<List<GetAllJobBean>>> getAllJobList(String auth, Map<String, String> data);

    Single<ApiResponse<JobDetailsBean>> getJobDetails(String auth, String post_id);

    Single<SimpleApiResponse> acceptRejectBid(String auth, Map<String, Integer> data);

    Single<SimpleApiResponse> userAddCard(String auth, String name, long card_number, int expiry_year, int expiry_month);

    Single<ApiResponse<List<GetCardBean>>> getUserCard(String auth);

    Single<ApiResponse<List<GetNotificationList>>> getNotificationList(String auth);

    Single<SimpleApiResponse> readNotification(String auth, String type, String notification_id);

    Single<ApiResponse<SignupData>> socialLogin(String social_id, String social_type, int device_type, Map<String, String> data);

    Single<SimpleApiResponse> addFilter(String auth, String category_id, int rating, int distance, String state, int total_jobs);

    Single<SimpleApiResponse> addIdentity(String auth_key,RequestBody type, RequestBody api_type, MultipartBody.Part frontImage,MultipartBody.Part backImage);

    Single<SimpleApiResponse> editIdentity(String auth_key,RequestBody identity_id,RequestBody type, RequestBody api_type, MultipartBody.Part frontImage,MultipartBody.Part backImage);

    Single<SimpleApiResponse> deleteIdentity(String auth_key,int identity_id);

}
