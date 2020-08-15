package com.marius.valeyou_admin.ui.fragment.message;

import android.content.Context;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marius.valeyou_admin.BR;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.chat.UsersModel;
import com.marius.valeyou_admin.data.beans.faq.FaqModel;
import com.marius.valeyou_admin.databinding.HolderChatListBinding;
import com.marius.valeyou_admin.databinding.HolderFaqBinding;
import com.marius.valeyou_admin.di.module.GlideApp;
import com.marius.valeyou_admin.ui.activity.faq.faqAdapter;
import com.marius.valeyou_admin.util.databinding.ImageViewBindingUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ChatUsersAdapter extends RecyclerView.Adapter<ChatUsersAdapter.MyViewHolder> {
    Context context;
    List<UsersModel> usersModelList;
    public Listner listner;

    public interface Listner {
        void onItemClick(View v, int position, UsersModel model);
    }

    public ChatUsersAdapter(Context context,List<UsersModel> usersModelList,Listner listner){
        this.context =context;
        this.usersModelList = usersModelList;
        this.listner = listner;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        HolderChatListBinding holderChatListBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.holder_chat_list, parent, false);
        holderChatListBinding.setVariable(BR.callback, listner);

        return new ChatUsersAdapter.MyViewHolder(holderChatListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UsersModel model = usersModelList.get(position);
        holder.holderChatListBinding.tvName.setText(model.getChat().getUser().getName());
        holder.holderChatListBinding.message.setText(model.getChat().getMessage());
        ImageViewBindingUtils.setProfileUrl(holder.holderChatListBinding.image,"http://3.17.254.50:4999/upload/"+model.getChat().getUser().getImage());

        if (model.getChat().getUnread_message() > 0){
            holder.holderChatListBinding.message.setTextColor(context.getResources().getColor(R.color.black));
            holder.holderChatListBinding.message.setTypeface(holder.holderChatListBinding.message.getTypeface(), Typeface.BOLD);
        }

        holder.holderChatListBinding.tvTime.setText(convertTimeStampIntoDateTime(model.getChat().getCreatedAt()));

    }

    @Override
    public int getItemCount() {
        return usersModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        HolderChatListBinding holderChatListBinding;
        public MyViewHolder(@NonNull HolderChatListBinding itemView) {
            super(itemView.getRoot());
            holderChatListBinding = itemView;
            holderChatListBinding.llItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onItemClick(v,getAdapterPosition(),usersModelList.get(getAdapterPosition()));
                }
            });
        }
    }

    private String convertTimeStampIntoDateTime(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp*1000L);
        String date = DateFormat.format("MMM dd, yyyy \n hh:mm a", cal).toString();

        return date;
    }
}
