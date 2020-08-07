package com.marius.valeyou_admin.ui.activity.signup.signuptwo.selectcategory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.CatBean;
import com.marius.valeyou_admin.data.beans.SubCatBean;
import com.marius.valeyou_admin.data.beans.base.MoreBean;
import com.marius.valeyou_admin.data.beans.categoriesBean.CategoryDataBean;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.ActivitySelectCategoryBinding;
import com.marius.valeyou_admin.databinding.DialogSetPriceBinding;
import com.marius.valeyou_admin.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.ui.activity.signup.uploaddocument.UploadDocumentActivity;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SelectCategoryActivity extends AppActivity<ActivitySelectCategoryBinding, SelectCategoryActivityVM> {

    private CategoryAdapter categoryAdapter;
    HashMap<String, String> map;

    List<CategoryDataBean> categoryList;
    List<CatBean> catList;
    String auth_key;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, SelectCategoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<SelectCategoryActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_select_category, SelectCategoryActivityVM.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        Intent in = getIntent();
        if (in!=null){
            map = (HashMap<String, String>) in.getSerializableExtra("signupMap");
            auth_key = in.getStringExtra("auth_key");
        }

        viewModel.categoriesData(0);
        binding.remoteRB.setChecked(true);

        binding.remoteRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewModel.categoriesData(0);
                }
            }
        });

        binding.localRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewModel.categoriesData(1);
                }
            }
        });
    }

    @Override
    protected void subscribeToEvents(SelectCategoryActivityVM vm) {
        binding.header.tvTitle.setText(R.string.slect_resource);
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
                    case R.id.btn_next:
                        List<CategoryDataBean> mainCatList = categoryAdapter.getList();
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

                    if (map!=null&&catList.size()>0){

                        Gson gson = new Gson();
                        String category = gson.toJson(catList);

                            Intent intent = UploadDocumentActivity.newIntent(SelectCategoryActivity.this);
                            intent.putExtra("categoryList",category);
                            intent.putExtra("signupMap",map);
                            intent.putExtra("auth_key",auth_key);
                            startNewActivity(intent);

                        }


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
        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectCategoryActivity.this);
        binding.rvCategory.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, new CategoryAdapter.ProductCallback() {
            @Override
            public void onItemClick(View v, CategoryDataBean m) {

                for (int j = 0; j < dataList.size(); j++) {
                    if (dataList.get(j).getId() == m.getId()) {
                        dataList.get(j).setType(1);
                    }  else {
                        dataList.get(j).setType(0);
                    }
                }


                categoryAdapter.setProductList(dataList);

            }
        });

        binding.rvCategory.setAdapter(categoryAdapter);
        categoryAdapter.setProductList(dataList);

    }


    private BaseCustomDialog<DialogSetPriceBinding> dialogSuccess;

    private void openDialog() {
        dialogSuccess = new BaseCustomDialog<>(SelectCategoryActivity.this, R.layout.dialog_set_price, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogSuccess.dismiss();
                            break;
                        case R.id.iv_cancel:
                            dialogSuccess.dismiss();
                            break;
                    }
                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawableResource(R.color.transparance_whhite);
        dialogSuccess.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogSuccess.show();
    }
}
