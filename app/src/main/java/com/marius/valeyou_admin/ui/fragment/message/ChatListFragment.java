package com.marius.valeyou_admin.ui.fragment.message;

import android.content.Intent;
import android.view.View;

import com.marius.valeyou_admin.BR;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.base.MoreBean;
import com.marius.valeyou_admin.databinding.FragmentChatListBinding;
import com.marius.valeyou_admin.databinding.HolderChatListBinding;
import com.marius.valeyou_admin.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou_admin.di.base.view.AppFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.message.chatview.ChatActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ChatListFragment extends AppFragment<FragmentChatListBinding, ChatListFragmentVM> {

    public static final String TAG = "ChatListFragment";

    public static Fragment newInstance() {
        return new ChatListFragment();
    }

    @Override
    protected BindingFragment<ChatListFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_chat_list, ChatListFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(ChatListFragmentVM vm) {
        setRecyclerView();
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvChatList.setLayoutManager(layoutManager);
        binding.rvChatList.setAdapter(adapter);
        adapter.setList(getList());
    }

    SimpleRecyclerViewAdapter<MoreBean, HolderChatListBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_chat_list, BR.bean,
                    new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
                @Override
                public void onItemClick(View v, MoreBean o) {
                    Intent intent = ChatActivity.newIntent(getActivity());
                    startNewActivity(intent);
                }
            });

    private List<MoreBean> getList() {
        List<MoreBean> moreBeans = new ArrayList<>();
        moreBeans.add(new MoreBean(1,"",1));
        moreBeans.add(new MoreBean(1,"",1));
        moreBeans.add(new MoreBean(1,"",1));
        moreBeans.add(new MoreBean(1,"",1));
        moreBeans.add(new MoreBean(1,"",1));
        return moreBeans;
    }

}
