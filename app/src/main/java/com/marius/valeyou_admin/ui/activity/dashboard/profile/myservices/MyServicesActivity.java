package com.marius.valeyou_admin.ui.activity.dashboard.profile.myservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.CatBean;
import com.marius.valeyou_admin.data.beans.SubCatBean;
import com.marius.valeyou_admin.data.beans.base.SimpleApiResponse;
import com.marius.valeyou_admin.data.beans.categoriesBean.CategoryDataBean;
import com.marius.valeyou_admin.data.beans.profilebean.ProfileModel;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.ActivityMyServicesBinding;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.ProfileActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.certificate.AddCertificateActivity;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;
import com.marius.valeyou_admin.ui.activity.main.MainActivity;
import com.marius.valeyou_admin.ui.activity.signup.signuptwo.selectcategory.CategoryAdapter;
import com.marius.valeyou_admin.ui.activity.signup.signuptwo.selectcategory.SelectCategoryActivity;
import com.marius.valeyou_admin.ui.activity.signup.signuptwo.selectcategory.SelectCategoryActivityVM;
import com.marius.valeyou_admin.ui.activity.signup.uploaddocument.UploadDocumentActivity;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyServicesActivity extends AppActivity<ActivityMyServicesBinding,MyServicesActivityVM> {


    private MyServicesAdapter myServicesAdapter;

    List<CategoryDataBean> categoryList;

    int typeValue =0;

    List<CatBean> catList;
    List<ProfileModel.ProviderCategoriesBean> categoriesBeansList;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, MyServicesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<MyServicesActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_my_services, MyServicesActivityVM.class);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );


        viewModel.categoriesData(0);
        binding.remoteRB.setChecked(true);

        binding.remoteRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    typeValue = 0;
                    viewModel.categoriesData(0);

                }
            }
        });

        binding.localRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    typeValue = 1;
                    viewModel.categoriesData(1);
                }
            }
        });
    }

    @Override
    protected void subscribeToEvents(MyServicesActivityVM vm) {
        binding.header.tvTitle.setText(R.string.my_services);

        Intent intent = getIntent();
        if (intent!=null){

            Bundle args = intent.getBundleExtra("BUNDLE");
            List<ProfileModel.ProviderCategoriesBean> list = (ArrayList<ProfileModel.ProviderCategoriesBean>) args.getSerializable("categoriesBean");

            Toast.makeText(this, ""+list.size(), Toast.LENGTH_SHORT).show();
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
                switch (view != null ? view.getId() : 0) {
                    case R.id.btn_save:

                        List<CategoryDataBean> mainCatList = myServicesAdapter.getList();
                        catList = new ArrayList<>();
                        for (int j=0;j<mainCatList.size();j++){
                            if (mainCatList.get(j).isSelected()){
                                List<SubCatBean> subCat = new ArrayList<>();
                                List<CategoryDataBean.SubCategoriesBean> subCategories
                                        = mainCatList.get(j).getSubCategories();
                                for (int i=0;i<subCategories.size();i++){
                                    if (subCategories.get(i).isCheck()){
                                        subCat.add(new SubCatBean(subCategories.get(i).getId(),subCategories.get(i).getPrice()));
                                    }
                                }
                                catList.add(new CatBean(mainCatList.get(j).getId(),subCat));
                            }
                        }

                        if (catList.size()>0){

                            Gson gson = new Gson();
                            String category = gson.toJson(catList);

                            Log.d("categoriesSTr : ",category);
                            String authKey = vm.sharedPref.getUserData().getAuthKey();
                            vm.editSerivces(authKey, category);
                        }


                        break;
                }
            }
        });

        vm.profilebeandata.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status){
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        Intent intent = ProfileActivity.newIntent(MyServicesActivity.this);
                        startNewActivity(intent);
                        finish();
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorized")){
                            Intent intent1 = LoginActivity.newIntent(MyServicesActivity.this);
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


        vm.categoriesbeandata.observe(this, new SingleRequestEvent.RequestObserver<List<CategoryDataBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<CategoryDataBean>> resource) {
                switch (resource.status){
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        categoryList = resource.data;
                        setRecyclerView(resource.data);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;

                }
            }
        });
    }
    private void setRecyclerView(List<CategoryDataBean> dataList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyServicesActivity.this);
        binding.rvCategory.setLayoutManager(layoutManager);
        myServicesAdapter = new MyServicesAdapter(this, new MyServicesAdapter.ProductCallback() {
            @Override
            public void onItemClick(View v, CategoryDataBean m) {
                //openDialog();
                for (int j = 0; j < dataList.size(); j++) {
                    if (dataList.get(j).getId() == m.getId()) {
                        dataList.get(j).setType(1);
                    }  else {
                        dataList.get(j).setType(0);
                    }
                }
                myServicesAdapter.setProductList(dataList);
            }
        });
        binding.rvCategory.setAdapter(myServicesAdapter);
        myServicesAdapter.setProductList(dataList);
    }

}
