package com.marius.valeyou.ui.activity.selectcategory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.reqbean.RequestBean;
import com.marius.valeyou.data.beans.reqbean.RequestSubCategory;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivitySelectCategoryBinding;
import com.marius.valeyou.databinding.DialogSetPriceBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.JunkRemovalJobFragment;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SelectCategoryActivity extends AppActivity<ActivitySelectCategoryBinding, SelectCategoryActivityVM> {
    public static Intent newIntent(Activity activity,int provider_id) {
        Intent intent = new Intent(activity, SelectCategoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("provider_id",provider_id);
        return intent;
    }

    private List<RequestBean> selected = new ArrayList<>();
    public String category_type = "0";

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
    }

    @Override
    protected void subscribeToEvents(SelectCategoryActivityVM vm) {
        binding.header.tvTitle.setText(R.string.select_category);
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        vm.getListOfCateegory(0,"");
        vm.rButtonEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer) {
                    case R.id.rb_remote_job:
                        category_type = "0";
                        vm.getListOfCateegory(0,"");
                        break;
                    case R.id.rb_local_job:
                        category_type = "1";
                        vm.getListOfCateegory(1,"");
                        break;
                }
            }
        });
        setActionOnCatSearch();
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
                        List<CategoryListBean> list = moreBeans;
                        String sub_category = "";
                        for (int k = 0; k < list.size(); k++) {
                            if (list.get(k).isCheck()) {
                                RequestBean requestBean = new RequestBean();
                                List<RequestSubCategory> subcategory = new ArrayList<>();
                                requestBean.setCategory_id(String.valueOf(list.get(k).getId()));
                                for (int r = 0; r < list.get(k).getSubCategories().size(); r++) {
                                    if (list.get(k).getSubCategories().get(r).isCheck()) {
                                        subcategory.add(new RequestSubCategory(String.valueOf(list.get(k).getSubCategories().get(r).getId()), list.get(k).getSubCategories().get(r).getPrice()));
                                        if (sub_category != null && sub_category.equalsIgnoreCase("")) {
                                            sub_category = list.get(k).getSubCategories().get(r).getName();
                                        } else {
                                            sub_category = sub_category.trim().substring(0, sub_category.trim().length() - 1) + ", " + list.get(k).getSubCategories().get(r).getName() + ", ";
                                        }
                                    }
                                }
                                requestBean.setSubcategory(subcategory);
                                selected.add(requestBean);
                            }
                        }
                        String json = new Gson().toJson(selected);
                        if (!json.equalsIgnoreCase("[]")) {
                            String cat_value = "";
                            if (sub_category != null && !sub_category.equalsIgnoreCase("")) {
                                if (sub_category.contains(",")) {
                                    cat_value = sub_category.trim().substring(0, sub_category.trim().length() - 1);
                                } else {
                                    cat_value = sub_category.trim();
                                }
                            }
                            Intent intent = JunkRemovalJobFragment.newInstance(SelectCategoryActivity.this,
                                    json, category_type, cat_value,getIntent().getIntExtra("provider_id",0));
                            startNewActivity(intent);
                        } else {
                            vm.info.setValue("Please select at least one category");
                        }
                        break;
                }
            }
        });

        vm.categoryList.observe(this, new SingleRequestEvent.RequestObserver<List<CategoryListBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<CategoryListBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setLoading(true);
                        break;
                    case SUCCESS:
                        binding.setLoading(false);
                        moreBeans = resource.data;
                        categoryAdapter.setProductList(moreBeans);
                        break;
                    case WARN:
                        binding.setLoading(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setLoading(false);
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        setRecyclerView();
    }

    private CategoryAdapter categoryAdapter;
    private List<CategoryListBean> moreBeans;

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectCategoryActivity.this);
        binding.rvCategory.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, new CategoryAdapter.ProductCallback() {
            @Override
            public void onItemClick(View v, CategoryListBean m) {
                //openDialog();
                moreBeans = categoryAdapter.getListData();
                for (int j = 0; j < moreBeans.size(); j++) {
                    if (moreBeans.get(j).getId() == m.getId()) {
                        moreBeans.get(j).setType(1);
                    } else {
                        moreBeans.get(j).setType(0);
                    }
                }
                categoryAdapter.setProductList(moreBeans);
            }
        });
        binding.rvCategory.setAdapter(categoryAdapter);
        if (moreBeans != null)
            categoryAdapter.setProductList(moreBeans);
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

    private void setActionOnCatSearch() {
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(binding.etSearch.getWindowToken(), 0);
                    viewModel.getListOfCateegory(Integer.valueOf(category_type), binding.etSearch.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

}
