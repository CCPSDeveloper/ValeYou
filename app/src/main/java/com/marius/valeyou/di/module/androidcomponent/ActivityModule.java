package com.marius.valeyou.di.module.androidcomponent;

import com.marius.valeyou.ui.activity.PayPerHourActivity;
import com.marius.valeyou.ui.activity.categorytitle.CategoryTitleActivity;
import com.marius.valeyou.ui.activity.forgot.ForgotPasswordActivity;
import com.marius.valeyou.ui.activity.forgot.recoverpass.RecoverPasswordActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.notification.NotificationActivity;
import com.marius.valeyou.ui.activity.provider_profile.ProviderProfileActivity;
import com.marius.valeyou.ui.activity.selectcategory.SelectCategoryActivity;
import com.marius.valeyou.ui.activity.signup.SignupActivity;
import com.marius.valeyou.ui.activity.tourpage.TourActivity;
import com.marius.valeyou.ui.activity.welcome.WelcomeActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.when.WhenActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.whentype.WhenTypeActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.where.WhereActivity;
import com.marius.valeyou.ui.fragment.message.chatview.ChatActivity;
import com.marius.valeyou.ui.fragment.more.changepassword.ChangePasswordFragment;
import com.marius.valeyou.ui.fragment.more.helpnsupport.faq.FAQActivity;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivity;
import com.marius.valeyou.ui.fragment.more.profile.addidentity.AddIdentityActivity;
import com.marius.valeyou.ui.fragment.more.profile.addportfolio.AddPortfolioActivity;
import com.marius.valeyou.ui.fragment.more.profile.editprofile.EditProfileActivity;
import com.marius.valeyou.ui.fragment.more.profile.selectlanguage.SelectLanguageActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.PaymentActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails.PaymentCardDetails;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails.paymentsuccess.PaymentSuccessActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    // NOTE: UserApp  here

    @ContributesAndroidInjector
    abstract WelcomeActivity welcomeActivity();

    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector
    abstract SignupActivity signupActivity();

    @ContributesAndroidInjector
    abstract ForgotPasswordActivity forgotPasswordActivity();

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract TourActivity tourActivity();

    @ContributesAndroidInjector
    abstract RecoverPasswordActivity recoverPasswordActivity();

    @ContributesAndroidInjector
    abstract NotificationActivity notificationActivity();

    @ContributesAndroidInjector
    abstract ProfileActivity profileActivity();

    @ContributesAndroidInjector
    abstract EditProfileActivity editProfileActivity();

    @ContributesAndroidInjector
    abstract WhereActivity whereActivity();

    @ContributesAndroidInjector
    abstract WhenActivity whenActivity();

    @ContributesAndroidInjector
    abstract PaymentActivity paymentActivity();

    @ContributesAndroidInjector
    abstract PaymentCardDetails paymentCardDetails();

    @ContributesAndroidInjector
    abstract PaymentSuccessActivity paymentSuccessActivity();

    @ContributesAndroidInjector
    abstract ChatActivity chatActivity();

    @ContributesAndroidInjector
    abstract ChangePasswordFragment changePasswordFragment();

    @ContributesAndroidInjector
    abstract FAQActivity faqActivity();

    @ContributesAndroidInjector
    abstract SelectLanguageActivity selectLanguageActivity();

    @ContributesAndroidInjector
    abstract AddPortfolioActivity addPortfolioActivity();

    @ContributesAndroidInjector
    abstract SelectCategoryActivity selectCategoryActivity();

    @ContributesAndroidInjector
    abstract ProviderProfileActivity providerProfileActivity();

    @ContributesAndroidInjector
    abstract WhenTypeActivity whenTypeActivity();

    @ContributesAndroidInjector
    abstract CategoryTitleActivity categoryTitleActivity();

    @ContributesAndroidInjector
    abstract PayPerHourActivity payPerHourActivity();

    @ContributesAndroidInjector
    abstract AddIdentityActivity addIdentityActivity();

}
