package com.marius.valeyou_admin.ui.fragment.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.marius.valeyou_admin.BR;
import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.LocationsBean;
import com.marius.valeyou_admin.data.beans.base.MoreBean;
import com.marius.valeyou_admin.data.beans.base.SimpleApiResponse;
import com.marius.valeyou_admin.data.beans.business.BusinessHoursModel;
import com.marius.valeyou_admin.data.beans.categoriesBean.CategoryDataBean;
import com.marius.valeyou_admin.data.beans.map.MapListModel;
import com.marius.valeyou_admin.data.beans.singninbean.SignInModel;
import com.marius.valeyou_admin.data.remote.helper.Resource;
import com.marius.valeyou_admin.databinding.FragmentHomeBinding;
import com.marius.valeyou_admin.databinding.HolderFilterCategoryBinding;
import com.marius.valeyou_admin.databinding.HolderListItemsBinding;
import com.marius.valeyou_admin.databinding.HolderMapItemsBinding;
import com.marius.valeyou_admin.databinding.HolderSetCategoryBinding;
import com.marius.valeyou_admin.databinding.HolderSetStateBinding;
import com.marius.valeyou_admin.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou_admin.di.base.view.AppFragment;
import com.marius.valeyou_admin.ui.activity.dashboard.jobhistory.currentjob.jobdetailsone.JobDetailsOneActivity;
import com.marius.valeyou_admin.ui.activity.dashboard.profile.ProfileActivity;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;
import com.marius.valeyou_admin.ui.activity.main.MainActivity;
import com.marius.valeyou_admin.ui.activity.notification.NotificationActivity;
import com.marius.valeyou_admin.ui.fragment.myjob.upcoming.closedetails.JobDetailsActivity;
import com.marius.valeyou_admin.util.databinding.ImageViewBindingUtils;
import com.marius.valeyou_admin.util.event.SingleRequestEvent;
import com.marius.valeyou_admin.util.location.LiveLocationDetecter;
import com.marius.valeyou_admin.util.location.LocCallback;
import com.marius.valeyou_admin.util.permission.PermissionHandler;
import com.marius.valeyou_admin.util.permission.Permissions;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends AppFragment<FragmentHomeBinding, HomeFragmentVM> implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    public static final String TAG = "HomeFragment";
    private MainActivity mainActivity;
    List<CategoryDataBean> mylist = new ArrayList<>();

    String authKey;

    private Location mCurrentlocation;
    @Nullable
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected BindingFragment<HomeFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_home, HomeFragmentVM.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        String image = viewModel.sharedPref.getUserData().getImage();
        if (!image.isEmpty()) {
            ImageViewBindingUtils.setProfilePicture(binding.profileIMG, "http://3.17.254.50:4999/upload/" + image);
        }

        getCurrentLocation();
        Log.d("DeviceToke : ",viewModel.sharedPref.getUserData().getDeviceToken());


    }

    private void getCurrentLocation() {
        Permissions.check(baseContext, Manifest.permission.ACCESS_FINE_LOCATION, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                viewModel.liveLocationDetecter.startLocationUpdates();
                setMyLocation();
            }
        });

    }

    @Override
    protected void subscribeToEvents(final HomeFragmentVM vm) {
        authKey = vm.sharedPref.getUserData().getAuthKey();

        binding.setCheck(true);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        binding.bottomLayout.ftCategory.remoteRB.setChecked(true);
        binding.bottomLayout.ftCategory.remoteRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    vm.getCategories(0);
                }
            }
        });

        binding.bottomLayout.ftCategory.localRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    vm.getCategories(1);
                }
            }
        });
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader("");
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

                case  R.id.plus:

                    mainActivity.showDrawer();

                    break;
                case R.id.ll_profile:
                    mainActivity.showDrawer();

                    break;
                case R.id.iv_notification:
                    Intent intent = NotificationActivity.newIntent(getActivity());
                    startNewActivity(intent);
                    break;
                case R.id.cv_list:
                    binding.setCheck(false);
                    break;
                case R.id.cv_map:
                    binding.setCheck(true);
                    break;
                case R.id.fab_filter:

                    break;
                case R.id.iv_cancel:
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    break;

                case R.id.apply:

                    String st;
                    StringBuilder listString = new StringBuilder();

                    for (CategoryDataBean s : mylist) {
                        listString.append(s.getId() + ",");
                    }

                    int value =  binding.bottomLayout.ftDistance.distanceSlider.getProgress();
                    st = binding.bottomLayout.ftState.etState.getText().toString();

                    if (st.isEmpty()){
                        st = "";
                    }
                    vm.filterApi(vm.sharedPref.getUserData().getAuthKey(),
                            listString.toString(),String.valueOf(value),st);
                    break;
            }
        });


        vm.filterbeandata.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                       binding.bottomLayout.spinKit.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        binding.bottomLayout.spinKit.setVisibility(View.GONE);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        if (resource.message.equalsIgnoreCase("get successfully")) {
                            vm.success.setValue("Filter Applied");
                        }
                        vm.getMapList(vm.sharedPref.getUserData().getAuthKey(),"");
                        break;
                    case ERROR:
                        binding.bottomLayout.spinKit.setVisibility(View.GONE);
                        vm.error.setValue(resource.message);
                        break;
                    case WARN:
                        binding.bottomLayout.spinKit.setVisibility(View.GONE);
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });


        vm.mapListBean.observe(this, new SingleRequestEvent.RequestObserver<List<MapListModel>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<MapListModel>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        setMarkerOnMap(resource.data);
                        setRecyclerView(resource.data);
                        setRecyclerViewList(resource.data);
                        binding.setVariable(BR.bean,resource.data);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorized")){
                            Intent intent1 = LoginActivity.newIntent(getActivity());
                            startNewActivity(intent1, true, true);

                        }
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });

        vm.categoriesbeandata.observe(this, new SingleRequestEvent.RequestObserver<List<CategoryDataBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<CategoryDataBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        binding.setVariable(BR.bean,resource.data);
                        setFilterCatRv(resource.data);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorised")){
                            Intent intent1 = LoginActivity.newIntent(getActivity());
                            startNewActivity(intent1, true, true);

                        }
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                }
            }
        });



        vm.liveLocationDetecter.observe(this, new Observer<LocCallback>() {
            @Override
            public void onChanged(LocCallback locCallback) {
                switch (locCallback.getType()) {
                    case STARTED:
                        break;
                    case ERROR:
                        break;
                    case STOPPED:
                        break;
                    case OPEN_GPS:
                        viewModel.info.setValue("Prompt open");
                        try {
                            locCallback.getApiException().startResolutionForResult(getActivity(), LiveLocationDetecter.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case PROMPT_CANCEL:
                        viewModel.error.setValue("prompt cancel");
                        break;
                    case FOUND:

                        mCurrentlocation = locCallback.getLocation();
                        vm.liveLocationDetecter.removeLocationUpdates();

                        if (authKey!=null) {
                            vm.getMapList(authKey,"");
                        }
                        break;
                }

            }
        });


        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    binding.etSearch.clearFocus();
                    InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(  binding.etSearch.getWindowToken(), 0);
                    vm.getMapList(vm.sharedPref.getUserData().getAuthKey(),binding.etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });


        setBottomsheet();
        setFilterRecycler();
    }

    private void setMarkerOnMap(List<MapListModel> list) {
        if (googleMap == null)
            return;
        googleMap.clear();
        if (list == null)
            list = new ArrayList<>();

        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (int i = 0; i < list.size(); i++) {
            LatLng latLng = new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("")
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView("", 1))));
            builder.include(latLng);
        }
        // Set a listener for marker click.
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));

        CameraPosition pos = new CameraPosition.Builder()
                .target(new LatLng(mCurrentlocation.getLatitude(), mCurrentlocation.getLongitude()))
                .zoom(15)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
    }

    private GoogleMap.OnCameraIdleListener onCameraIdleListener = () -> {

    };

    private GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener = i -> {

    };

    @Override
    public void onMapClick(LatLng latLng) {

        Log.i("HelloLATLON", "latidute" + latLng.latitude + ":::long" + latLng.longitude);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        try {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } catch (Exception e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        setMyLocation();
    }


    private void setMyLocation() {
        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private Bitmap getMarkerBitmapFromView(String distance, int pos) {
        View customMarkerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    private void setRecyclerView(List<MapListModel> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        binding.rvList.setLayoutManager(layoutManager);
        binding.rvList.setAdapter(adapter);
        adapter.setList(list);
    }

    SimpleRecyclerViewAdapter<MapListModel, HolderMapItemsBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_map_items, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MapListModel>() {
        @Override
        public void onItemClick(View v, MapListModel o) {

            switch (v.getId()) {
                case R.id.ll_item:

                    Intent intent = JobDetailsActivity.newIntent(getActivity());
                    intent.putExtra("id",o.getId());
                    startNewActivity(intent);



                    break;
            }

        }
    });


    private void setRecyclerViewList(List<MapListModel> list) {
        if (list.size()>0) {
            binding.rvListData.setVisibility(View.VISIBLE);
            binding.txtNoRecord.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            binding.rvListData.setLayoutManager(layoutManager);
            binding.rvListData.setAdapter(adapterList);
            adapterList.setList(list);
        }else{
            binding.rvListData.setVisibility(View.GONE);
            binding.txtNoRecord.setVisibility(View.VISIBLE);
        }
    }

    SimpleRecyclerViewAdapter<MapListModel, HolderListItemsBinding> adapterList = new SimpleRecyclerViewAdapter(R.layout.holder_list_items, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MapListModel>() {
        @Override
        public void onItemClick(View v, MapListModel moreBean) {
            switch (v.getId()) {
                case R.id.ll_list_items:
                    Intent intent = JobDetailsActivity.newIntent(getActivity());
                    intent.putExtra("id",moreBean.getId());
                    startNewActivity(intent);

                    break;
            }


        }
    });

    public static BottomSheetBehavior bottomSheetBehavior;

    private void setBottomsheet() {

        viewModel.getCategories(0);

        bottomSheetBehavior = BottomSheetBehavior.from(binding.llOne);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        //binding.bottomLayout.ivDropDowm.setRotation(0);
                       // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //binding.bottomLayout.ivDropDowm.setRotation(180);
                       // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                       // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                float s = (1f - v / 2f);
            }
        });

    }

    private List<MoreBean> filterCat;

    private void setFilterRecycler() {
        binding.bottomLayout.setType(1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.bottomLayout.rvFilterCat.setLayoutManager(layoutManager);
        binding.bottomLayout.rvFilterCat.setAdapter(adapterCat);
        filterCat = getListData();
        adapterCat.setList(filterCat);
    }

    private List<MoreBean> getListData() {
        List<MoreBean> beanList = new ArrayList<>();
        beanList.add(new MoreBean(1, "Categories", 1));
      //  beanList.add(new MoreBean(2, "Rating", 0));
        beanList.add(new MoreBean(3, "Distance", 0));
        beanList.add(new MoreBean(4, "State", 0));
      //  beanList.add(new MoreBean(5, "No. of Jobs", 0));
        return beanList;
    }

    SimpleRecyclerViewAdapter<MoreBean, HolderFilterCategoryBinding> adapterCat =
            new SimpleRecyclerViewAdapter<>(R.layout.holder_filter_category, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
                @Override
                public void onItemClick(View v, MoreBean moreBean) {
                    for (int j = 0; j < filterCat.size(); j++) {
                        if (filterCat.get(j).getId() == moreBean.getId()) {
                            filterCat.get(j).setImage(1);
                        } else {
                            filterCat.get(j).setImage(0);
                        }
                    }
                    adapterCat.setList(filterCat);
                    binding.bottomLayout.setType(moreBean.getId());
                }
            });


    private void setFilterCatRv(List<CategoryDataBean> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.bottomLayout.ftCategory.rvCatFilter.setLayoutManager(layoutManager);
        binding.bottomLayout.ftCategory.rvCatFilter.setAdapter(catAdapter);
        catAdapter.setCatList(list);
    }


    FilterCatAdapter catAdapter = new FilterCatAdapter(getActivity(), new FilterCatAdapter.CheckCallback() {
        @Override
        public void onItemCheck(View v, int position, boolean isChecked, List<CategoryDataBean> list) {
            if (isChecked) {
             int id = list.get(position).getId();
             CategoryDataBean categoryDataBean =  new CategoryDataBean();
              categoryDataBean.setId(id);
               mylist.add(categoryDataBean);
            }else{
                if (mylist.size()>1) {
                    mylist.remove(position).getId();
                }else if (mylist.size()==1){
                    mylist.clear();
                }
            }
        }
    });



}
