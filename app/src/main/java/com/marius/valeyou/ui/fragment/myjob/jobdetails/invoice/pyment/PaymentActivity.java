package com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;


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

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, permissions, 101);
            }
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
                            onClickSomething("4242424242424242",11,2023,"766");
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

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (requestCode == 101 && grantResults.length > 0
                    && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                throw new RuntimeException("Location services are required in order to " +
                        "connect to a reader.");
            }
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

                String cardNumber = bean.getCardNumber();
                String exp_month = bean.getExpMonth();
                String exp_year = bean.getExpYear();


            }
        });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
    }

    public void onClickSomething(String cardNumber, int cardExpMonth, int cardExpYear, String cardCVC) {
        showProgressDialog(R.string.plz_wait);
        Card card = new Card(cardNumber, cardExpMonth, cardExpYear, cardCVC);
        card.validateNumber();
        card.validateCVC();

        if (!card.validateNumber()) {
            viewModel.error.setValue("card number not valid");
            dismissProgressDialog();
            return;
        } else if (!card.validateExpiryDate()) {
            viewModel.error.setValue("expire date not valid");
            dismissProgressDialog();
            return;
        } else if (!card.validateCVC()) {
            viewModel.error.setValue("cvc number not valid");
            dismissProgressDialog();
            return;
        } else if (card.validateCard()) {
            createToken(card);
        } else {
            dismissProgressDialog();
            viewModel.error.setValue("card is not valid..");
        }

    }

    private void createToken(Card card) {
        /*This is the TEST KEY for our stripe account - pk_test_UdHpc2MLTMd7FQzy1swFF5b3
        SECRET KEY is sk_test_UgjtRADK4YlevK8iBwBYeJVJ*/
        Stripe stripe = new Stripe(this, Constants.STRIPE_PUBLISHABLE_KEY);
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        dismissProgressDialog();
                        String token_value = token.getId();
                        viewModel.success.setValue("Payment done ! ");
                        ///paymentMethod(orde_number, token.getId()); // hit backed api
                        Intent intent = PaymentSuccessActivity.newIntent(PaymentActivity.this);
                        startNewActivity(intent);
                    }
                    public void onError(Exception error) {
                        viewModel.error.setValue(error.toString());
                    }
                }
        );
    }


  /*  private void cardDetailsFillWork() {
        binding.etCardNumber.addTextChangedListener(new GroupedInputFormatWatcher(binding.etCardNumber));
        binding.etCardNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (binding.etCardNumber.length() == 19) {
                    //binding.etDate.requestFocus();
                }
                return false;
            }
        });
        binding.etDate.addTextChangedListener(new TwoDigitsCardTextWatcher(binding.etDate));
        binding.etDate.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (binding.etDate.length() == 5) {
                    //binding.etCvv.requestFocus();
                }
                return false;
            }
        });
    }
*/

}
