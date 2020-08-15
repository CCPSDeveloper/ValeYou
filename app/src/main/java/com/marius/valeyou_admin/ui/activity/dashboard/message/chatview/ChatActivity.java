package com.marius.valeyou_admin.ui.activity.dashboard.message.chatview;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.chat.MessageModel;
import com.marius.valeyou_admin.data.beans.chat.UsersModel;
import com.marius.valeyou_admin.databinding.ActivityChatBinding;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.di.socket.SocketManager;
import com.marius.valeyou_admin.util.Constants;
import com.marius.valeyou_admin.util.databinding.ImageViewBindingUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ChatActivity extends AppActivity<ActivityChatBinding, ChatActivityVM> implements SocketManager.Observer{
    public SocketManager mSocketManager;
    UsersModel data;
    List<MessageModel> messagesList = new ArrayList<>();
    MessageModel model;
    ChatAdapter adapter;
    public int currentpage = 1;
    List<MessageModel> messageModelsList = new ArrayList<>();

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<ChatActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_chat, ChatActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(ChatActivityVM vm) {
        binding.header.tvTitle.setText("Message");

        Intent intent = getIntent();
        if (intent!=null) {
            String comeFrom = intent.getStringExtra("comeFrom");
            if (comeFrom.equalsIgnoreCase("chat")) {
                data = getIntent().getParcelableExtra("userData");
            }else if (comeFrom.equalsIgnoreCase("job")){

            }

        }
        binding.tvName.setText(data.getChat().getUser().getName());
        ImageViewBindingUtils.setProfileUrl(binding.imgUser,"http://3.17.254.50:4999/upload/"+data.getChat().getUser().getImage());

        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view!=null?view.getId():0) {
                    case R.id.iv_send:
                        if (binding.etChat.getText().toString().isEmpty()){

                            vm.info.setValue("Type your message");

                        }else{

                            JSONObject jsonObject = getJsonData();
                            if (jsonObject!=null) {
                                mSocketManager.sendMessage(jsonObject);
                                binding.etChat.setText("");
                            }

                        }
                        break;
                }
            }
        });

        if (mSocketManager == null) {
            mSocketManager = new SocketManager(this,this);
        }
        if (mSocketManager.getmSocket()==null){
            mSocketManager.init();
        }

        getMessagesList();

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMessagesList();
            }
        });

    }

    private void resumeInit() {
        if (mSocketManager!=null) {
            mSocketManager.onRegister(this);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mSocketManager!=null) {
            mSocketManager.unRegister(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeInit();
    }

    private void setupAdater(List<MessageModel> list) {
        if (list.size() > 0) {

            int id = sharedPref.getUserData().getId();
            Collections.reverse(list);
            adapter = new ChatAdapter(ChatActivity.this, list,id);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
            binding.rvMessageList.setLayoutManager(linearLayoutManager);
            if (list.size() > 2){
                binding.rvMessageList.smoothScrollToPosition(adapter.getItemCount()-1);
            } else {
                binding.rvMessageList.smoothScrollToPosition(adapter.getItemCount());
            }
            binding.rvMessageList.setAdapter(adapter);


        }
    }


    private void getMessagesList(){
        try {
            binding.swipeRefreshLayout.setRefreshing(true);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.SENDERID, sharedPref.getUserData().getId());
            jsonObject.put(Constants.RECIEVERID, data.getChat().getUser().getId());
            jsonObject.put(Constants.PAGE, "");
            jsonObject.put(Constants.LIMIT, "");
            mSocketManager.getMessageList(jsonObject);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    @Override
    public void getChatList(String event, JSONArray args) {

    }

    @Override
    public void getMessages(String event, JSONArray args) {
        binding.swipeRefreshLayout.setRefreshing(false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messagesList.clear();
                Type type = TypeToken.getParameterized(ArrayList.class, MessageModel.class).getType();
                messagesList = new Gson().fromJson(args.toString(), type);

                setupAdater(messagesList);
            }
        });
    }

    @Override
    public void sendMessageResponse(String event, JSONObject args) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                model = new Gson().fromJson(args.toString(), MessageModel.class);

                if (adapter!=null){
                    adapter.sendNewMessage(model);
                    binding.rvMessageList.smoothScrollToPosition(adapter.getItemCount()-1);
                }

            }
        });
    }


    private JSONObject getJsonData(){
        String timestamp =  (System.currentTimeMillis()/1000)+"";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.SENDERID, sharedPref.getUserData().getId());
            jsonObject.put(Constants.RECIEVERID, data.getChat().getUser().getId());
            jsonObject.put(Constants.MESSAGE, binding.etChat.getText().toString());
            jsonObject.put(Constants.USERNAME, sharedPref.getUserData().getFirstName()+" "+sharedPref.getUserData().getLastName());
            jsonObject.put(Constants.USERIMAGE, sharedPref.getUserData().getImage());
            jsonObject.put(Constants.RECIEVERNAME,data.getChat().getUser().getName());
            jsonObject.put(Constants.RECIEVERIMAGE,data.getChat().getUser().getImage());
            jsonObject.put(Constants.MSGTYPE, 0);
            jsonObject.put(Constants.TIMESTAMP, timestamp);
            jsonObject.put(Constants.TYPE, 1);

            return jsonObject;
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }


    }
}
