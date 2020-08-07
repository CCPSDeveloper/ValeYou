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
import com.marius.valeyou_admin.data.local.SharedPref;
import com.marius.valeyou_admin.data.remote.api.WelcomeApi;
import com.marius.valeyou_admin.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou_admin.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

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
    public Single<ApiResponse<List<CategoryDataBean>>> categoriesApi(int type) {
        return welcomeApi.getCategories(Constants.SECURITY_KEY,type);
    }

    @Override
    public Single<SimpleApiResponse> checkEmail(String email) {
        return welcomeApi.checkEmail(Constants.SECURITY_KEY,email);
    }

    @Override
    public Single<SimpleApiResponse> sendOTP(String email) {
        return welcomeApi.sendOTP(Constants.SECURITY_KEY,email);
    }

    @Override
    public Single<SimpleApiResponse> verifyEmail(String email, String otp) {
        return welcomeApi.verifyEmail(Constants.SECURITY_KEY,email,otp);
    }

    public Single<ApiResponse<SignInModel>> signup(Map<String, String> map) {
        return welcomeApi.signup(Constants.SECURITY_KEY,map);
    }

    @Override
    public Single<ApiResponse<SignInModel>> signin(Map<String, String> map) {
        return welcomeApi.singIn(Constants.SECURITY_KEY,map).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });
    }

    @Override
    public Single<ApiResponse<SignInModel>> logout(String provider_id, String auth_key) {
        return welcomeApi.logout(Constants.SECURITY_KEY,provider_id,auth_key);
    }

    @Override
    public Single<ApiResponse<SignInModel>> socialSignIn(Map<String, String> map) {
        return welcomeApi.socialSignInApi(Constants.SECURITY_KEY,map);
    }

    @Override
    public Single<ApiResponse<ForgotPasswordModel>> forgotPassword(String email) {
        return welcomeApi.forgotPassword(Constants.SECURITY_KEY,email);
    }

    @Override
    public Single<ApiResponse<ChangePasswordModel>> changePassword(String authKey, String oldPassword, String newPassord) {
        return welcomeApi.ChangePassword(Constants.SECURITY_KEY,authKey,oldPassword,newPassord);
    }

    @Override
    public Single<ApiResponse<ProfileModel>> profile(String auth_key, String provider_id) {
        return welcomeApi.getProfileData(Constants.SECURITY_KEY,auth_key,provider_id);
    }

    @Override
    public Single<ApiResponse<List<MapListModel>>> getMapList(String auth_key, String search) {
        return welcomeApi.getMapList(Constants.SECURITY_KEY,auth_key,search);
    }

    @Override
    public Single<ApiResponse<JsonElement>> addPortfolio(String auth_key, Map<String, RequestBody> map, MultipartBody.Part imageFile) {
        return welcomeApi.addPortfolio(Constants.SECURITY_KEY,auth_key,map,imageFile);
    }

    @Override
    public Single<ApiResponse<List<LanguagesModel>>> getLanguagesApi(String auth_key) {
        return welcomeApi.getLanguagesApi(Constants.SECURITY_KEY,auth_key);
    }

    @Override
    public Single<ApiResponse<JsonElement>> addLanguageApi(String auth_key, String name, String type) {
        return welcomeApi.addLanguageApi(Constants.SECURITY_KEY,auth_key,name,type);
    }

    @Override
    public Single<SimpleApiResponse> addCertificateApi(String auth_key, Map<String, RequestBody> map, MultipartBody.Part image) {
        return welcomeApi.addCertificateApi(Constants.SECURITY_KEY,auth_key,map,image);
    }

    @Override
    public Single<SimpleApiResponse> deletePortfolioApi(String auth_key, int portfolio_id) {
        return welcomeApi.deletePortfolio(Constants.SECURITY_KEY,auth_key,portfolio_id);
    }

    @Override
    public Single<SimpleApiResponse> deleteLanguageApi(String auth_key, int lang_id) {
        return welcomeApi.deleteLanguage(Constants.SECURITY_KEY,auth_key,lang_id);
    }

    @Override
    public Single<SimpleApiResponse> deleteCertificate(String auth_key, int certificate_id) {
        return welcomeApi.deleteCertificate(Constants.SECURITY_KEY,auth_key,certificate_id);
    }

    @Override
    public Single<SimpleApiResponse> editProfileServicesApi(String auth_key, String selected_data) {
        return welcomeApi.editProfileServices(Constants.SECURITY_KEY,auth_key,selected_data);

    }

    @Override
    public Single<SimpleApiResponse> editPortfolio(String auth_key, Map<String,RequestBody> map, MultipartBody.Part image) {
        return welcomeApi.editPortfolio(Constants.SECURITY_KEY,auth_key,map,image);

    }

    @Override
    public Single<SimpleApiResponse> editCertificate(String auth_key, Map<String, RequestBody> map, MultipartBody.Part image) {
        return welcomeApi.editCertificate(Constants.SECURITY_KEY,auth_key,map,image);
    }

    @Override
    public Single<ApiResponse<AboutModel>> aboutApi(int type) {
        return welcomeApi.aboutUsApi(Constants.SECURITY_KEY,type);
    }

    @Override
    public Single<ApiResponse<PrivacyModel>> privacyApi(int type) {
        return welcomeApi.privacyApi(Constants.SECURITY_KEY,type);
    }

    @Override
    public Single<ApiResponse<TermsModel>> termsApi(int type) {
        return welcomeApi.termsApi(Constants.SECURITY_KEY,type);
    }

    @Override
    public Single<ApiResponse<List<MyReview>>> myReviewApi(String auth_key) {
        return welcomeApi.myReviewApi(Constants.SECURITY_KEY,auth_key);
    }

    @Override
    public Single<ApiResponse<SignInModel>> editProfile(String auth_key, Map<String, RequestBody> map, MultipartBody.Part image) {
        return welcomeApi.editProfile(Constants.SECURITY_KEY,auth_key,map,image).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });
    }

    @Override
    public Single<ApiResponse<SignInModel>> editProfileStr(String auth_key, Map<String, String> map) {
        return welcomeApi.editProfileStr(Constants.SECURITY_KEY,auth_key,map).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });


    }

    @Override
    public Single<ApiResponse<List<MyFavouritesModel>>> myFavouritesApi(String auth_key) {
        return welcomeApi.myFavouritesApi(Constants.SECURITY_KEY,auth_key);
    }

    @Override
    public Single<SimpleApiResponse> addBussinessHoursApi(String auth_key, String data,String type) {
        return welcomeApi.addBussinessHoursApi(Constants.SECURITY_KEY,auth_key,data, type);
    }

    @Override
    public Single<ApiResponse<JobDetailModel>> getJobDetailApi(String auth_key, int post_id) {
        return welcomeApi.getJobDetail(Constants.SECURITY_KEY,auth_key,post_id);
    }

    @Override
    public Single<SimpleApiResponse> placeBid(String auth_key, int post_id, int price, String description) {
        return welcomeApi.placeBid(Constants.SECURITY_KEY,auth_key,post_id,price,description);
    }

    @Override
    public Single<ApiResponse<List<MyJobsModel>>> myJobsApi(String auth_key, String status) {
        return welcomeApi.myJobsApi(Constants.SECURITY_KEY,auth_key,status);
    }

    @Override
    public Single<SimpleApiResponse> filterApi(String auth_key, String category_id, String distance, String state) {
        return welcomeApi.filterApi(Constants.SECURITY_KEY,auth_key,category_id,distance,state);
    }

    @Override
    public Single<SimpleApiResponse> addIdentity(String auth_key, MultipartBody.Part image,MultipartBody.Part backImage) {
        return welcomeApi.addIdentity(Constants.SECURITY_KEY,auth_key,image,backImage);
    }

    @Override
    public Single<ApiResponse<List<IdentityModel>>> getIdentity(String auth_key) {
        return welcomeApi.getIdentity(Constants.SECURITY_KEY,auth_key);
    }

    @Override
    public Single<SimpleApiResponse> deleteIdentity(String auth_key, int identity_id) {
        return welcomeApi.deleteIdentity(Constants.SECURITY_KEY,auth_key,identity_id);
    }

    @Override
    public Single<SimpleApiResponse> editIdentity(String auth_key, Map<String, RequestBody> map, MultipartBody.Part image,MultipartBody.Part backImage) {
        return welcomeApi.editIdentity(Constants.SECURITY_KEY,auth_key,map,image,backImage);
    }

    @Override
    public Single<ApiResponse<List<LanguagesBean>>> allLanguages() {
        return welcomeApi.allLanguages(Constants.SECURITY_KEY);
    }

    @Override
    public Single<SimpleApiResponse> accountSetting(String auth_key, String type) {
        return welcomeApi.accountSetting(Constants.SECURITY_KEY,auth_key,type);
    }

    @Override
    public Single<SimpleApiResponse> startJob(String auth_key, int job_id, int status,String start) {
        return welcomeApi.startJob(Constants.SECURITY_KEY,auth_key,job_id,status,start);
    }

    @Override
    public Single<SimpleApiResponse> EndJob(String auth_key, int job_id, int status,String end) {
        return welcomeApi.EndJob(Constants.SECURITY_KEY,auth_key,job_id,status,end);
    }

    @Override
    public Single<SimpleApiResponse> addToFavApi(String auth_key, int job_id, int status) {
        return welcomeApi.addToFavApi(Constants.SECURITY_KEY,auth_key,job_id,status);
    }

    @Override
    public Single<ApiResponse<List<NotificationModel>>> getNotifications(String auth_key) {
        return welcomeApi.getNotifications(Constants.SECURITY_KEY,auth_key);
    }

    @Override
    public Single<ApiResponse<List<FaqModel>>> getFaq(String auth_key) {
        return welcomeApi.getFaq(Constants.SECURITY_KEY,auth_key);
    }

    @Override
    public Single<SimpleApiResponse> addEditEducationApi(String auth_key, HashMap<String, String> map) {
        return welcomeApi.addEditEducationApi(Constants.SECURITY_KEY,auth_key,map);
    }

    @Override
    public Single<SimpleApiResponse> EditEducationApi(String auth_key, HashMap<String, String> map, int id) {
        return welcomeApi.EditEducationApi(Constants.SECURITY_KEY,auth_key,map,id);
    }

    @Override
    public Single<SimpleApiResponse> addEditExperienceApi(String auth_key,HashMap<String, String> map) {
        return welcomeApi.addEditExperienceApi(Constants.SECURITY_KEY,auth_key,map);
    }

    @Override
    public Single<SimpleApiResponse> EditExperienceApi(String auth_key, HashMap<String, String> map, int id) {
        return welcomeApi.EditExperienceApi(Constants.SECURITY_KEY,auth_key,map,id);
    }

    @Override
    public Single<SimpleApiResponse> deleteEducation(String auth_key, int id) {
        return welcomeApi.deleteEducation(Constants.SECURITY_KEY,auth_key,id);
    }

    @Override
    public Single<SimpleApiResponse> deleteExperience(String auth_key, int id) {
        return welcomeApi.deleteExperience(Constants.SECURITY_KEY,auth_key,id);
    }

    @Override
    public Single<SimpleApiResponse> editLanguage(String auth_key, int lang_id, String name, String type) {
        return welcomeApi.editLanguage(Constants.SECURITY_KEY,auth_key,lang_id,name,type);
    }


}
