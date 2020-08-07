package com.marius.valeyou_admin.ui.fragment.myjob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.jobs.MyJobsModel;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.FragmentMyJobBinding;
import com.marius.valeyou_admin.di.base.adapter.PagerAdapter;
import com.marius.valeyou_admin.di.base.view.AppFragment;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;
import com.marius.valeyou_admin.ui.fragment.myjob.startjob.StartJobTimerActivity;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.UpComingJobFragment;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.UpComingJobsAdapter;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.closedetails.JobDetailsActivity;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.complete.CompleteJobActivity;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.opendetails.CurrentJobDetailsActivity;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.opendetails.closejobs.CloseJobsFragment;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

public class MyJobFragment extends AppFragment<FragmentMyJobBinding,MyJobFragmentVM> {

    public static final String TAG = "MyJobFragment";
    PopupWindow popupWindow;
    String authKey;

    public static Fragment newInstance() {
        return new MyJobFragment();
    }

    @Override
    protected BindingFragment<MyJobFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_my_job,MyJobFragmentVM.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        authKey = viewModel.sharedPref.getUserData().getAuthKey();

        if (!authKey.isEmpty()&&authKey!=null) {
            viewModel.myJobsApi(authKey, "0");
        }

    }

    @Override
    protected void subscribeToEvents(MyJobFragmentVM vm) {
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view!=null?view.getId():0){
                    case R.id.iv_filter:
                        showSortPopup(getActivity());
                        break;
                }
            }
        });



        vm.myJobsBean.observe(this, new SingleRequestEvent.RequestObserver<List<MyJobsModel>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<MyJobsModel>> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        setRecyclerView(resource.data);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorized")){
                            Intent intent1 = LoginActivity.newIntent(getActivity());
                            startNewActivity(intent1, true, true);

                        }
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

    }

    private void setRecyclerView(List<MyJobsModel> myJobsModelsList) {
        if (myJobsModelsList.size()>0) {
            binding.rvMyJobs.setVisibility(View.VISIBLE);
            binding.tvNoJob.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            binding.rvMyJobs.setLayoutManager(layoutManager);
            binding.rvMyJobs.setAdapter(mAdapter);
            mAdapter.setList(myJobsModelsList);
        }else{
            binding.rvMyJobs.setVisibility(View.GONE);
            binding.tvNoJob.setVisibility(View.VISIBLE);
        }
    }

    UpComingJobsAdapter mAdapter = new UpComingJobsAdapter(getActivity(), new UpComingJobsAdapter.ProductCallback() {
        @Override
        public void onItemClick(View v, MyJobsModel m) {

            switch (v.getId()){
                case R.id.ll_job_item_click:

                   if(m.getStatus()==1) {

                       Intent intent = CurrentJobDetailsActivity.newIntent(getActivity());
                       intent.putExtra("id", m.getId());
                       startNewActivity(intent);

                   }else if (m.getStatus()==3){

                       Intent intent = StartJobTimerActivity.newInstent(getActivity());
                       intent.putExtra("id", m.getId());
                       startNewActivity(intent);

                   }else if (m.getStatus()==4){

                       Intent intent = CompleteJobActivity.newIntent(getActivity());
                       intent.putExtra("id", m.getId());
                       startNewActivity(intent);

                   }

                    break;
            }

        }
    });

    private void showSortPopup(final Activity mContext) {
        LayoutInflater layoutInflater = (LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.window_popup_filter, null);
        popupWindow=new PopupWindow(popupView,
                300,  ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setElevation(10);

        popupWindow.showAtLocation(binding.ivFilter, Gravity.RIGHT|Gravity.TOP, 30, 450);
        popupView.findViewById(R.id.tv_all_jobs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (!authKey.isEmpty()&&authKey!=null) {
                    viewModel.myJobsApi(authKey, "0");
                }
            }
        });


        popupView.findViewById(R.id.tv_ongoing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (!authKey.isEmpty()&&authKey!=null) {
                    binding.tvNoJob.setVisibility(View.GONE);
                    viewModel.myJobsApi(authKey, "3");
                }
            }
        });


        popupView.findViewById(R.id.tv_completed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (!authKey.isEmpty()&&authKey!=null) {
                    binding.tvNoJob.setVisibility(View.GONE);
                    viewModel.myJobsApi(authKey, "4");
                }
            }
        });


        popupView.findViewById(R.id.tv_canceled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (!authKey.isEmpty()&&authKey!=null) {
                    binding.tvNoJob.setVisibility(View.GONE);
                    viewModel.myJobsApi(authKey, "2");
                }
            }
        });

        popupView.findViewById(R.id.tv_accepted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (!authKey.isEmpty()&&authKey!=null) {
                    binding.tvNoJob.setVisibility(View.GONE);
                    viewModel.myJobsApi(authKey, "1");
                }
            }
        });

    }

}
