package com.marius.valeyou.data.remote.api;

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
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WelcomeApi {

    @FormUrlEncoded
    @POST("user_signup")
    Single<ApiResponse<SignupData>> signupApi(@Header("security_key") String security_key, @FieldMap() Map<String, String> data,
                                              @Field("device_type ") int device_type, @Field("ngo") int ngo);

    @FormUrlEncoded
    @POST("user_login")
    Single<ApiResponse<SignupData>> loginApi(@Header("security_key") String security_key, @FieldMap() Map<String, String> data);

    @PUT("user_logout")
    Single<ApiResponse> logOut(@Header("security_key") String security_key, @Header("user_id") int user_id, @Header("auth_key") String auth_key);

    @FormUrlEncoded
    @PUT("user_change_password")
    Single<ApiResponse> userChangePassword(@Header("security_key") String security_key, @Header("auth_key") String auth_key,
                                           @Field("old_password") String old_password, @Field("new_password") String new_password);

    @GET("user_provider_nearme")
    Single<ApiResponse<List<ProviderNearMe>>> getJobProvider(@Header("security_key") String security_key, @Header("auth_key") String auth_key,
                                                             @Header("latitude") String latitude, @Header("longitude") String longitude,
                                                             @Query("limit") String limit, @Query("page") String page,
                                                             @Query("search") String search);

    @GET("user_get_categories")
    Single<ApiResponse<List<CategoryListBean>>> getCategory(@Header("security_key") String security_key,
                                                            @Header("auth_key") String auth_key,
                                                            @Header("type") int type,@Query("search") String search);

    @GET("user_get_sub_categories")
    Single<ApiResponse<List<SubCategoryBean>>> getSubCategory(@Header("security_key") String security_key,
                                                              @Header("auth_key") String auth_key, @Header("category_id") int category_id);

    @GET("all_content")
    Single<ApiResponse<PrivacyResponseBean>> getAllContent(@Header("security_key") String security_key,
                                                           @Header("auth_key") String auth_key, @Header("type") int type);

    @GET("user_get_profile")
    Single<ApiResponse<GetProfileBean>> getProfileData(@Header("security_key") String security_key,
                                                       @Header("auth_key") String auth_key, @Header("user_id") int user_id);

    @GET("user_faqs")
    Single<ApiResponse<List<FaqBean>>> getFaq(@Header("security_key") String security_key,
                                              @Header("auth_key") String auth_key, @Header("user_id") int user_id);

    @FormUrlEncoded
    @POST("user_forgot_password")
    Single<SimpleApiResponse> forgetPassword(@Field("email") String email);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "provider_delete_language", hasBody = true)
    Single<SimpleApiResponse> deleteLanguage(@Header("security_key") String security_key,
                                             @Header("auth_key") String auth_key, @Field("lang_id") int firstname);

    @GET("user_account_setting")
    Single<SimpleApiResponse> accountSettings(@Header("security_key") String security_key,
                                              @Header("auth_key") String auth_key, @Header("type") String type);

    @FormUrlEncoded
    @POST("user_add_to_fav_list")
    Single<SimpleApiResponse> addToFav(@Header("security_key") String security_key, @Header("auth_key") String auth_key,
                                       @Field("provider_id") int provider_id, @Field("status") int status);

    @GET("user_get_favourite_list")
    Single<ApiResponse<List<FavoriteListBean>>> getFavoriteList(@Header("security_key") String security_key,
                                                                @Header("auth_key") String auth_key);

    @GET("user_get_provider_detail")
    Single<ApiResponse<ProviderDetails>> getProviderDetails(@Header("security_key") String security_key,
                                                            @Header("auth_key") String auth, @Header("provider_id") String privider_id);

    @Multipart
    @POST("user_add_post")
    Single<ApiResponse<AddJobBean>> createJob(@Header("security_key") String security_key,
                                              @Header("auth_key") String auth,
                                              @Part("provider_id") RequestBody provider_id,
                                              @Part("title") RequestBody title,
                                              @Part("description") RequestBody description,
                                              @Part("startTime") RequestBody estimationTime,
                                              @Part("endTime") RequestBody estimationPrice,
                                              @Part("location") RequestBody location,
                                              @Part("latitude") RequestBody latitude,
                                              @Part("longitude") RequestBody longitude,
                                              @Part("state") RequestBody state,
                                              @Part("zipCode") RequestBody zip_code,
                                              @Part("city") RequestBody city,
                                              @Part("street") RequestBody street,
                                              @Part("appartment") RequestBody appartment,
                                              @Part("date") RequestBody date,
                                              @Part("time") RequestBody time,
                                              @Part("selected_data") RequestBody selected_data,
                                              @Part("type") RequestBody type,
                                              @Part("startPrice") RequestBody startPrice,
                                              @Part("endPrice") RequestBody endPrice,
                                              @Part("jobType") RequestBody jobType,
                                              @Part MultipartBody.Part image);

    @Multipart
    @PUT("user_edit_profile")
    Single<ApiResponse<SignupData>> updateProfile(@Header("security_key") String security_key,
                                                  @Header("auth_key") String auth,
                                                  @PartMap Map<String, RequestBody> data,
                                                  @Part MultipartBody.Part image);

    @GET("user_job_list")
    Single<ApiResponse<List<GetAllJobBean>>> getAllJobList(@Header("security_key") String security_key,
                                                           @Header("auth_key") String auth, @QueryMap Map<String, String> data);

    @GET("user_post_details")
    Single<ApiResponse<JobDetailsBean>> getJobDetails(@Header("security_key") String security_key,
                                                      @Header("auth_key") String auth, @Header("post_id") String post_id);

    @FormUrlEncoded
    @POST("user_accept_reject_bid")
    Single<SimpleApiResponse> acceptRejectBid(@Header("security_key") String security_key,
                                              @Header("auth_key") String auth, @FieldMap() Map<String, Integer> data);


    @FormUrlEncoded
    @POST("user_add_card")
    Single<SimpleApiResponse> userAddCard(@Header("security_key") String security_key,
                                          @Header("auth_key") String auth, @Field("name") String name,
                                          @Field("card_number") long card_number, @Field("expiry_year") int expiry_year,
                                          @Field("expiry_month") int expiry_month);

    @GET("user_get_card")
    Single<ApiResponse<List<GetCardBean>>> getUserCard(@Header("security_key") String security_key, @Header("auth_key") String auth);

    @GET("user_get_notifications")
    Single<ApiResponse<List<GetNotificationList>>> getNotificationList(@Header("security_key") String security_key, @Header("auth_key") String auth);

    @GET("user_read_notification")
    Single<SimpleApiResponse> readNotification(@Header("security_key") String security_key, @Header("auth_key") String auth,
                                               @Header("type") String type, @Header("notification_id") String notification_id);

    @FormUrlEncoded
    @POST("user_social_login")
    Single<ApiResponse<SignupData>> socialLogin(@Header("security_key") String security_key, @Field("social_id") String social_id,
                                                @Field("social_type") String social_type, @Field("device_type ") int device_type,
                                                @FieldMap() Map<String, String> data);

    @GET("user_filter_search")
    Single<SimpleApiResponse> addFilter(@Header("security_key") String security_key, @Header("auth_key") String auth,
                                        @Header("category_id") String category_id, @Header("rating") int rating,
                                        @Header("distance") int distance, @Header("state") String state, @Header("total_jobs") int total_jobs);


    @Multipart
    @POST("user_add_edit_identity")
    Single<SimpleApiResponse> addIdentity(@Header("security_key") String security_key,
                                              @Header("auth_key") String auth,
                                              @Part("type")  RequestBody type,
                                              @Part("api_type") RequestBody api_type,
                                              @Part MultipartBody.Part frontImage,
                                              @Part MultipartBody.Part backImage);

    @Multipart
    @POST("user_add_edit_identity")
    Single<SimpleApiResponse> editIdentity(@Header("security_key") String security_key,
                                          @Header("auth_key") String auth,
                                           @Part("identity_id")  RequestBody identity_id,
                                          @Part("type")  RequestBody type,
                                          @Part("api_type") RequestBody api_type,
                                          @Part MultipartBody.Part frontImage,
                                          @Part MultipartBody.Part backImage);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "user_delete_identity",hasBody = true)
    Single<SimpleApiResponse> deleteIdentity(@Header("security_key") String security_key,
                                             @Header("auth_key") String auth_key,
                                             @Field("identity_id") int identity_id);



}
