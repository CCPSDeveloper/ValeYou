package com.marius.valeyou.ui.fragment.more.profile.editprofile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityEditProfileBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.util.DateInputMask;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends AppActivity<ActivityEditProfileBinding, EditProfileActivityVM> {

    private static final String USER_BEAN = "user_bean";
    private SignupData signupData;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, EditProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<EditProfileActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_edit_profile, EditProfileActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(EditProfileActivityVM vm) {
        /*DateInputMask dateInputMask = new DateInputMask(binding.etAge);
        binding.etAge.addTextChangedListener(dateInputMask);*/
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        binding.header.tvTitle.setText("Edit Profile");
        binding.header.setCheck(true);
        binding.header.tvTwo.setText("Save");
        signupData = vm.sharedPref.getUserData();
        binding.setBean(signupData);
        binding.etAge.setText(vm.sharedPref.getUserData().getDob());
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.singleRequestEvent.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {

                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        vm.success.setValue(resource.message);
                        onBackPressed();
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {
                    case R.id.cv_image:
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .setCropShape(CropImageView.CropShape.RECTANGLE)
                                .start(EditProfileActivity.this);
                        break;
                    case R.id.et_age:
                        datepicker();
                        break;
                    case R.id.tv_two:
                        MultipartBody.Part body = null;
                        if (resultUri != null) {
                            File file = new File(resultUri.getPath());
                            RequestBody requestFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                        }

                        RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etFirstName.getText().toString());
                        RequestBody last_name = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etLastName.getText().toString());
                        RequestBody email = RequestBody.create(MediaType.parse("text/plain"),
                                signupData.getEmail());
                        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etPhone.getText().toString());
                        RequestBody country_code = RequestBody.create(MediaType.parse("text/plain"),
                                "91");
                        RequestBody description = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etAbout.getText().toString());
                        RequestBody city = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etCity.getText().toString());
                        RequestBody state = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etState.getText().toString());
                        RequestBody location = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etAddress.getText().toString());
                        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"),
                                signupData.getLatitude() != null ? signupData.getLatitude() : "");
                        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"),
                                signupData.getLongitude() != null ? signupData.getLongitude() : "");
                        RequestBody age = RequestBody.create(MediaType.parse("text/plain"),
                                binding.etAge.getText().toString());
                        Map<String, RequestBody> bodyMap = new HashMap<>();
                        bodyMap.put("first_name", first_name);
                        bodyMap.put("last_name", last_name);
                        bodyMap.put("email", email);
                        bodyMap.put("phone", phone);
                        bodyMap.put("country_code", country_code);
                        bodyMap.put("description", description);
                        bodyMap.put("city", city);
                        bodyMap.put("state", state);
                        bodyMap.put("location", location);
                        bodyMap.put("latitude", latitude);
                        bodyMap.put("longitude", longitude);
                        bodyMap.put("dob", age);
                        vm.editProfile(bodyMap, body);
                        // done
                        break;
                }
            }
        });

        vm.singleRequestEvent.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        vm.success.setValue(resource.message);
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

    }

    final Calendar myCalendar = Calendar.getInstance();
    private void datepicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; // format for the date.
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                binding.etAge.setText(sdf.format(myCalendar.getTime()));
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private Uri resultUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Glide.with(this).load(resultUri).into(binding.ivProfile);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
