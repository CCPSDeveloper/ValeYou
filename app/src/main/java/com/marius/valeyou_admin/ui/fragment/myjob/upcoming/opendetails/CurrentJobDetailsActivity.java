package com.marius.valeyou_admin.ui.fragment.myjob.upcoming.opendetails;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.marius.valeyou_admin.BR;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.base.ApiResponse;
import com.marius.valeyou_admin.data.beans.base.SimpleApiResponse;
import com.marius.valeyou_admin.data.beans.jobs.JobDetailModel;
import com.marius.valeyou_admin.data.beans.singninbean.SignInModel;
import com.marius.valeyou_admin.data.beans.startendjob.StartEndModel;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.ActivityCurrentJobDetailsBinding;
import com.marius.valeyou_admin.databinding.DialogDeleteAccouontBinding;
import com.marius.valeyou_admin.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;
import com.marius.valeyou_admin.ui.activity.main.MainActivity;
import com.marius.valeyou_admin.ui.fragment.myjob.startjob.StartJobTimerActivity;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.closedetails.JobDetailsActivity;
import com.marius.valeyou_admin.util.databinding.ImageViewBindingUtils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import androidx.lifecycle.Observer;

public class CurrentJobDetailsActivity extends AppActivity<ActivityCurrentJobDetailsBinding, CurrentJobDetailsActivityVM> {

    int job_id;
    String auth_key;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, CurrentJobDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<CurrentJobDetailsActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_current_job_details, CurrentJobDetailsActivityVM.class);
    }

    private String convertTimeStampToTime(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000L);
        String date = DateFormat.format("MMM dd, yyyy hh:mm a", cal).toString();
        return date;
    }


    @Override
    protected void subscribeToEvents(CurrentJobDetailsActivityVM vm) {
        binding.header.tvTitle.setText("Current Job Detail");

        Intent intent = getIntent();
        if (intent!=null){
            int id = intent.getIntExtra("id",0);
            if (id!=0){
                 auth_key = vm.sharedPref.getUserData().getAuthKey();
                vm.getJobDetaial(auth_key,id);
            }
        }


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
                switch (view.getId()) {
                    case R.id.btn_start_end_job:
                        dialogStartJob();
                        break;
                }
            }
        });

        vm.jobDetailBean.observe(this, new Observer<Resource<JobDetailModel>>() {
            @Override
            public void onChanged(Resource<JobDetailModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        binding.setVariable(BR.bean,resource.data);
                        job_id = resource.data.getId();
                        if (resource.data.getDate()!=null){
                            String date = convertTimeStampToTime(Long.parseLong(resource.data.getDate()));
                            binding.jobDate.setText(date);
                        }

                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorised")){
                            Intent intent1 = LoginActivity.newIntent(CurrentJobDetailsActivity.this);
                            startNewActivity(intent1, true, true);

                        }
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

       vm.startJobApiBean.observe(this, new Observer<Resource<ApiResponse<SimpleApiResponse>>>() {
           @Override
           public void onChanged(Resource<ApiResponse<SimpleApiResponse>> resource) {
               switch (resource.status) {
                   case LOADING:
                       binding.setCheck(true);
                       break;
                   case SUCCESS:
                       binding.setCheck(false);

                       vm.success.setValue(resource.message);
                       Intent intent = StartJobTimerActivity.newInstent(CurrentJobDetailsActivity.this);
                       intent.putExtra("id",job_id);
                       startNewActivity(intent,true);
                       finish();


                       break;
                   case ERROR:
                       binding.setCheck(false);
                       vm.error.setValue(resource.message);
                       if (resource.message.equalsIgnoreCase("unauthorised")){

                           Intent intent1 = LoginActivity.newIntent(CurrentJobDetailsActivity.this);
                           startNewActivity(intent1, true, true);

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


    private BaseCustomDialog<DialogDeleteAccouontBinding> dialogStartEndJob;
    private void dialogStartJob() {
        dialogStartEndJob = new BaseCustomDialog<>(CurrentJobDetailsActivity.this, R.layout.dialog_start_job, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            String authKey = viewModel.sharedPref.getUserData().getAuthKey();

                                String start =  (System.currentTimeMillis()/1000)+"";
                                viewModel.startJob(authKey, job_id, 3,start);

                            dialogStartEndJob.dismiss();
                            break;
                        case R.id.btn_cancel:
                            dialogStartEndJob.dismiss();
                            break;
                    }
                }
            }
        });
        dialogStartEndJob.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogStartEndJob.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogStartEndJob.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogStartEndJob.show();
    }


}
