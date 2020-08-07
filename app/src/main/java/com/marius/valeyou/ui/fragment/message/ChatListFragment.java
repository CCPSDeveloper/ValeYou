package com.marius.valeyou.ui.fragment.message;

import android.content.Intent;
import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.databinding.FragmentChatListBinding;
import com.marius.valeyou.databinding.HolderChatListBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.message.chatview.ChatActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ChatListFragment extends AppFragment<FragmentChatListBinding, ChatListFragmentVM> {

    public static final String TAG = "CMhatListFragment";

    private MainActivity mainActivity;

    public static Fragment newInstance() {
        return new ChatListFragment();
    }

    @Override
    protected BindingFragment<ChatListFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_chat_list, ChatListFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(ChatListFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
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
