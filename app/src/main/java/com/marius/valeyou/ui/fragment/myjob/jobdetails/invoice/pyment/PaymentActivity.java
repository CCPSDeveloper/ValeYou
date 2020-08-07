package com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.respbean.GetCardBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityPaymentBinding;
import com.marius.valeyou.databinding.HolderSavedCardBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails.PaymentCardDetails;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails.paymentsuccess.PaymentSuccessActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class PaymentActivity extends AppActivity<ActivityPaymentBinding, PaymentActivityVM> {

    private List<GetCardBean> getCardBeans;

    public static Intent newIntnt(Activity activity) {
        Intent intent = new Intent(activity, PaymentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<PaymentActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_payment, PaymentActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(PaymentActivityVM vm) {
        vm.getUserCard();
        binding.header.tvTitle.setText("Payment");
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
                Intent intent;
                switch (view != null ? view.getId() : 0) {
                    case R.id.btn_pay:
                        intent = PaymentSuccessActivity.newIntent(PaymentActivity.this);
                        startNewActivity(intent);
                        break;
                    case R.id.btn_cancel:
                        break;
                    case R.id.tv_add_newcard:
                        intent = PaymentCardDetails.newIntent(PaymentActivity.this);
                        startNewActivity(intent);
                        break;
                }
            }
        });

        vm.singleRequestEvent.observe(this, new SingleRequestEvent.RequestObserver<List<GetCardBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<GetCardBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        getCardBeans = resource.data;
                        if (getCardBeans != null && getCardBeans.size() > 0)
                            getCardBeans.get(0).setStatus(1);
                        adapter.setList(getCardBeans);
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        setRecyclerView();
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvAddedCard.setLayoutManager(layoutManager);
        binding.rvAddedCard.setAdapter(adapter);
        if (getCardBeans != null)
            adapter.setList(getCardBeans);
    }

    SimpleRecyclerViewAdapter<GetCardBean, HolderSavedCardBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_saved_card,
            BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<GetCardBean>() {
        @Override
        public void onItemClick(View v, GetCardBean bean) {

        }
    });

}
