package com.marius.valeyou_admin.data.remote.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.marius.valeyou_admin.data.beans.IdentityModel;
import com.marius.valeyou_admin.data.beans.LanguagesBean;
import com.marius.valeyou_admin.data.beans.allcontent.AboutModel;
import com.marius.valeyou_admin.data.beans.allcontent.PrivacyModel;
import com.marius.valeyou_admin.data.beans.allcontent.TermsModel;
import com.marius.valeyou_admin.data.beans.base.ApiResponse;
import com.marius.valeyou_admin.data.beans.base.SimpleApiResponse;
import com.marius.valeyou_admin.data.beans.categoriesBean.CategoryDataBean;
import com.marius.valeyou_admin.data.beans.change.ChangePasswordModel;
import com.marius.valeyou_admin.data.beans.faq.FaqModel;
import com.marius.valeyou_admin.data.beans.favourites.MyFavouritesModel;
import com.marius.valeyou_admin.data.beans.forgot.ForgotPasswordModel;
import com.marius.valeyou_admin.data.beans.jobs.JobDetailModel;
import com.marius.valeyou_admin.data.beans.jobs.MyJobsModel;
import com.marius.valeyou_admin.data.beans.map.MapListModel;
import com.marius.valeyou_admin.data.beans.notification.NotificationModel;
import com.marius.valeyou_admin.data.beans.profilebean.LanguagesModel;
import com.marius.valeyou_admin.data.beans.profilebean.ProfileModel;
import com.marius.valeyou_admin.data.beans.reviews.MyReview;
import com.marius.valeyou_admin.data.beans.singninbean.SignInModel;
import com.marius.valeyou_admin.data.beans.singninbean.SocialSignIn;

import java.net.URI;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WelcomeApi {

    @GET(Urls.GET_CATEGORIES)
    Single<ApiResponse<List<CategoryDataBean>>> getCategories(@Header("security_key") String security_key, @Header("type") int type);



    @FormUrlEncoded
    @POST(Urls.CHECK_EMAIL)
    Single<SimpleApiResponse> checkEmail(@Header("security_key") String security_key,
                                         @Field("email") String email);

    @FormUrlEncoded
    @POST(Urls.SEND_OTP)
    Single<SimpleApiResponse> sendOTP(@Header("security_key") String security_key,
                                         @Field("email") String email);

    @FormUrlEncoded
    @POST(Urls.VERIFY_EMAIL)
    Single<SimpleApiResponse> verifyEmail(@Header("security_key") String security_key,
                                      @Field("email") String email,
                                          @Field("otp") String otp);

    @FormUrlEncoded
    @POST(Urls.SIGNUP)
    Single<ApiResponse<SignInModel>> signup(@Header("security_key") String security_key,
                                            @FieldMap Map<String,String> data);



    @FormUrlEncoded
    @POST(Urls.LOGIN)
    Single<ApiResponse<SignInModel>> singIn(@Header("security_key") String security_key, @FieldMap Map<String, String> data);

    @PUT(Urls.LOGOUT)
    Single<ApiResponse<SignInModel>> logout(@Header("security_key") String security_key, @Header("provider_id") String provider_id, @Header("auth_key") String auth_key);

    @FormUrlEncoded
    @POST(Urls.SOCIAL_LOGIN)
    Single<ApiResponse<SignInModel>> socialSignInApi(@Header("security_key") String security_key, @FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST(Urls.FORGOT_PASSWORD)
    Single<ApiResponse<ForgotPasswordModel>> forgotPassword(@Header("security_key") String security_key, @Field("email") String email);

    @FormUrlEncoded
    @PUT(Urls.CHANGE_PASSWORD)
    Single<ApiResponse<ChangePasswordModel>> ChangePassword(@Header("security_key") String security_key, @Header("auth_key") String auth_key,
                                                            @Field("old_password") String old_password, @Field("new_password") String new_password);
    @GET(Urls.PROFILE)
    Single<ApiResponse<ProfileModel>> getProfileData(@Header("security_key") String security_key,@Header("auth_key") String auth_key,@Header("provider_id") String provider_id);

    @GET(Urls.MAP_LIST)
    Single<ApiResponse<List<MapListModel>>> getMapList(@Header("security_key") String security_key,
                                                       @Header("auth_key") String auth_key,
                                                       @Query("search") String search);

    @POST(Urls.Add_PORTFOLIO)
    @Multipart
    Single<ApiResponse<JsonElement>> addPortfolio(@Header("security_key") String security_key,@Header("auth_key") String auth_key, @PartMap Map<String, RequestBody> data,@Part MultipartBody.Part image);

    @GET(Urls.GET_LANGUAGES)
    Single<ApiResponse<List<LanguagesModel>>> getLanguagesApi(@Header("security_key")String security_key,@Header("auth_key") String auth_key);

    @POST(Urls.ADD_LANGUAGE)
    @FormUrlEncoded
    Single<ApiResponse<JsonElement>> addLanguageApi(@Header("security_key") String security_key, @Header("auth_key") String auth_key, @Field("name") String name, @Field("type") String type);

    @POST(Urls.ADD_CERTIFICATE)
    @Multipart
    Single<SimpleApiResponse> addCertificateApi(@Header("security_key") String security_key,@Header("auth_key") String auth_key,@PartMap Map<String, RequestBody> data,@Part MultipartBody.Part image);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = Urls.DELETE_PORTFOLIO,hasBody = true)
    Single<SimpleApiResponse> deletePortfolio(@Header("security_key") String security_key,@Header("auth_key") String auth_key,@Field("portfolio_id") int portfolio_id);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = Urls.DELETE_LANGUAGE,hasBody = true)
    Single<SimpleApiResponse> deleteLanguage(@Header("security_key") String security_key,
                                             @Header("auth_key") String auth_key,@Field("lang_id") int lang_id);

   @FormUrlEncoded
   @HTTP(method = "DELETE", path = Urls.DELETE_CERTIFICATE,hasBody = true)
   Single<SimpleApiResponse> deleteCertificate(@Header("security_key") String security_key,
                                               @Header("auth_key") String auth_key,
                                               @Field("certificate_id") int certificate);

    @PUT(Urls.EDIT_PROFILE)
    @FormUrlEncoded
    Single<SimpleApiResponse> editProfileServices(@Header("security_key") String security_key,@Header("auth_key") String auth_key,@Field("selected_data")String selected_data);

    @PUT(Urls.EDIT_PROFILE)
    @Multipart
    Single<ApiResponse<SignInModel>> editProfile(@Header("security_key") String security_key,
                                          @Header("auth_key") String auth_key,
                                          @PartMap Map<String, RequestBody> data,
                                          @Part MultipartBody.Part image);

    @PUT(Urls.EDIT_PROFILE)
    @FormUrlEncoded
    Single<ApiResponse<SignInModel>> editProfileStr(@Header("security_key") String security_key,
                                          @Header("auth_key") String auth_key,
                                          @FieldMap Map<String, String> data);


    @FormUrlEncoded
    @PUT(Urls.EDIT_LANGUAGE)
    Single<SimpleApiResponse> editLanguage(@Header("security_key") String security_key,
                                           @Header("auth_key") String auth_key,
                                           @Field("lang_id") int lang_id,
                                           @Field("name") String name,
                                           @Field("type") String type);

    @Multipart
    @PUT(Urls.EDIT_PORTFOLIO)
    Single<SimpleApiResponse> editPortfolio(@Header("security_key") String security_key,
                                           @Header("auth_key") String auth_key,
                                            @PartMap Map<String, RequestBody> data,
                                            @Part MultipartBody.Part image
                                           );

    @Multipart
    @PUT(Urls.EDIT_CERTIFICATE)
    Single<SimpleApiResponse> editCertificate(@Header("security_key") String security_key,
                                            @Header("auth_key") String auth_key,
                                            @PartMap Map<String, RequestBody> data,
                                            @Part MultipartBody.Part image
    );
    @GET(Urls.ALL_CONTENT)
    Single<ApiResponse<AboutModel>> aboutUsApi(@Header("security_key") String security_key,@Header("type") int type);

    @GET(Urls.ALL_CONTENT)
    Single<ApiResponse<PrivacyModel>> privacyApi(@Header("security_key") String security_key,@Header("type") int type);

    @GET(Urls.ALL_CONTENT)
    Single<ApiResponse<TermsModel>> termsApi(@Header("security_key") String security_key,
                                             @Header("type") int type);


    @GET(Urls.MY_REVIEWS)
    Single<ApiResponse<List<MyReview>>> myReviewApi(@Header("security_key") String security_key,
                                                    @Header("auth_key") String auth_key);

    @GET(Urls.MY_FAVOURITES)
    Single<ApiResponse<List<MyFavouritesModel>>> myFavouritesApi(@Header("security_key") String security_key,
                                                                 @Header("auth_key") String auth_key);

    @POST(Urls.Add_BUSSINESS_HOURS)
    @FormUrlEncoded
    Single<SimpleApiResponse> addBussinessHoursApi(@Header("security_key") String security_key,
                                                   @Header("auth_key") String auth_key, @Field("data") String data,@Field("type") String type);

    @GET(Urls.JOB_DETAIL)
    Single<ApiResponse<JobDetailModel>> getJobDetail(@Header("security_key") String security_key,
                                                     @Header("auth_key") String auth_key,
                                                     @Header("post_id") int post_id);

    @POST(Urls.PLACE_BID)
    @FormUrlEncoded
    Single<SimpleApiResponse> placeBid(@Header("security_key") String security_key,
                                                     @Header("auth_key") String auth_key,
                                                     @Field("post_id") int post_id,
                                                        @Field("price") int price,
                                                        @Field("description") String description);


    @GET(Urls.MY_JOBS)
    Single<ApiResponse<List<MyJobsModel>>> myJobsApi(@Header("security_key") String security_key,
                                                     @Header("auth_key")String auth_key,
                                                     @Query("status") String status);


    @GET(Urls.FILTER)
    Single<SimpleApiResponse> filterApi(@Header("security_key") String security_key,
                                        @Header("auth_key")String auth_key,
                                        @Header("category_id") String category_id,
                                        @Header("distance") String distance,
                                        @Header("state") String state);


    @POST(Urls.ADD_IDENTITY)
    @Multipart
    Single<SimpleApiResponse> addIdentity(@Header("security_key") String security_key,
                                          @Header("auth_key")String auth_key,
                                          @Part MultipartBody.Part image,
                                          @Part MultipartBody.Part backImage);

    @GET(Urls.GET_IDENTITY)
    Single<ApiResponse<List<IdentityModel>>> getIdentity(@Header("security_key") String security_key,
                                      @Header("auth_key")String auth_key);



    @FormUrlEncoded
    @HTTP(method = "DELETE", path = Urls.DELETE_IDENTITY,hasBody = true)
    Single<SimpleApiResponse> deleteIdentity(@Header("security_key") String security_key,
                                                @Header("auth_key") String auth_key,
                                                @Field("identity_id") int certificate);

    @Multipart
    @PUT(Urls.EDIT_IDENTITY)
    Single<SimpleApiResponse> editIdentity(@Header("security_key") String security_key,
                                              @Header("auth_key") String auth_key,
                                              @PartMap Map<String, RequestBody> data,
                                              @Part MultipartBody.Part image,
                                           @Part MultipartBody.Part backImage);

    @GET(Urls.ALL_LANGUAGES)
    Single<ApiResponse<List<LanguagesBean>>> allLanguages(@Header("security_key") String security_key);


    @POST(Urls.ACCOUNT_SETTING)
    Single<SimpleApiResponse> accountSetting(@Header("security_key") String security_key
                           ,@Header("auth_key") String auth_key,
                            @Header("type") String type);

    @POST(Urls.START_JOB)
    @FormUrlEncoded
    Single<SimpleApiResponse> startJob(@Header("security_key") String security_key,
                                          @Header("auth_key") String auth_key,
                                          @Field("job_id") int job_id,
                                          @Field("status") int status,
                                          @Field("start_job_date") String start_job_date);


    @POST(Urls.START_JOB)
    @FormUrlEncoded
    Single<SimpleApiResponse> EndJob(@Header("security_key") String security_key,
                                          @Header("auth_key") String auth_key,
                                          @Field("job_id") int job_id,
                                          @Field("status") int status,
                                          @Field("end_job_date") String end_job_date);


    @POST(Urls.ADD_TO_FAV)
    @FormUrlEncoded
    Single<SimpleApiResponse> addToFavApi(@Header("security_key") String security_key,
                                          @Header("auth_key") String auth_key,
                                          @Field("job_id") int job_id,
                                          @Field("status") int status);


    @GET(Urls.GET_NOTIFICATION)
    Single<ApiResponse<List<NotificationModel>>> getNotifications(@Header("security_key") String security_key,
                                                                  @Header("auth_key") String auth_key);


    @GET(Urls.FAQ)
    Single<ApiResponse<List<FaqModel>>> getFaq(@Header("security_key") String security_key,
                                               @Header("auth_key") String auth_key);

    @POST(Urls.EDUCATION_ADD_EDIT)
    @FormUrlEncoded
    Single<SimpleApiResponse> addEditEducationApi(@Header("security_key") String security_key,
                                                  @Header("auth_key") String auth_key,
                                                  @FieldMap HashMap<String,String> map);

    @POST(Urls.EDUCATION_ADD_EDIT)
    @FormUrlEncoded
    Single<SimpleApiResponse> EditEducationApi(@Header("security_key") String security_key,
                                                  @Header("auth_key") String auth_key,
                                                  @FieldMap HashMap<String,String> map,
                                               @Field("id") int id);


    @POST(Urls.EXPERIENCE_ADD_EDIT)
    @FormUrlEncoded
    Single<SimpleApiResponse> addEditExperienceApi(@Header("security_key") String security_key,
                                                   @Header("auth_key") String auth_key,
                                                   @FieldMap HashMap<String,String> map);


    @POST(Urls.EXPERIENCE_ADD_EDIT)
    @FormUrlEncoded
    Single<SimpleApiResponse> EditExperienceApi(@Header("security_key") String security_key,
                                                   @Header("auth_key") String auth_key,
                                                   @FieldMap HashMap<String,String> map,
                                                @Field("id") int id);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = Urls.DELETE_EDUCATION,hasBody = true)
    Single<SimpleApiResponse> deleteEducation(@Header("security_key") String security_key,
                                             @Header("auth_key") String auth_key,
                                             @Field("id") int id);


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = Urls.DELETE_EXPERIENCE,hasBody = true)
    Single<SimpleApiResponse> deleteExperience(@Header("security_key") String security_key,
                                             @Header("auth_key") String auth_key,
                                             @Field("id") int id);



}
