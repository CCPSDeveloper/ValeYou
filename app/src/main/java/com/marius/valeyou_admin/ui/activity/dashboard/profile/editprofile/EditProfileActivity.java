package com.marius.valeyou_admin.ui.activity.dashboard.profile.editprofile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.base.ApiResponse;
import com.marius.valeyou_admin.data.beans.base.SimpleApiResponse;
import com.marius.valeyou_admin.data.beans.profilebean.ProfileModel;
import com.marius.valeyou_admin.data.beans.singninbean.SignInModel;
import com.marius.valeyou_admin.data.remote.helper.ApiUtils;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.ActivityEditProfileBinding;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.ProfileActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.certificate.AddCertificateActivity;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;
import com.marius.valeyou_admin.util.databinding.ImageViewBindingUtils;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;
import com.marius.valeyou_admin.util.permission.PermissionHandler;
import com.marius.valeyou_admin.util.permission.Permissions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends AppActivity<ActivityEditProfileBinding, EditProfileActivityVM> {

    public static final int RESULT_GALLERY = 0;
    File file;
    String firstname;
    String lastname;
    String email;
    String phone;
    String aboutus;


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
    protected void onResume() {
        super.onResume();
        firstname = viewModel.sharedPref.getUserData().getFirstName();
        lastname = viewModel.sharedPref.getUserData().getLastName();
        email = viewModel.sharedPref.getUserData().getEmail();
        phone = viewModel.sharedPref.getUserData().getPhone();
        aboutus = viewModel.sharedPref.getUserData().getDescription();
        String state = viewModel.sharedPref.getUserData().getState();
        String zipcode = viewModel.sharedPref.getUserData().getZipCode();
        String apartment = viewModel.sharedPref.getUserData().getAppartment();
        String houseNumber = viewModel.sharedPref.getUserData().getHouseNumber();
        String street= viewModel.sharedPref.getUserData().getStreet();
        String city = viewModel.sharedPref.getUserData().getCity();
        String dateofBirth = viewModel.sharedPref.getUserData().getDob();

        String image = viewModel.sharedPref.getUserData().getImage();
        if (image!=null){
            ImageViewBindingUtils.setProfilePicture(binding.IMGUserProfile,"http://3.17.254.50:4999/upload/"+image);
        }

        binding.etFirstName.setText(firstname);
        binding.etLastname.setText(lastname);
        binding.etEmail.setText(email);
        binding.etPhone.setText(phone);
        binding.etAbout.setText(aboutus);
        binding.etState.setText(state);
        binding.etZipCode.setText(zipcode);
        binding.etApartment.setText(apartment);
        binding.etHouseNumber.setText(houseNumber);
        binding.etStreet.setText(street);
        binding.etCity.setText(city);
        binding.etDateOfBirth.setText(dateofBirth);

    }

    @Override
    protected void subscribeToEvents(EditProfileActivityVM vm) {
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        binding.header.tvTitle.setText("Edit Profile");
        binding.header.setCheck(true);
        binding.header.tvTwo.setText("Save");

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });


        vm.profilebeandata.observe(this, new SingleRequestEvent.RequestObserver<ApiResponse<SignInModel>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<ApiResponse<SignInModel>> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        Intent intent = ProfileActivity.newIntent(EditProfileActivity.this);
                        startNewActivity(intent);
                        finish();
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorized")){
                            Intent intent1 = LoginActivity.newIntent(EditProfileActivity.this);
                            startNewActivity(intent1, true, true);

                        }
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {

                    case R.id.cv_gallaryBtn:
                        AllowPermision();
                        break;

                    case R.id.tv_two:

                        String authKey = vm.sharedPref.getUserData().getAuthKey();
                        String firstname = binding.etFirstName.getText().toString();
                        String lastname = binding.etLastname.getText().toString();
                        String email = binding.etEmail.getText().toString();
                        String phone = binding.etPhone.getText().toString();
                        String about = binding.etAbout.getText().toString();
                        String houseNumber = binding.etHouseNumber.getText().toString();
                        String apartment = binding.etApartment.getText().toString();
                        String Street = binding.etStreet.getText().toString();
                        String zipcode = binding.etZipCode.getText().toString();
                        String state = binding.etState.getText().toString();
                        String city = binding.etCity.getText().toString();
                        String dob = binding.etDateOfBirth.getText().toString();


                        if (file==null){

                            HashMap<String, String> strMap = new HashMap<>();
                            strMap.put("first_name",firstname);
                            strMap.put("last_name",lastname);
                            strMap.put("email",email);
                            strMap.put("phone",phone);
                            strMap.put("description",about);
                            strMap.put("state",state);
                            strMap.put("houseNumber",houseNumber);
                            strMap.put("appartment",apartment);
                            strMap.put("street",Street);
                            strMap.put("zipCode",zipcode);
                            strMap.put("city",city);
                            strMap.put("dob",dob);

                            vm.editProfileSTr(authKey,strMap);
                        }else{

                        Map<String, RequestBody> map = new HashMap<>();
                        map.put("first_name",toRequestBody(firstname));
                        map.put("last_name",toRequestBody(lastname));
                        map.put("email",toRequestBody(email));
                        map.put("phone",toRequestBody(phone));
                        map.put("description",toRequestBody(about));
                        map.put("state",toRequestBody(state));
                        map.put("houseNumber",toRequestBody(houseNumber));
                        map.put("appartment",toRequestBody(apartment));
                        map.put("street",toRequestBody(Street));
                        map.put("zipCode",toRequestBody(zipcode));
                        map.put("city",toRequestBody(city));
                            map.put("dob",toRequestBody(dob));


                            MultipartBody.Part image = ApiUtils.createMultipartBodySingle(file);
                            vm.editProfile(authKey,map,image);
                        }


                        break;

                }
            }
        });
    }


    private void AllowPermision(){
        Permissions.check(this, Manifest.permission.READ_EXTERNAL_STORAGE, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                gallaryIntent();
            }
        });
    }


    private void gallaryIntent(){
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent , RESULT_GALLERY );

    }

    public static RequestBody toRequestBody (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body ;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_GALLERY :
                if (null != data) {
                   Uri imageUri = data.getData();

                    Bitmap mBitmap = null;
                    try {

                        mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        binding.img.setImageBitmap(mBitmap);
                        String pathStr = getRealPathFromUri(this,imageUri);

                        file = new File(pathStr);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
            default:
                break;
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
