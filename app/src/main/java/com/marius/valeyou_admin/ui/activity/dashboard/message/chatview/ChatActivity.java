package com.marius.valeyou_admin.ui.activity.dashboard.message.chatview;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.databinding.ActivityChatBinding;
import com.marius.valeyou_admin.di.base.view.AppActivity;

import androidx.lifecycle.Observer;

public class ChatActivity extends AppActivity<ActivityChatBinding, ChatActivityVM> {
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
                        binding.etChat.setText("");
                        break;
                }
            }
        });

    }
}
