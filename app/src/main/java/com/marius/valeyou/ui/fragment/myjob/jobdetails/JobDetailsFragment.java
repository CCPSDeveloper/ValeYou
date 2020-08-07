package com.marius.valeyou.ui.fragment.myjob.jobdetails;

import android.os.Bundle;
import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.BidsBean;
import com.marius.valeyou.data.beans.respbean.GetAllJobBean;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentJobDetailsBinding;
import com.marius.valeyou.databinding.HolderBiddesItemsBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragment;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.InvoiceFragment;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class JobDetailsFragment extends AppFragment<FragmentJobDetailsBinding, JobDetailsFragmentVM> {

    private static final String BEAN = "bean";
    private MainActivity mainActivity;
    private String SUB_TAG = MyJobFragment.TAG;
    private GetAllJobBean getAllJobBean;
    private List<BidsBean> bidList;
    private int type = 1;

    public static Fragment newInstance(GetAllJobBean bean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BEAN, bean);
        JobDetailsFragment fragment = new JobDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BindingFragment<JobDetailsFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_job_details, JobDetailsFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(JobDetailsFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader("Job Details");
        getAllJobBean = getArguments().getParcelable(BEAN);
        vm.getJobDetails(getAllJobBean.getId());
        vm.jobDetailsEvent.observe(this, new SingleRequestEvent.RequestObserver<JobDetailsBean>() {
            @Override
            public void onRequestReceived(@NonNull Resource<JobDetailsBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setLoading(true);
                        break;
                    case SUCCESS:
                        bidList = resource.data.getBids();
                        binding.setBean(resource.data);
                        if (bidList != null && bidList.size() > 0) {
                            binding.tvBids.setVisibility(View.GONE);
                            adapter.setList(bidList);
                        }


                        if(resource.data.getJobType() ==0){

                            for (int i=0;i<bidList.size();i++) {
                                if (bidList.get(i).getStatus() == 4) {

                                    binding.rvBids.setVisibility(View.GONE);
                                    binding.tvBids.setVisibility(View.VISIBLE);
                                }
                            }

                        }else if (resource.data.getJobType() == 1){
                            for (int i=0;i<bidList.size();i++) {
                                if (bidList.get(i).getStatus() == 1) {

                                    binding.rvBids.setVisibility(View.GONE);
                                    binding.tvBids.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        binding.setLoading(false);
                        break;
                    case WARN:
                        binding.setLoading(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setLoading(false);
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });
        vm.acceptRejectEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setLoading(true);
                        break;
                    case SUCCESS:
                        if (type == 1) {
                            if (getAllJobBean.getJobType() == 0) {
                                mainActivity.addSubFragment(SUB_TAG, InvoiceFragment.newInstance(getAllJobBean));
                            } else {
                                vm.success.setValue(resource.message);
                                mainActivity.onBackPressed();
                            }
                        } else {
                            vm.getJobDetails(getAllJobBean.getId());
                        }
                        binding.setLoading(false);
                        break;
                    case WARN:
                        binding.setLoading(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setLoading(false);
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });
        setRecyclerView();
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvBids.setLayoutManager(layoutManager);
        binding.rvBids.setAdapter(adapter);
    }

    SimpleRecyclerViewAdapter<BidsBean, HolderBiddesItemsBinding> adapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_biddes_items, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<BidsBean>() {
                @Override
                public void onItemClick(View v, BidsBean bidsBean) {
                    Map<String, Integer> data = new HashMap<>();
                    data.put("post_id", getAllJobBean.getId());
                    data.put("provider_id", bidsBean.getProviderId());
                    switch (v != null ? v.getId() : 0) {
                        case R.id.cv_message:
                            viewModel.info.setValue("message");
                            break;
                        case R.id.cv_accept:
                            type = 1;
                            data.put("type", type);
                            viewModel.acceptRejectBid(data);
                            break;
                        case R.id.cv_decline:
                            type = 2;
                            data.put("type", type);
                            viewModel.acceptRejectBid(data);
                            break;
                    }
                }
            });

}
