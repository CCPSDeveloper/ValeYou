package com.marius.valeyou.ui.fragment.loadtype.removaljob.when;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.TimePicker;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivityWhenBinding;
import com.marius.valeyou.di.base.view.AppActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.lifecycle.Observer;

public class WhenActivity extends AppActivity<ActivityWhenBinding, WhenActivityVM> {

    final Calendar myCalendar = Calendar.getInstance();
    private long start_time_stamp;
    private long end_time_stamp;
    private long time_stamp;

    private String start_time = "";
    private String end_time = "null";

    private String job_type;

    public static Intent newIntent(Activity activity, String job_type) {
        Intent intent = new Intent(activity, WhenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("job_type", job_type);
        return intent;
    }

    @Override
    protected BindingActivity<WhenActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_when, WhenActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(WhenActivityVM vm) {
        job_type = getIntent().getStringExtra("job_type");
        binding.header.tvTitle.setText("When");
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        setCurrentTime();
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {
                    case R.id.btn_save:

                        switch (job_type) {
                            case "1":
                                if (start_time != null && start_time.equalsIgnoreCase("")) {
                                    vm.error.setValue("please select date");
                                    return;
                                }
                                if (time_stamp == 0) {
                                    vm.error.setValue("please select time");
                                    return;
                                }
                                break;
                            case "2":
                                if (start_time == null || start_time.equalsIgnoreCase("")) {
                                    vm.error.setValue("please select date");
                                    return;
                                }
                                if (time_stamp == 0) {
                                    vm.error.setValue("please select time");
                                    return;
                                }
                                break;
                            case "3":
                                if (start_time != null && start_time.equalsIgnoreCase("")) {
                                    vm.error.setValue("please select start date");
                                    return;
                                }
                                if (end_time != null && end_time.equalsIgnoreCase("null")) {
                                    vm.error.setValue("please select end date");
                                    return;
                                }
                                if (time_stamp == 0) {
                                    vm.error.setValue("please select time");
                                    return;
                                }
                                break;
                        }

                        if (start_time != null && !start_time.equalsIgnoreCase("")) {
                            Intent intent = new Intent();
                            intent.putExtra("start", start_time_stamp);
                            intent.putExtra("end", end_time_stamp);
                            intent.putExtra("time", time_stamp);
                            intent.putExtra("start_time", start_time);
                            intent.putExtra("end_time", end_time);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                        break;
                    case R.id.ll_time_picker:
                        new TimePickerDialog(WhenActivity.this, date, myCalendar
                                .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE),
                                false).show();
                        break;
                }
            }
        });

        binding.calendar.setCalendarListener(new CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                setStartDate(startDate);
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
                setStartDate(startDate);
                setEndDate(endDate);
            }
        });
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        binding.calendar.setSelectableDateRange(Calendar.getInstance(), cal);
    }

    private void setCurrentTime() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a", Locale.getDefault());
        String time = sdf.format(currentTime);
        String s[] = time.split(" ");
        String s1[] = s[0].split(":");
        binding.tvHour.setText(s1[0]);
        binding.tvMinutes.setText(s1[1]);
        binding.tvAmPm.setText(s[1]);
        SimpleDateFormat sdfcurrent = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String date = sdfcurrent.format(currentTime);
        String date_split[] = date.split(" ");
        binding.tvStartMonth.setText(date_split[1]);
        binding.tvStartYear.setText(date_split[2]);
        binding.tvStartDate.setText(date_split[0]);
        binding.tvEndMonth.setText(date_split[1]);
        binding.tvEndYear.setText(date_split[2]);
        binding.tvEndDate.setText(date_split[0]);
    }

    private void setStartDate(Calendar startDate) {
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat startFrmt = new SimpleDateFormat("MMM dd yyyy");
        start_time_stamp = startDate.getTimeInMillis() / 1000;
        String date = fmtOut.format(startDate.getTime());
        String split[] = date.split("-");
        binding.tvStartDate.setText(split[0]);
        binding.tvStartMonth.setText(split[1]);
        binding.tvStartYear.setText(split[2]);
        start_time = startFrmt.format(startDate.getTime());
    }

    private void setEndDate(Calendar endDate) {
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat endfrmt = new SimpleDateFormat("MMM dd yyyy");
        end_time_stamp = endDate.getTimeInMillis() / 1000;
        String date = fmtOut.format(endDate.getTime());
        String split[] = date.split("-");
        binding.tvEndDate.setText(split[0]);
        binding.tvEndMonth.setText(split[1]);
        binding.tvEndYear.setText(split[2]);
        end_time = endfrmt.format(endDate.getTime());
    }

    TimePickerDialog.OnTimeSetListener date = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            Calendar c = Calendar.getInstance();
            if (job_type.equalsIgnoreCase("3")) {
                updateLabel();
            } else {
                if (myCalendar.getTimeInMillis() > c.getTimeInMillis()) {
                    //it's after current
                    updateLabel();
                } else {
                    //it's before current'
                    viewModel.error.setValue("Invalid Time");
                }
            }
        }
    };

    private void updateLabel() {
        String myFormat = "hh:mm a"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String time = sdf.format(myCalendar.getTime());
        time_stamp = myCalendar.getTimeInMillis() / 1000;
        String splite_am[] = time.split(" ");
        String split_time[] = splite_am[0].split(":");
        binding.tvAmPm.setText(splite_am[1]);
        binding.tvHour.setText(split_time[0]);
        binding.tvMinutes.setText(split_time[1]);
    }

}
