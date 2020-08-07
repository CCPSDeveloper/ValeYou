package com.marius.valeyou.ui.fragment.loadtype.removaljob.whentype;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivityWhenTypeBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.when.WhenActivity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

public class WhenTypeActivity extends AppActivity<ActivityWhenTypeBinding, WhenTypeActivityVM> {
    private static final int WHEN_TYPE_RESULT = 3;
    private String job_type = "";

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, WhenTypeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<WhenTypeActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_when_type, WhenTypeActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(WhenTypeActivityVM vm) {
        binding.header.tvTitle.setText("When");
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
                    case R.id.cv_now:
                        job_type = "1";
                        intent = WhenActivity.newIntent(WhenTypeActivity.this,job_type);
                        startActivityForResult(intent, WHEN_TYPE_RESULT);
                        break;
                    case R.id.cv_today:
                        job_type = "2";
                        intent = WhenActivity.newIntent(WhenTypeActivity.this,job_type);
                        startActivityForResult(intent, WHEN_TYPE_RESULT);
                        break;
                    case R.id.cv_future:
                        job_type = "3";
                        intent = WhenActivity.newIntent(WhenTypeActivity.this,job_type);
                        startActivityForResult(intent, WHEN_TYPE_RESULT);
                        break;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case WHEN_TYPE_RESULT:
                if (Activity.RESULT_OK == resultCode) {
                    Intent intent = new Intent();
                    intent.putExtra("start", data.getLongExtra("start", 0));
                    intent.putExtra("end", data.getLongExtra("end", 0));
                    intent.putExtra("time", data.getLongExtra("time", 0));
                    intent.putExtra("job_type", job_type);
                    intent.putExtra("start_time", data.getStringExtra("start_time"));
                    intent.putExtra("end_time", data.getStringExtra("end_time"));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }
}
