package com.marius.valeyou.ui.fragment.message.chatview;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.chat.MessagesModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter{

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    Context context;
    List<MessagesModel>list;
    int login_id;

    public ChatAdapter(Context context, List<MessagesModel>list, int login_id){
        this.context = context;
        this.list = list;
        this.login_id = login_id;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == MSG_TYPE_RIGHT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_message_sent, parent, false);
            return new SentMessageHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_message_recive, parent, false);
            return new ReceivedMessageHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case MSG_TYPE_RIGHT:
                ((SentMessageHolder) holder).bind(list.get(position));
                break;
            case MSG_TYPE_LEFT:
                ((ReceivedMessageHolder) holder).bind(list.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        MessagesModel message = list.get(position);
        if (message.getUserId()==login_id) {
            return MSG_TYPE_RIGHT ;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    public void sendNewMessage(MessagesModel model){
        list.add(model);
        notifyDataSetChanged();
    }

    public void updateList(List<MessagesModel> model){
        list.addAll(model);
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText,txtTime;
        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            txtTime=  itemView.findViewById(R.id.timetxt);


        }
        void bind(final MessagesModel message) {

            messageText.setText(message.getMessage());
            txtTime.setText(convertTimeStampIntoDateTime(message.getCreatedAt()));

        }
    }
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText,txtTime;
        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText =  itemView.findViewById(R.id.text_message_body);
            txtTime=  itemView.findViewById(R.id.timetxt);

        }
        void bind(MessagesModel message) {
            messageText.setText(message.getMessage());
            txtTime.setText(convertTimeStampIntoDateTime(message.getCreatedAt()));


        }
    }

    private String convertTimeStampIntoDateTime(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp*1000L);
        String date = DateFormat.format("MMM dd, hh:mm a", cal).toString();

        return date;
    }

}
