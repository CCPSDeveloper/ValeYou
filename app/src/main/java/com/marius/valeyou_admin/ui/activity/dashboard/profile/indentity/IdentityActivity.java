package com.marius.valeyou_admin.ui.activity.dashboard.profile.indentity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.IdentityModel;
import com.marius.valeyou_admin.data.beans.base.SimpleApiResponse;
import com.marius.valeyou_admin.data.beans.profilebean.ProfileModel;
import com.marius.valeyou_admin.data.remote.helper.ApiUtils;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.ActivityIdentityBinding;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.di.base.view.BaseActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.ProfileActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.certificate.AddCertificateActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.portfolio.AddPortfolioActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.portfolio.AddPortfolioActivityVM;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;
import com.marius.valeyou_admin.ui.activity.main.MainActivity;
import com.marius.valeyou_admin.util.databinding.ImageViewBindingUtils;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;
import com.marius.valeyou_admin.util.permission.PermissionHandler;
import com.marius.valeyou_admin.util.permission.Permissions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class IdentityActivity extends AppActivity<ActivityIdentityBinding,ActivityIdentityVM> {

    public static final int RESULT_GALLERY = 101;
    public static final int RESULT_CAMERA = 102;
    File file;
    String comeFrom;
    String identity_id;
    int camera;
    File backfile;
    File frontfile;
    String authkey;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, IdentityActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<ActivityIdentityVM> getBindingActivity() {
        return new BaseActivity.BindingActivity<>(R.layout.activity_identity, ActivityIdentityVM.class);
    }


    @Override
    protected void subscribeToEvents(ActivityIdentityVM vm) {

        AllowGalleryPermision();
        Intent intent = getIntent();
        if (intent != null) {
            comeFrom = intent.getStringExtra("comeFrom");
            if (comeFrom.equalsIgnoreCase("add")) {
                binding.header.tvTitle.setText("Upload Identity");
            } else if (comeFrom.equalsIgnoreCase("edit")){
                binding.header.tvTitle.setText("Edit Identity");
                IdentityModel identityModel = intent.getParcelableExtra("IdentityData");
                identity_id = String.valueOf(identityModel.getId());
            }else if (comeFrom.equalsIgnoreCase("cat")){
                binding.header.tvTitle.setText("Upload Identity");
                authkey = intent.getStringExtra("auth_key");
            }
        }
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });


        vm.addIdentityBean.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        if (comeFrom.equalsIgnoreCase("cat")){
                            Intent intent = MainActivity.newIntent(IdentityActivity.this);
                            startNewActivity(intent,true);
                           finishAffinity();
                        }else {
                            Intent intent = ProfileActivity.newIntent(IdentityActivity.this);
                            startNewActivity(intent);
                            finish();
                        }
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorized")) {
                            Intent intent1 = LoginActivity.newIntent(IdentityActivity.this);
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
                    case R.id.cv_cancel:
                        finish(true);
                        break;


                    case R.id.cv_save:

                        if (frontfile == null) {

                            vm.info.setValue("Please choose  front side of your identity");

                        } else if(backfile == null) {

                            vm.info.setValue("Please choose back side of your identity");
                        }else{

                            RequestBody requestFrontFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), frontfile);

                            MultipartBody.Part frontbody =
                                    MultipartBody.Part.createFormData("image", frontfile.getName(), requestFrontFile);



                            RequestBody requestBackFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), backfile);

                            MultipartBody.Part backbody =
                                    MultipartBody.Part.createFormData("backImage", backfile.getName(), requestBackFile);



                            if (comeFrom.equalsIgnoreCase("add")) {

                                 vm.addIdentity(vm.sharedPref.getUserData().getAuthKey(), frontbody,backbody);

                            } else if (comeFrom.equalsIgnoreCase("edit")) {
                                Map<String, RequestBody> map = new HashMap<>();
                                map.put("identity_id", toRequestBody(identity_id));
                                vm.editIdentity(vm.sharedPref.getUserData().getAuthKey(), map, frontbody,backbody);

                            }else if (comeFrom.equalsIgnoreCase("cat")) {

                                if (authkey!=null) {
                                    vm.addIdentity(authkey, frontbody, backbody);
                                }

                            }

                        }

                        break;

                    case R.id.tv_choose_front:
                            camera = 1;
                        AllowCameraPermision();


                        break;

                    case R.id.tv_choose_back:

                        camera = 2;

                        AllowCameraPermision();


                        break;
                }
            }
        });
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    private void AllowGalleryPermision() {
        Permissions.check(this, Manifest.permission.READ_EXTERNAL_STORAGE, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
            }
        });
    }

    private void AllowCameraPermision() {
        Permissions.check(this, Manifest.permission.CAMERA, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                galaryCameraIntent();
            }
        });
    }



    private void galaryCameraIntent() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                if (camera == 1){
                    Uri frontUri = result.getUri();
                    binding.imageFront.setImageURI(frontUri);
                    String frontpathString = getRealPathFromURI(frontUri);
                     frontfile = new File(frontpathString);
                     binding.tvFrontfileName.setText("File Name : "+ frontfile.getName());
                }else if (camera == 2){
                    Uri backUri = result.getUri();
                    binding.imageBack.setImageURI(backUri);
                    String backpathstring = getRealPathFromURI(backUri);
                     backfile = new File(backpathstring);
                    binding.tvBackfileName.setText("File Name : "+backfile.getName());

                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }



    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }




}
