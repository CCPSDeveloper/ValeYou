package com.marius.valeyou.ui.fragment.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.chat.UsersModel;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.FragmentChatListBinding;
import com.marius.valeyou.databinding.HolderChatListBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.di.socket.SocketManager;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.message.chatview.ChatActivity;
import com.marius.valeyou.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ChatListFragment extends AppFragment<FragmentChatListBinding, ChatListFragmentVM> implements SocketManager.Observer {

    @Inject
    SharedPref sharedPref;
    public static final String TAG = "CAhatListFragment";
    SocketManager mSocketManager;
    List<UsersModel> usersList = new ArrayList<>();
    public static Fragment newInstance() {
        return new ChatListFragment();
    }

    @Override
    protected BindingFragment<ChatListFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_chat_list, ChatListFragmentVM.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mSocketManager == null) {
            mSocketManager = new SocketManager(getActivity(),this);
        }
        if (mSocketManager.getmSocket()==null){
            mSocketManager.init();
        }
    }

    @Override
    protected void subscribeToEvents(ChatListFragmentVM vm) {


    }

    @Override
    public void onResume() {
        super.onResume();
        getChatList();
    }

    private void setRecyclerView(List<UsersModel> list) {
        if (list.size()>0) {
            binding.tvNoData.setVisibility(View.GONE);
            binding.rvChatList.setVisibility(View.VISIBLE);
            ChatUsersAdapter chatUsersAdapter = new ChatUsersAdapter(getContext(), list, new ChatUsersAdapter.Listner() {
                @Override
                public void onItemClick(View v, int position, UsersModel model) {
                     Intent intent = ChatActivity.newIntent(getActivity());
                     intent.putExtra("comeFrom","chat");
                     intent.putExtra("userData",model);
                     startNewActivity(intent);
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            binding.rvChatList.setLayoutManager(layoutManager);
            binding.rvChatList.setAdapter(chatUsersAdapter);
        }else{

            binding.tvNoData.setVisibility(View.VISIBLE);
            binding.rvChatList.setVisibility(View.GONE);

        }
    }




    public void getChatList(){
        try {
            binding.setCheck(true);
            JSONObject jsonObject =  new JSONObject();
            jsonObject.put(Constants.SOCKET_USER_ID, sharedPref.getUserData().getId());
            jsonObject.put(Constants.TYPE, "0");
            jsonObject.put(Constants.PAGE,"1");
            jsonObject.put(Constants.LIMIT,"10");
            mSocketManager.getChatList(jsonObject);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void getChatList(String event, JSONArray args) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.setCheck(false);
                usersList.clear();
                Type type = TypeToken.getParameterized(ArrayList.class, UsersModel.class).getType();
                usersList = new Gson().fromJson(args.toString(), type);

                setRecyclerView(usersList);
            }
        });
    }

    @Override
    public void getMessages(String event, JSONArray args) {

    }

    @Override
    public void sendMessageResponse(String event, JSONObject args) {

    }
}
