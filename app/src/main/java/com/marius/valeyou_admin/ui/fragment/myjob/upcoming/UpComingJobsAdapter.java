package com.marius.valeyou_admin.ui.fragment.myjob.upcoming;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marius.valeyou_admin.BR;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.categoriesBean.CategoryDataBean;
import com.marius.valeyou_admin.data.beans.jobs.MyJobsModel;
import com.marius.valeyou_admin.databinding.HolderMyServicesListBinding;
import com.marius.valeyou_admin.databinding.HolderUpcomingJobBinding;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.myservices.MyServicesAdapter;
import com.marius.valeyou_admin.util.databinding.ImageViewBindingUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class UpComingJobsAdapter extends RecyclerView.Adapter<UpComingJobsAdapter.MyViewHolder> {

    private List<MyJobsModel> myJobsModelsLsit;
    private UpComingJobsAdapter.ProductCallback mcallback;
    private Context baseContext;

    public UpComingJobsAdapter(Context baseContext, UpComingJobsAdapter.ProductCallback callback) {
        this.baseContext = baseContext;
        this.mcallback = callback;
    }

    public interface ProductCallback {
        void onItemClick(View v, MyJobsModel m);
    }

    @NonNull
    @Override
    public UpComingJobsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderUpcomingJobBinding holderUpcomingJobBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.holder_upcoming_job, parent, false);
        holderUpcomingJobBinding.setVariable(BR.callback, mcallback);

        return new UpComingJobsAdapter.MyViewHolder(holderUpcomingJobBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingJobsAdapter.MyViewHolder holder, int position) {

        MyJobsModel model = myJobsModelsLsit.get(position);
        holder.holderUpcomingJobBinding.setBean(model);


        if (model.getOrderImages().size()>0) {
            String image = model.getOrderImages().get(0).getImages();
            ImageViewBindingUtils.setImage(holder.holderUpcomingJobBinding.jobImage,"http://3.17.254.50:4999/upload/"+image);

        }

        int jobStatus = model.getStatus();
        if (jobStatus == 1){
            holder.holderUpcomingJobBinding.txtStatus.setText("Accepted");
            holder.holderUpcomingJobBinding.txtStatus.setBackgroundColor(Color.parseColor("#aad9b9"));
        }else if (jobStatus == 3){
            holder.holderUpcomingJobBinding.txtStatus.setText("Ongoing");
            holder.holderUpcomingJobBinding.txtStatus.setBackgroundColor(Color.parseColor("#FDB537"));
        }else if (jobStatus == 2){
            holder.holderUpcomingJobBinding.txtStatus.setText("Canceled");
            holder.holderUpcomingJobBinding.txtStatus.setBackgroundColor(Color.parseColor("#FFF44336"));
        }else if (jobStatus == 4){
            holder.holderUpcomingJobBinding.txtStatus.setText("Completed");
            holder.holderUpcomingJobBinding.txtStatus.setBackgroundColor(Color.parseColor("#0D9211"));
        }


        if (!model.getDate().isEmpty()) {
            long timeMilis = Long.parseLong(model.getDate());
            String dateTime = convertTimeStampToTime(timeMilis);
            holder.holderUpcomingJobBinding.txtTimeDate.setText(dateTime);
        }
    }

    @Override
    public int getItemCount() {
        if (myJobsModelsLsit != null) {
            return myJobsModelsLsit.size();
        } else {
            return 0;
        }
    }

    public void setList(List<MyJobsModel> myJobsModelsLsit) {
        this.myJobsModelsLsit = myJobsModelsLsit;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private HolderUpcomingJobBinding holderUpcomingJobBinding;
        public MyViewHolder(@NonNull HolderUpcomingJobBinding itemView) {
            super(itemView.getRoot());
            this.holderUpcomingJobBinding = itemView;
        }
    }

    private String convertTimeStampToTime(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000L);
        String date = DateFormat.format("MMM dd, yyyy ", cal).toString();
        return date;
    }
}
