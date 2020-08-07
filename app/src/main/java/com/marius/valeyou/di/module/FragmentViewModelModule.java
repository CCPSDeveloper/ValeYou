package com.marius.valeyou.di.module;

import androidx.lifecycle.ViewModel;

import com.marius.valeyou.di.mapkey.ViewModelKey;
import com.marius.valeyou.ui.activity.tourpage.tourfragment.TourFragmentVM;
import com.marius.valeyou.ui.fragment.home.HomeFragmentVM;
import com.marius.valeyou.ui.fragment.loadtype.StandardPickFragmentVM;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.JunkRemovalJobFragmentVM;
import com.marius.valeyou.ui.fragment.message.ChatListFragmentVM;
import com.marius.valeyou.ui.fragment.more.MoreFragmentVM;
import com.marius.valeyou.ui.fragment.favourite.MyMFragmentVM;
import com.marius.valeyou.ui.fragment.more.aboutus.AboutUsFragmentVM;
import com.marius.valeyou.ui.fragment.more.helpnsupport.HelpAndSupportFragmentVM;
import com.marius.valeyou.ui.fragment.more.privacy_policy.PrivacyPolicyFragmentVM;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragmentVM;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.JobDetailsFragmentVM;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.InvoiceFragmentVM;
import com.marius.valeyou.ui.fragment.myjob.upcoming.UpComingJobFragmentVM;
import com.marius.valeyou.ui.fragment.products.ProductFragmentVM;
import com.marius.valeyou.ui.fragment.products.junkremove.JunkRemovalFragmentVM;
import com.marius.valeyou.ui.fragment.restaurant.RestaurantFragmentVM;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class FragmentViewModelModule {
    // NOTE: customize here

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentVM.class)
    abstract ViewModel HomeFragmentVM(HomeFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MyMFragmentVM.class)
    abstract ViewModel MyMFragmentVM(MyMFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ProductFragmentVM.class)
    abstract ViewModel ProductFragmentVM(ProductFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantFragmentVM.class)
    abstract ViewModel RestaurantFragmentVM(RestaurantFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MoreFragmentVM.class)
    abstract ViewModel MoreFragmentVM(MoreFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(TourFragmentVM.class)
    abstract ViewModel TourFragmentVM(TourFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ChatListFragmentVM.class)
    abstract ViewModel ChatListFragmentVM(ChatListFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MyJobFragmentVM.class)
    abstract ViewModel MyJobFragmentVM(MyJobFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(UpComingJobFragmentVM.class)
    abstract ViewModel UpComingJobFragmentVM(UpComingJobFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(AboutUsFragmentVM.class)
    abstract ViewModel AboutUsFragmentVM(AboutUsFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(PrivacyPolicyFragmentVM.class)
    abstract ViewModel PrivacyPolicyFragmentVM(PrivacyPolicyFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(HelpAndSupportFragmentVM.class)
    abstract ViewModel HelpAndSupportFragmentVM(HelpAndSupportFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(JunkRemovalFragmentVM.class)
    abstract ViewModel JunkRemovalFragmentVM(JunkRemovalFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(StandardPickFragmentVM.class)
    abstract ViewModel StandardPickFragmentVM(StandardPickFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(JunkRemovalJobFragmentVM.class)
    abstract ViewModel JunkRemovalJobFragmentVM(JunkRemovalJobFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(JobDetailsFragmentVM.class)
    abstract ViewModel JobDetailsFragmentVM(JobDetailsFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(InvoiceFragmentVM.class)
    abstract ViewModel InvoiceFragmentVM(InvoiceFragmentVM vm);

}
