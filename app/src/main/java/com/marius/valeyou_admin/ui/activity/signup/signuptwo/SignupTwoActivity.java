package com.marius.valeyou_admin.ui.activity.signup.signuptwo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.databinding.ActivitySignupTwoBinding;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.ui.activity.LocationActivity;
import com.marius.valeyou_admin.ui.activity.signup.SignupActivity;
import com.marius.valeyou_admin.ui.activity.signup.signuptwo.selectcategory.SelectCategoryActivity;
import com.marius.valeyou_admin.util.Constants;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SignupTwoActivity extends AppActivity<ActivitySignupTwoBinding, SignupTwoActivityVM> {

    final Calendar myCalendar = Calendar.getInstance();
    Map<String, String> hashMap;
    String auth_key;
    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, SignupTwoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<SignupTwoActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_signup_two, SignupTwoActivityVM.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

    }

    @Override
    protected void subscribeToEvents(SignupTwoActivityVM vm) {

        Intent intent = getIntent();
        if (intent!=null){
            auth_key = intent.getStringExtra("auth_key");
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
                switch (view != null ? view.getId() : 0) {

                    case R.id.et_age:
                       // Toast.makeText(SignupTwoActivity.this, "test", Toast.LENGTH_SHORT).show();
                      //  pickerPopWin.showPopWin(SignupTwoActivity.this);
                        datePicker().show();
                        break;

                    case R.id.et_address:
                        Intent intent1 = new Intent(SignupTwoActivity.this,LocationActivity.class);
                        startActivityForResult(intent1,1);
                        break;

                    case R.id.btnSignup:

                            hashMap = new HashMap<>();
                            String dob = binding.etAge.getText().toString();
                            String houseNumber = binding.etHouseNumber.getText().toString();
                            String apartmentNumber = binding.etAppartmentNumber.getText().toString();
                            String street = binding.etStreet.getText().toString();
                            String zipCode = binding.etZipCode.getText().toString();
                            String city = binding.etCity.getText().toString();
                            String state = binding.etState.getText().toString();
                            String des = binding.etDescription.getText().toString();
                            String address = binding.etAddress.getText().toString();

                            if (address.isEmpty()) {
                                vm.error.setValue("Please select address");
                            }else{


                                if (!dob.isEmpty()){
                                    hashMap.put(Constants.DOB,dob);
                                }

                                if (!city.isEmpty()){
                                    hashMap.put(Constants.CITY,city);
                                }

                                if (!state.isEmpty()){
                                    hashMap.put(Constants.STATE,state);
                                }

                                if (!houseNumber.isEmpty()){
                                    hashMap.put(Constants.HOUSE_NUMBER,houseNumber);
                                }

                                if (!apartmentNumber.isEmpty()){
                                    hashMap.put(Constants.APARTMENT,apartmentNumber);
                                }

                                if (!street.isEmpty()){
                                    hashMap.put(Constants.STREET,street);
                                }


                                if (!zipCode.isEmpty()){
                                    hashMap.put(Constants.ZIP_CODE,zipCode);
                                }


                                if (!des.isEmpty()){
                                    hashMap.put(Constants.DESCRIPTION,des);
                                }

                                hashMap.put(Constants.ADDRESS,address);
                                intent = SelectCategoryActivity.newIntent(SignupTwoActivity.this);
                                intent.putExtra("signupMap", (Serializable) hashMap);
                                intent.putExtra("auth_key",auth_key);
                                startNewActivity(intent);

                            }
                        break;

                }

            }


            public Boolean checkDateFormat(String date){
                if (date == null || !date.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))
                    return false;
                SimpleDateFormat format=new SimpleDateFormat("dd/mm/yyyy");
                try {
                    format.parse(date);
                    return true;
                }catch (ParseException e){
                    return false;
                }
            }
            private String compareDates(String date)  {
                String msg = "";
                SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
                Date strDate = null;
                try {
                    strDate = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (System.currentTimeMillis() <= strDate.getTime()) {
                    msg = "your are not alowed to enter future date. Please enter a valid birthday date.";
                }
                return msg;
            }



                DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(SignupTwoActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {

                    }
                }).textConfirm("CONFIRM") //text of confirm button
                        .textCancel("CANCEL") //text of cancel button
                        .btnTextSize(16) // button text size
                        .viewTextSize(25) // pick view text size
                        .colorCancel(Color.parseColor("#999999")) //color of cancel button
                        .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                        .minYear(1990) //min year in loop
                        .maxYear(2550) // max year in loop
                        .showDayMonthYear(true) // shows like dd mm yyyy (default is false)
                        .dateChose("2013-11-11") // date chose when init popwindow
                        .build();




            private DatePickerDialog datePicker(){
               DatePickerDialog dialog =   new DatePickerDialog(SignupTwoActivity.this,date , myCalendar
                       .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                       myCalendar.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dialog;
            }

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "dd/MM/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    binding.etAge.setText(sdf.format(myCalendar.getTime()));
                }

            };

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){

            case 1:
                String result=data.getStringExtra("address");
                binding.etAddress.setText(result);
                String city = data.getStringExtra("city");
                String state = data.getStringExtra("state");
                String zipcode = data.getStringExtra("zipcode");
                binding.etCity.setText(city);
                binding.etState.setText(state);
                binding.etZipCode.setText(zipcode);

                break;
        }
    }
}
