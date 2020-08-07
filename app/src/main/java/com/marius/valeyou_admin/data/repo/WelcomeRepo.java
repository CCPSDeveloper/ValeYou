package com.marius.valeyou_admin.data.repo;

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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

public interface WelcomeRepo {

    Single<ApiResponse<List<CategoryDataBean>>> categoriesApi(int type);

    Single<SimpleApiResponse> checkEmail(String email);

    Single<SimpleApiResponse> sendOTP(String email);

    Single<SimpleApiResponse> verifyEmail(String email,String otp);

    Single<ApiResponse<SignInModel>> signup(Map<String, String> map);

    Single<ApiResponse<SignInModel>> signin(Map<String,String> map);

    Single<ApiResponse<SignInModel>> logout(String provider_id, String auth_key);

    Single<ApiResponse<SignInModel>> socialSignIn(Map<String,String> map);

    Single<ApiResponse<ForgotPasswordModel>> forgotPassword(String email);

    Single<ApiResponse<ChangePasswordModel>> changePassword(String authKey, String oldPassword, String newPassord);

    Single<ApiResponse<ProfileModel>> profile(String auth_key,String provider_id);

    Single<ApiResponse<List<MapListModel>>> getMapList(String auth_key, String search);

    Single<ApiResponse<JsonElement>> addPortfolio(String auth_key,Map<String, RequestBody> map, MultipartBody.Part image);

    Single<ApiResponse<List<LanguagesModel>>> getLanguagesApi(String auth_key);

    Single<ApiResponse<JsonElement>> addLanguageApi(String auth_key,String name,String type);

    Single<SimpleApiResponse> addCertificateApi(String auth_key,Map<String, RequestBody> map, MultipartBody.Part image);

    Single<SimpleApiResponse> deletePortfolioApi(String auth_key, int portfolio_id);

    Single<SimpleApiResponse> deleteLanguageApi(String auth_key, int lang_id);

    Single<SimpleApiResponse> deleteCertificate(String auth_key, int certificate_id);

    Single<SimpleApiResponse> editLanguage(String auth_key, int lang_id, String name, String type);

    Single<SimpleApiResponse> editProfileServicesApi(String auth_key, String selected_data);

    Single<SimpleApiResponse> editPortfolio(String auth_key,Map<String, RequestBody> map,MultipartBody.Part image);

    Single<SimpleApiResponse> editCertificate(String auth_key,Map<String, RequestBody> map,MultipartBody.Part image);

    Single<ApiResponse<AboutModel>> aboutApi(int type);

    Single<ApiResponse<PrivacyModel>> privacyApi(int type);

    Single<ApiResponse<TermsModel>> termsApi(int type);

    Single<ApiResponse<List<MyReview>>> myReviewApi(String auth_key);

    Single<ApiResponse<SignInModel>> editProfile(String auth_key,Map<String, RequestBody> map, MultipartBody.Part image);

    Single<ApiResponse<SignInModel>> editProfileStr(String auth_key,Map<String, String> map);


    Single<ApiResponse<List<MyFavouritesModel>>> myFavouritesApi(String auth_key);

    Single<SimpleApiResponse> addBussinessHoursApi(String auth_key,String data, String type);

    Single<ApiResponse<JobDetailModel>> getJobDetailApi(String auth_key,int post_id);

    Single<SimpleApiResponse> placeBid(String auth_key,int post_id,int price,String description);

    Single<ApiResponse<List<MyJobsModel>>> myJobsApi(String auth_key,String status);

    Single<SimpleApiResponse> filterApi(String auth_key,String category_id, String distance, String state);

    Single<SimpleApiResponse> addIdentity(String auth_key,MultipartBody.Part image,MultipartBody.Part backImage);

    Single<ApiResponse<List<IdentityModel>>> getIdentity(String auth_key);


    Single<SimpleApiResponse> deleteIdentity(String auth_key,int identity_id);

    Single<SimpleApiResponse> editIdentity(String auth_key,
                                           Map<String,RequestBody> map,
                                           @Part MultipartBody.Part image,
                                           @Part MultipartBody.Part backImage);


    Single<ApiResponse<List<LanguagesBean>>> allLanguages();



    Single<SimpleApiResponse> accountSetting(String auth_key,String type);

    Single<SimpleApiResponse> startJob(String auth_key,int job_id, int status,String start);

    Single<SimpleApiResponse> EndJob(String auth_key,int job_id, int status,String end);


    Single<SimpleApiResponse> addToFavApi(String auth_key,int job_id, int status);


    Single<ApiResponse<List<NotificationModel>>> getNotifications(String auth_key);

    Single<ApiResponse<List<FaqModel>>> getFaq(String auth_key);

    Single<SimpleApiResponse> addEditEducationApi(String auth_key,HashMap<String,String> map);
    Single<SimpleApiResponse> EditEducationApi(String auth_key,HashMap<String,String> map,int id);

    Single<SimpleApiResponse> addEditExperienceApi(String auth_key, HashMap<String,String> map);

    Single<SimpleApiResponse> EditExperienceApi(String auth_key, HashMap<String,String> map,int id);

    Single<SimpleApiResponse> deleteEducation(String auth_key,int id);

    Single<SimpleApiResponse> deleteExperience(String auth_key,int id);


}
