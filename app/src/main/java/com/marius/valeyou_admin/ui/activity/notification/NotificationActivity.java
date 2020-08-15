package com.marius.valeyou_admin.ui.activity.notification;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.marius.valeyou_admin.BR;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.base.MoreBean;
import com.marius.valeyou_admin.data.beans.notification.NotificationModel;
import com.marius.valeyou_admin.data.beans.singninbean.SocialSignIn;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.ActivityNotificationBinding;
import com.marius.valeyou_admin.databinding.HolderNotificationItemBinding;
import com.marius.valeyou_admin.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.di.base.view.AppFragment;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;
import com.marius.valeyou_admin.ui.activity.main.MainActivity;
import com.marius.valeyou_admin.ui.activity.notification.notificationdetails.NotificationDetailsActivity;
import com.marius.valeyou_admin.ui.activity.signup.SignupActivity;
import com.marius.valeyou_admin.util.Constants;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class NotificationActivity extends AppActivity<ActivityNotificationBinding, NotificationActivityVM> {
    // CONVERTED INTO THE FRAGMENT
    public static final String TAG = "NotificationActivity";

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<NotificationActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_notification, NotificationActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(NotificationActivityVM vm) {
        binding.header.tvTitle.setText("Notification");
        vm.getNotification(vm.sharedPref.getUserData().getAuthKey());
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });


        vm.notificationbean.observe(this, new SingleRequestEvent.RequestObserver<List<NotificationModel>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<NotificationModel>> resource) {
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
                        if (resource.message.equalsIgnoreCase("Bad Request")) {
                            vm.error.setValue("Incorrect Email Or Password");
                        }else{
                            vm.error.setValue(resource.message);
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

    private void setRecyclerView(List<NotificationModel> list) {
        if (list.size()>0) {
            binding.noTxt.setVisibility(View.GONE);
            binding.rvNotification.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            binding.rvNotification.setLayoutManager(layoutManager);
            binding.rvNotification.setAdapter(adapter);
            adapter.setList(list);
        }else{
            binding.noTxt.setVisibility(View.VISIBLE);
            binding.rvNotification.setVisibility(View.GONE);
        }
    }


    SimpleRecyclerViewAdapter<NotificationModel, HolderNotificationItemBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_notification_item, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<NotificationModel>() {
                @Override
                public void onItemClick(View v, NotificationModel o) {
                   /* Intent intent = NotificationDetailsActivity.newIntent(NotificationActivity.this);
                    startNewActivity(intent);*/
                }
            });



    private String calculateTime(String milis){

        String time="";

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");
            Date past = format.parse("1596189471");
            Date now = new Date();
            long seconds=TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if(seconds<60)
            {
               time = time + seconds+" seconds ago";
            }
            else if(minutes<60)
            {
                time = time + minutes+" minutes ago";
            }
            else if(hours<24)
            {
                time = time + hours+" hours ago";
            }
            else
            {
                time = time + days+" days ago";
            }

        }
        catch (Exception j){
            j.printStackTrace();
        }

        return time;
    }

}
