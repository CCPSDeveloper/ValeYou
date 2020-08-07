package com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.GetAllJobBean;
import com.marius.valeyou.databinding.FragmentInvoiceBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.PaymentActivity;
import com.marius.valeyou.util.misc.BackStackManager;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class InvoiceFragment extends AppFragment<FragmentInvoiceBinding, InvoiceFragmentVM> {

    private static final String BEAN = "bean";
    private MainActivity mainActivity;
    private GetAllJobBean getAllJobBean;

    public static Fragment newInstance(GetAllJobBean getAllJobBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BEAN,getAllJobBean);
        InvoiceFragment invoiceFragment = new InvoiceFragment();
        invoiceFragment.setArguments(bundle);
        return invoiceFragment;
    }

    @Override
    protected BindingFragment<InvoiceFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_invoice, InvoiceFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(InvoiceFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader("Invoice");
        getAllJobBean = getArguments().getParcelable(BEAN);
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view!=null?view.getId():0) {
                    case R.id.btn_pay:
                        BackStackManager.getInstance(getActivity()).clear();
                        Intent intent = PaymentActivity.newIntnt(getActivity());
                        startNewActivity(intent);
                        break;
                    case R.id.btn_cancel:
                        vm.info.setValue("clicked_cancel!");
                        break;
                }
            }
        });
    }

}
