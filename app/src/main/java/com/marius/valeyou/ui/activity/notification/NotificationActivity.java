package com.marius.valeyou.ui.activity.notification;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.GetNotificationList;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityNotificationBinding;
import com.marius.valeyou.databinding.HolderNotificationItemBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.PayPerHourActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class NotificationActivity extends AppActivity<ActivityNotificationBinding, NotificationActivityVM> {

    private List<GetNotificationList> getNotificationLists;

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
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        vm.getNotification();
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.singleRequestEvent.observe(this, new SingleRequestEvent.RequestObserver<List<GetNotificationList>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<GetNotificationList>> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        getNotificationLists = resource.data;
                        adapter.setList(getNotificationLists);
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorized")){
                            Intent intent1 = LoginActivity.newIntent(NotificationActivity.this);
                            startNewActivity(intent1, true, true);

                        }
                        break;
                }
            }
        });

        vm.readNotification.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);
                        vm.getNotification();
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        setRecyclerView();
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
        binding.rvNotification.setLayoutManager(layoutManager);
        binding.rvNotification.setAdapter(adapter);
        if (getNotificationLists != null)
            adapter.setList(getNotificationLists);
    }

    SimpleRecyclerViewAdapter<GetNotificationList, HolderNotificationItemBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_notification_item, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<GetNotificationList>() {
                @Override
                public void onItemClick(View v, GetNotificationList moreBean) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.tv_read:
                            viewModel.readNotification("1", moreBean.getId());
                            break;
                        case R.id.cv_items:
                            if (moreBean.getType() == 5) {
                                Intent intent = PayPerHourActivity.newIntent(NotificationActivity.this);
                                startNewActivity(intent);
                            }
                            break;
                    }
                }
            });

}
