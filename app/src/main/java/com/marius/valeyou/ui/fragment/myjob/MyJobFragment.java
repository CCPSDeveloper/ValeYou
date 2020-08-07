package com.marius.valeyou.ui.fragment.myjob;

import android.content.Intent;
import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.GetAllJobBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentMyJobBinding;
import com.marius.valeyou.databinding.HolderUpcomingJobBinding;
import com.marius.valeyou.di.base.adapter.PagerAdapter;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.selectcategory.SelectCategoryActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.JobDetailsFragment;
import com.marius.valeyou.ui.fragment.myjob.upcoming.UpComingJobFragment;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

public class MyJobFragment extends AppFragment<FragmentMyJobBinding, MyJobFragmentVM> {

    public static final String TAG = "MyJobFragment";
    private MainActivity mainActivity;

    private int page = 1;
    private int type = 0;
    private List<GetAllJobBean> getAllJobBeans;

    public static Fragment newInstance() {
        return new MyJobFragment();
    }

    @Override
    protected BindingFragment<MyJobFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_my_job, MyJobFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(MyJobFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.hideBackButton();
        mainActivity.setHeader("My Jobs");
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {
                    case R.id.ll_one:
                        binding.vpJob.setCurrentItem(0);
                        break;
                    case R.id.ll_two:
                        binding.vpJob.setCurrentItem(1);
                        break;
                    case R.id.fab_add_jobb:
                        //mainActivity.addSubFragment(TAG, ProductFragment.newInstance());
                        Intent intent = SelectCategoryActivity.newIntent(getActivity(),0);
                        startNewActivity(intent);
                        break;
                    case R.id.iv_filter:
                        binding.spinner.performClick();
                        break;
                }
            }
        });

        vm.text.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String value) {
                if (value.equalsIgnoreCase("All Jobs")) {
                    type = 0;
                }
                if (value.equalsIgnoreCase("Upcoming")) {
                    type = 1;
                }
                if (value.equalsIgnoreCase("Canceled")) {
                    type = 2;
                }
                if (value.equalsIgnoreCase("Ongoing")) {
                    type = 3;
                }
                if (value.equalsIgnoreCase("Completed")) {
                    type = 4;
                }
                vm.getAllJobList("", "", String.valueOf(type));
            }
        });

        vm.jobListEvent.observe(this, new SingleRequestEvent.RequestObserver<List<GetAllJobBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<GetAllJobBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setLoading(true);
                        break;
                    case SUCCESS:
                        binding.setLoading(false);
                        getAllJobBeans = resource.data;
                        adapter.setList(getAllJobBeans);

                        break;
                    case WARN:
                        binding.setLoading(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setLoading(false);
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorized")){
                            Intent intent1 = LoginActivity.newIntent(getActivity());
                            startNewActivity(intent1, true, true);

                        }
                        break;
                }
            }
        });
        setViewPager();
        setRecyclerView();
    }

    private List<Fragment> fragmentList;

    private void setViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(UpComingJobFragment.newInstance());
        fragmentList.add(UpComingJobFragment.newInstance());
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), fragmentList);
        binding.vpJob.setAdapter(pagerAdapter);
        binding.vpJob.setCurrentItem(0);
        binding.vpJob.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.setCheck(false);
                        break;
                    case 1:
                        binding.setCheck(true);
                        break;
                }
            }
        });
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvUpcoming.setLayoutManager(layoutManager);
        binding.rvUpcoming.setAdapter(adapter);
        if (getAllJobBeans != null)
            adapter.setList(getAllJobBeans);
    }

    SimpleRecyclerViewAdapter<GetAllJobBean, HolderUpcomingJobBinding> adapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_upcoming_job, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<GetAllJobBean>() {
        @Override
        public void onItemClick(View v, GetAllJobBean bean) {
            mainActivity.addSubFragment(TAG, JobDetailsFragment.newInstance(bean));
        }
    });

}
