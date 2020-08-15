package com.marius.valeyou.ui.fragment.message.chatview;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.chat.MessagesModel;
import com.marius.valeyou.data.beans.chat.UsersModel;
import com.marius.valeyou.data.beans.respbean.ProviderDetails;
import com.marius.valeyou.databinding.ActivityChatBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.di.socket.SocketManager;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ChatActivity extends AppActivity<ActivityChatBinding, ChatActivityVM> implements SocketManager.Observer{

    public SocketManager mSocketManager;
    UsersModel data;
    ProviderDetails data1;
    List<MessagesModel> messagesList = new ArrayList<>();
    MessagesModel model;
    ChatAdapter adapter;

    int otherUserId;
    String otherUserName;
    String ohterUserImage;

    List<MessagesModel> messageModelsList = new ArrayList<>();

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
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));


        Intent intent = getIntent();
        String comefrom = intent.getStringExtra("comeFrom");
        if (comefrom.equalsIgnoreCase("profile")){


            otherUserId = intent.getIntExtra("id",0);
            otherUserName = intent.getStringExtra("name");
            ohterUserImage = intent.getStringExtra("image");

        }else if (comefrom.equalsIgnoreCase("chat")){

            data = intent.getParcelableExtra("userData");
            otherUserId = data.getChat().getUser().getId();
            otherUserName = data.getChat().getUser().getName();
            ohterUserImage = data.getChat().getUser().getImage();

        }

        binding.tvName.setText(otherUserName);
        ImageViewBindingUtils.setProfileUrl(binding.imgUser,"http://3.17.254.50:4999/upload/"+ohterUserImage);


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
    private void setupAdater(List<MessagesModel> list) {
        if (list.size() > 0) {

            int id = sharedPref.getUserData().getId();
            Collections.reverse(list);
            adapter = new ChatAdapter(ChatActivity.this, list,id);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
            binding.rvChatList.setLayoutManager(linearLayoutManager);
            if (list.size() > 2){
                binding.rvChatList.smoothScrollToPosition(adapter.getItemCount()-1);
            } else {
                binding.rvChatList.smoothScrollToPosition(adapter.getItemCount());
            }
            binding.rvChatList.setAdapter(adapter);


        }
    }

    private void getMessagesList(){
        try {
            binding.swipeRefreshLayout.setRefreshing(true);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.SENDERID, sharedPref.getUserData().getId());
            jsonObject.put(Constants.RECIEVERID, otherUserId);
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
                Type type = TypeToken.getParameterized(ArrayList.class, MessagesModel.class).getType();
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

                model = new Gson().fromJson(args.toString(), MessagesModel.class);

                if (adapter!=null){
                    adapter.sendNewMessage(model);
                    binding.rvChatList.smoothScrollToPosition(adapter.getItemCount()-1);
                }

            }
        });
    }


    private JSONObject getJsonData(){
        String timestamp =  (System.currentTimeMillis()/1000)+"";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.SENDERID, sharedPref.getUserData().getId());
            jsonObject.put(Constants.RECIEVERID, otherUserId);
            jsonObject.put(Constants.MESSAGE, binding.etChat.getText().toString());
            jsonObject.put(Constants.USERNAME, sharedPref.getUserData().getFirstName()+" "+sharedPref.getUserData().getLastName());
            jsonObject.put(Constants.USERIMAGE, sharedPref.getUserData().getImage());
            jsonObject.put(Constants.RECIEVERNAME,otherUserName);
            jsonObject.put(Constants.RECIEVERIMAGE,ohterUserImage);
            jsonObject.put(Constants.MSGTYPE, 0);
            jsonObject.put(Constants.TIMESTAMP, timestamp);
            jsonObject.put(Constants.TYPE, 0);

            return jsonObject;
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }


    }
}
