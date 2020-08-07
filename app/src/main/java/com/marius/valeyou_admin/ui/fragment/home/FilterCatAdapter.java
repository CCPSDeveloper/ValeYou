package com.marius.valeyou_admin.ui.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.marius.valeyou_admin.BR;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.business.BusinessHoursModel;
import com.marius.valeyou_admin.data.beans.categoriesBean.CategoryDataBean;
import com.marius.valeyou_admin.databinding.HolderSetCategoryBinding;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.businesshours.BusinessHoursAdapter;

import java.util.List;
import java.util.logging.Filter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FilterCatAdapter extends RecyclerView.Adapter<FilterCatAdapter.MyViewHolder> {

    Context context;
    List<CategoryDataBean> dataBeanList;
    public CheckCallback checkCallback;

    public FilterCatAdapter(Context context, FilterCatAdapter.CheckCallback checkCallback){
        this.context = context;
        this.checkCallback = checkCallback;
    }

    public interface CheckCallback {
        void onItemCheck(View v,int position, boolean isChecked, List<CategoryDataBean> list);
    }

    public void setCatList(List<CategoryDataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderSetCategoryBinding holderSetCategoryBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.holder_set_category, parent, false);
        holderSetCategoryBinding.setVariable(BR.callback, checkCallback);

        return new FilterCatAdapter.MyViewHolder(holderSetCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryDataBean categoryDataBean = dataBeanList.get(position);
        holder.holderSetCategoryBinding.setBean(categoryDataBean);


    }

    @Override
    public int getItemCount() {
        if (dataBeanList.size()>0){
            return dataBeanList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        HolderSetCategoryBinding holderSetCategoryBinding;
        public MyViewHolder(@NonNull HolderSetCategoryBinding itemView) {
            super(itemView.getRoot());
            holderSetCategoryBinding = itemView;
            holderSetCategoryBinding.catCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                  checkCallback.onItemCheck(buttonView,getAdapterPosition(),isChecked,dataBeanList);
                }
            });
        }
    }


}
