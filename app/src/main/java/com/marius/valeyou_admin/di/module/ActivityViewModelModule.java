package com.marius.valeyou_admin.di.module;

import androidx.lifecycle.ViewModel;

import com.marius.valeyou_admin.di.mapkey.ViewModelKey;
import com.marius.valeyou_admin.ui.activity.dashboard.DashBoardActivityVM;
import com.marius.valeyou_admin.ui.activity.dashboard.changepassword.ChangePasswordFragmentVM;
import com.marius.valeyou_admin.ui.activity.dashboard.jobhistory.currentjob.jobdetailsone.JobDetailsOneActivityVM;
import com.marius.valeyou_admin.ui.activity.dashboard.message.chatview.ChatActivityVM;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.ProfileActivityVM;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.businesshours.BusinessHoursActivityVM;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.editprofile.EditProfileActivityVM;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.education.AddEducationActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.education.AddEducationActivityVM;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.experience.AddExperienceActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.experience.AddExperienceActivityVM;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.indentity.ActivityIdentityVM;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.myservices.MyServicesActivityVM;
import com.marius.valeyou_admin.ui.activity.dashboard.review.sendreview.SendReviewActivityVM;
import com.marius.valeyou_admin.ui.activity.faq.FaqActivityVM;
import com.marius.valeyou_admin.ui.activity.forgot.ForgotPasswordActivityVM;
import com.marius.valeyou_admin.ui.activity.forgot.recoverpass.RecoverPasswordActivityVM;
import com.marius.valeyou_admin.ui.activity.login.LoginActivityVM;
import com.marius.valeyou_admin.ui.activity.main.MainActivityVM;
import com.marius.valeyou_admin.ui.activity.notification.NotificationActivityVM;
import com.marius.valeyou_admin.ui.activity.notification.notificationdetails.NotificationDetailsActivityVM;
import com.marius.valeyou_admin.ui.activity.profileproject.ProfileProjectActivityVM;
import com.marius.valeyou_admin.ui.activity.signup.SignupActivityVM;
import com.marius.valeyou_admin.ui.activity.signup.signuptwo.SignupTwoActivityVM;
import com.marius.valeyou_admin.ui.activity.signup.signuptwo.selectcategory.SelectCategoryActivityVM;
import com.marius.valeyou_admin.ui.activity.signup.uploaddocument.UploadDocumentActivityVM;
import com.marius.valeyou_admin.ui.activity.tourpage.TourActivityVM;
import com.marius.valeyou_admin.ui.activity.welcome.WelcomeActivityVM;
import com.marius.valeyou_admin.ui.fragment.myjob.startjob.StartJobTimerActivityVM;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.closedetails.JobDetailsActivityVM;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.complete.CompleteJobActivityVM;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.opendetails.CurrentJobDetailsActivityVM;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ActivityViewModelModule {
    // NOTE: customize here
    @Binds
    @IntoMap
    @ViewModelKey(WelcomeActivityVM.class)
    abstract ViewModel welcomeActivityVM(WelcomeActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityVM.class)
    abstract ViewModel LoginActivityVM(LoginActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(SignupActivityVM.class)
    abstract ViewModel SignupActivityVM(SignupActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordActivityVM.class)
    abstract ViewModel ForgotPasswordActivityVM(ForgotPasswordActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityVM.class)
    abstract ViewModel MainActivityVM(MainActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(TourActivityVM.class)
    abstract ViewModel TourActivityVM(TourActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(RecoverPasswordActivityVM.class)
    abstract ViewModel RecoverPasswordActivityVM(RecoverPasswordActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(SignupTwoActivityVM.class)
    abstract ViewModel SignupTwoActivityVM(SignupTwoActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(SelectCategoryActivityVM.class)
    abstract ViewModel SelectCategoryActivityVM(SelectCategoryActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(DashBoardActivityVM.class)
    abstract ViewModel DashBoardActivityVM(DashBoardActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(JobDetailsActivityVM.class)
    abstract ViewModel JobDetailsActivityVM(JobDetailsActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(CurrentJobDetailsActivityVM.class)
    abstract ViewModel CurrentJobDetailsActivityVM(CurrentJobDetailsActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(JobDetailsOneActivityVM.class)
    abstract ViewModel JobDetailsOneActivityVM(JobDetailsOneActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(SendReviewActivityVM.class)
    abstract ViewModel SendReviewActivityVM(SendReviewActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationDetailsActivityVM.class)
    abstract ViewModel NotificationDetailsActivityVM(NotificationDetailsActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ChatActivityVM.class)
    abstract ViewModel ChatActivityVM(ChatActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileActivityVM.class)
    abstract ViewModel ProfileActivityVM(ProfileActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileActivityVM.class)
    abstract ViewModel EditProfileActivityVM(EditProfileActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordFragmentVM.class)
    abstract ViewModel ChangePasswordFragmentVM(ChangePasswordFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(UploadDocumentActivityVM.class)
    abstract ViewModel UploadDocumentActivityVM(UploadDocumentActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileProjectActivityVM.class)
    abstract ViewModel ProfileProjectActivityVM(ProfileProjectActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationActivityVM.class)
    abstract ViewModel NotificationActivityVM(NotificationActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(StartJobTimerActivityVM.class)
    abstract ViewModel StartJobTimerActivityVM(StartJobTimerActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MyServicesActivityVM.class)
    abstract ViewModel MyServicesActivityVM(MyServicesActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ActivityIdentityVM.class)
    abstract ViewModel ActivityIdentityVM(ActivityIdentityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(BusinessHoursActivityVM.class)
    abstract ViewModel BusinessHoursActivityVM(BusinessHoursActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(FaqActivityVM.class)
    abstract ViewModel FaqActivityVM(FaqActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(CompleteJobActivityVM.class)
    abstract ViewModel CompleteJobActivityVM(CompleteJobActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(AddEducationActivityVM.class)
    abstract ViewModel AddEducationActivityVM(AddEducationActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(AddExperienceActivityVM.class)
    abstract ViewModel AddExperienceActivityVM(AddExperienceActivityVM vm);

}
