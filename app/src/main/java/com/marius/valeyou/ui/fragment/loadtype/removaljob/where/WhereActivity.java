package com.marius.valeyou.ui.fragment.loadtype.removaljob.where;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivityWhereBinding;
import com.marius.valeyou.di.base.view.AppActivity;

import androidx.lifecycle.Observer;


public class WhereActivity extends AppActivity<ActivityWhereBinding, WhereActivityVM> {
    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity,WhereActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<WhereActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_where, WhereActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(WhereActivityVM vm) {

        binding.header.tvTitle.setText("Where?");
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
                    case R.id.btn_save:
                        if (isEmpty()) {
                            Intent intent = new Intent();
                            intent.putExtra("location",binding.etAddress.getText().toString());
                            intent.putExtra("city",binding.etCity.getText().toString());
                            intent.putExtra("state",binding.etState.getText().toString());
                            intent.putExtra("country",binding.etCountry.getText().toString());
                            intent.putExtra("zip_code",binding.etZipCode.getText().toString());
                            intent.putExtra("apartment",binding.etApartmentNumber.getText().toString());
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                        break;
                }
            }
        });

    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty(binding.etAddress.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(binding.etState.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(binding.etCity.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(binding.etZipCode.getText().toString().trim()))
            return false;
        return true;
    }
}
