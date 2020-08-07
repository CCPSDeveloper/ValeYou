package com.marius.valeyou.ui.fragment.home;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.functions.Consumer;

import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
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
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.LocationsBean;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.beans.respbean.ProviderNearMe;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogLogoutBinding;
import com.marius.valeyou.databinding.FragmentHomeBinding;
import com.marius.valeyou.databinding.HolderFilterCategoryBinding;
import com.marius.valeyou.databinding.HolderListItemsBinding;
import com.marius.valeyou.databinding.HolderMapItemsBinding;
import com.marius.valeyou.databinding.HolderMoreBinding;
import com.marius.valeyou.databinding.HolderSetCategoryBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.di.module.GlideApp;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.notification.NotificationActivity;
import com.marius.valeyou.ui.activity.provider_profile.ProviderProfileActivity;
import com.marius.valeyou.ui.fragment.more.aboutus.AboutUsFragment;
import com.marius.valeyou.ui.fragment.more.changepassword.ChangePasswordFragment;
import com.marius.valeyou.ui.fragment.more.helpnsupport.HelpAndSupportFragment;
import com.marius.valeyou.ui.fragment.more.privacy_policy.PrivacyPolicyFragment;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.location.LocCallback;
import com.marius.valeyou.util.misc.RxBus;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends AppFragment<FragmentHomeBinding, HomeFragmentVM> implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    public static final String TAG = "HomeFragment";
    private MainActivity mainActivity;

    private Location mCurrentlocation;
    @Nullable
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private int page = 1;
    private int limit = 10;
    private List<ProviderNearMe> nearMes;
    @Inject
    SharedPref sharedPref;
    @Inject
    RxBus rxBus;

    private int rate = 0;
    private int cat_type = 0;

    private List<CategoryListBean> categoryListBeans;

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
        binding.setBean(sharedPref.getUserData());
        binding.nav.setBean(sharedPref.getUserData());
        getCurrentLocation();
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
        Log.d("Credential : ", vm.sharedPref.getUserData().getId() + "===" + vm.sharedPref.getUserData().getAuthKey());
        binding.setCheck(true);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        setActionOnSearch();
        vm.rButtonEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer) {
                    case R.id.rb_remote_job:
                        cat_type = 0;
                        vm.getListOfCateegory(cat_type, "");
                        break;
                    case R.id.rb_local_job:
                        cat_type = 1;
                        vm.getListOfCateegory(cat_type, "");
                        break;
                }
            }
        });

        setActionOnCatSearch();

        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {
                case R.id.iv_notification:
                    /*Intent intent = NotificationActivity.newIntent(getActivity());
                    startNewActivity(intent);*/
                    break;
                case R.id.cv_list:
                    binding.setCheck(false);
                    break;
                case R.id.cv_map:
                    binding.setCheck(true);
                    break;
                case R.id.iv_filter:
                    Intent intent = NotificationActivity.newIntent(getActivity());
                    startNewActivity(intent);
                    //mainActivity.addSubFragment(TAG, new MoreFragment());
                case R.id.iv_cancel:
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    break;
                case R.id.cv_profile:
                    binding.drawerView.openDrawer(GravityCompat.START);
                    break;

                case R.id.rl_profile:
                    binding.drawerView.openDrawer(GravityCompat.START);
                    break;

                case R.id.iv_profile:
                    binding.drawerView.openDrawer(GravityCompat.START);
                    break;

                case R.id.cv_pic:
                    binding.drawerView.openDrawer(GravityCompat.START);
                    break;

                case R.id.iv_close:
                    binding.drawerView.closeDrawers();
                    break;
                case R.id.view_five:
                    binding.bottomLayout.ftRating.setType(5);
                    rate = 0;
                    break;
                case R.id.view_four:
                    binding.bottomLayout.ftRating.setType(4);
                    rate = 3;
                    break;
                case R.id.view_three:
                    binding.bottomLayout.ftRating.setType(3);
                    rate = 4;
                    break;
                case R.id.view_two:
                    binding.bottomLayout.ftRating.setType(2);
                    rate = 4;
                    break;
                case R.id.view_one:
                    binding.bottomLayout.ftRating.setType(1);
                    rate = 5;
                    break;
                case R.id.b_apply:
                    // Apply Filter
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    applyFilter();
                    break;

                case R.id.view_profile:
                    Intent intent1 = ProfileActivity.newIntent(getActivity());
                    startNewActivity(intent1);
                    break;
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
                        sharedPref.put(Constants.LATITUDE, String.valueOf(mCurrentlocation.getLatitude()));
                        sharedPref.put(Constants.LONGITUDE, String.valueOf(mCurrentlocation.getLongitude()));
                        vm.liveLocationDetecter.removeLocationUpdates();
                        callApi("");
                        if (nearMes != null) {
                            setMarkerOnMap(nearMes);
                        }
                        break;
                }

            }
        });

        vm.providerList.observe(this, new SingleRequestEvent.RequestObserver<List<ProviderNearMe>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<ProviderNearMe>> resource) {
                switch (resource.status) {
                    case LOADING:

                        break;
                    case SUCCESS:
                        nearMes = resource.data;
                        adapter.setList(resource.data);
                        adapterList.setList(resource.data);
                        setMarkerOnMap(resource.data);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase("unauthorized")) {
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

        vm.logout.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        sharedPref.deleteAll();
                        LoginManager.getInstance().logOut();
                        Intent intent1 = LoginActivity.newIntent(getActivity());
                        startNewActivity(intent1, true, true);
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

        vm.categoryList.observe(this, new SingleRequestEvent.RequestObserver<List<CategoryListBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<CategoryListBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        categoryListBeans = resource.data;
                        ftAdapter.setList(resource.data);
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        vm.addFilterEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        vm.success.setValue(resource.message);
                        callApi("");
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        rxBus.toObserverable().subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object object) throws Exception {
                if (object instanceof String) {
                    String str = (String) object;
                    if (str.equalsIgnoreCase(Constants.FILTER)) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }
            }
        });

        setBottomsheet();
        setRecyclerView();
        setRecyclerViewList();
        setFilterRecycler();
        if (nearMes != null) {
            adapter.setList(nearMes);
            adapterList.setList(nearMes);
        }
        moveViewWithDrawer();
        setRecyclerViewMore();
    }

    private void moveViewWithDrawer() {
        binding.nav.setName(sharedPref.getUserData().getFirstName() + " " + sharedPref.getUserData().getLastName());
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                binding.drawerView, binding.toolbar, R.string.acc_drawer_open, R.string.acc_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                /*binding.content.setTranslationX(slideOffset * drawerView.getWidth());
                binding.drawerView.bringChildToFront(drawerView);
                binding.drawerView.requestLayout();
                binding.drawerView.setScrimColor(Color.TRANSPARENT);*/
                binding.drawerView.setScrimColor(Color.TRANSPARENT);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        binding.drawerView.addDrawerListener(mDrawerToggle);
    }

    private void callApi(String search) {
        viewModel.getProviderList(mCurrentlocation, limit, page, search);
    }

    private void setMarkerOnMap(List<ProviderNearMe> driverBeans) {
        if (googleMap == null)
            return;
        googleMap.clear();
        if (driverBeans == null)
            driverBeans = new ArrayList<>();

        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (int i = 0; i < driverBeans.size(); i++) {
            LatLng latLng = new LatLng(driverBeans.get(i).getLatitude(), driverBeans.get(i).getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("")
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(driverBeans.get(i).getImage(), 1))));
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

    public List<LocationsBean> getAllLocations() {
        List<LocationsBean> driverBeans = new ArrayList<>();
        driverBeans.add(new LocationsBean(30.617779404567703, 76.8352809548378));
        driverBeans.add(new LocationsBean(30.615712907170398, 76.84537377208471));
        driverBeans.add(new LocationsBean(30.60860851706435, 76.83920234441757));
        driverBeans.add(new LocationsBean(30.602075573161137, 76.84101518243551));
        driverBeans.add(new LocationsBean(30.60192782005248, 76.8503576517105));
        return driverBeans;
    }

    private Bitmap getMarkerBitmapFromView(String image_url, int pos) {
        View customMarkerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        ImageView provider = customMarkerView.findViewById(R.id.iv_picture);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.override(50, 50);
        options.placeholder(R.drawable.app_logo);
        GlideApp.with(getActivity()).load(Constants.IMAGE_BASE_URL + image_url).apply(options).into(provider);
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

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        binding.rvList.setLayoutManager(layoutManager);
        binding.rvList.setAdapter(adapter);
    }

    SimpleRecyclerViewAdapter<ProviderNearMe, HolderMapItemsBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_map_items, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<ProviderNearMe>() {
        @Override
        public void onItemClick(View v, ProviderNearMe nearMe) {
            switch (v != null ? v.getId() : 0) {
                case R.id.cv_items:
                    Intent intent = ProviderProfileActivity.newIntent(getActivity(), nearMe.getId(), nearMe.getFav());
                    startNewActivity(intent);
                    break;
            }
        }
    });

    private void setRecyclerViewList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvListData.setLayoutManager(layoutManager);
        binding.rvListData.setAdapter(adapterList);
    }

    SimpleRecyclerViewAdapter<ProviderNearMe, HolderListItemsBinding> adapterList = new SimpleRecyclerViewAdapter(R.layout.holder_list_items, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<ProviderNearMe>() {
        @Override
        public void onItemClick(View v, ProviderNearMe nearMe) {
            switch (v != null ? v.getId() : 0) {
                case R.id.cv_items:
                    Intent intent = ProviderProfileActivity.newIntent(getActivity(), nearMe.getId(), nearMe.getFav());
                    startNewActivity(intent);
                    break;
            }
        }
    });

    private BottomSheetBehavior bottomSheetBehavior;

    private void setBottomsheet() {
        viewModel.getListOfCateegory(0, "");
        binding.bottomLayout.ftRating.setType(5);
        bottomSheetBehavior = BottomSheetBehavior.from(binding.llOne);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        //binding.bottomLayout.ivDropDowm.setRotation(0);
                        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //binding.bottomLayout.ivDropDowm.setRotation(180);
                        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                float s = (1f - v / 2f);
            }
        });
        setFilterCatRv();
        setStateFilter();
    }

    private void setFilterCatRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.bottomLayout.ftCategory.rvCatFilter.setLayoutManager(layoutManager);
        binding.bottomLayout.ftCategory.rvCatFilter.setAdapter(ftAdapter);
        //ftAdapter.setList(getFtCategory());
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
        beanList.add(new MoreBean(2, "Rating", 0));
        beanList.add(new MoreBean(3, "Distance", 0));
        beanList.add(new MoreBean(4, "State", 0));
        beanList.add(new MoreBean(5, "No. of Jobs", 0));
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

    private List<MoreBean> getFtCategory() {
        List<MoreBean> beanList = new ArrayList<>();
        beanList.add(new MoreBean(1, "Amazonas", 1));
        beanList.add(new MoreBean(2, "Para", 0));
        beanList.add(new MoreBean(3, "Mato Grosso", 0));
        beanList.add(new MoreBean(4, "Minas Gerais", 0));
        beanList.add(new MoreBean(5, "Bahia", 0));
        beanList.add(new MoreBean(6, "Mato Grosso do Sul", 0));
        beanList.add(new MoreBean(7, "Goias", 0));
        beanList.add(new MoreBean(8, "Maranhao", 0));
        return beanList;
    }

    SimpleRecyclerViewAdapter<CategoryListBean, HolderSetCategoryBinding> ftAdapter =
            new SimpleRecyclerViewAdapter<>(R.layout.holder_set_category, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<CategoryListBean>() {
                @Override
                public void onItemClick(View v, CategoryListBean moreBean) {
                    for (int j = 0; j < categoryListBeans.size(); j++) {
                        if (categoryListBeans.get(j).getId() == moreBean.getId()) {
                            if (moreBean.isCheck()) {
                                categoryListBeans.get(j).setCheck(false);
                            } else {
                                categoryListBeans.get(j).setCheck(true);
                            }
                        }
                    }
                    ftAdapter.setList(categoryListBeans);
                }
            });

    private List<MoreBean> moreBeans;

    private void setStateFilter() {
        moreBeans = getFtCategory();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.bottomLayout.ftState.rvStateFilter.setLayoutManager(layoutManager);
        binding.bottomLayout.ftState.rvStateFilter.setAdapter(stateAdapter);
        stateAdapter.setList(moreBeans);
    }

    SimpleRecyclerViewAdapter<MoreBean, HolderSetCategoryBinding> stateAdapter =
            new SimpleRecyclerViewAdapter<>(R.layout.holder_set_state, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
                @Override
                public void onItemClick(View v, MoreBean moreBean) {
                    for (int j = 0; j < moreBeans.size(); j++) {
                        if (moreBeans.get(j).getId() == moreBean.getId()) {
                            state = moreBeans.get(j).getName();
                            moreBeans.get(j).setImage(1);
                        } else {
                            moreBeans.get(j).setImage(0);
                        }
                    }
                    stateAdapter.setList(moreBeans);
                }
            });

    private void setRecyclerViewMore() {
        binding.nav.rvMore.setLayoutManager(new LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false));
        SimpleRecyclerViewAdapter<MoreBean, HolderMoreBinding> adapter = new SimpleRecyclerViewAdapter<>(R.layout.holder_more, com.marius.valeyou.BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
            @Override
            public void onItemClick(View v, MoreBean shopBean) {
                binding.drawerView.closeDrawers();
                Intent intent;
                switch (shopBean.getId()) {
                    case 1:
                        intent = ChangePasswordFragment.newIntent(getActivity());
                        startNewActivity(intent);
                        break;
                    case 2:
                        mainActivity.addSubFragment(TAG, PrivacyPolicyFragment.newInstance("Privacy Policy", 1));
                        break;
                    case 3:
                        mainActivity.addSubFragment(TAG, PrivacyPolicyFragment.newInstance("Terms & Conditions", 2));
                        break;
                    case 4:
                        mainActivity.addSubFragment(TAG, AboutUsFragment.newInstance());
                        break;
                    case 5:
                        mainActivity.addSubFragment(TAG, HelpAndSupportFragment.newInstance());
                        break;
                    case 6:
                        dialogDeactivateAccount();
                        break;
                }
            }
        });
        binding.nav.rvMore.setAdapter(adapter);
        adapter.setList(getMoreData());
    }

    /* load data in list */
    private List<MoreBean> getMoreData() {
        List<MoreBean> menuBeanList = new ArrayList<>();
        menuBeanList.add(new MoreBean(1, "Change Password", R.drawable.ic_change_password_icon));
        menuBeanList.add(new MoreBean(2, "Privacy Policy", R.drawable.ic_privacy_policy_icon));
        menuBeanList.add(new MoreBean(3, "Terms & Conditions", R.drawable.ic_privacy_policy_icon));
        menuBeanList.add(new MoreBean(4, "About US", R.drawable.ic_about_us_icon));
        menuBeanList.add(new MoreBean(5, "Help And Support", R.drawable.ic_help_icon));
        menuBeanList.add(new MoreBean(6, "Logout", R.drawable.ic_logout_icon));
        return menuBeanList;
    }

    private BaseCustomDialog<DialogLogoutBinding> dialogSuccess;

    private void dialogDeactivateAccount() {
        dialogSuccess = new BaseCustomDialog<>(getActivity(), R.layout.dialog_logout, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogSuccess.dismiss();
                            viewModel.logout();
                            break;
                        case R.id.b_cancel:
                            dialogSuccess.dismiss();
                            break;
                    }
                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogSuccess.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogSuccess.show();
    }

    private void setActionOnSearch() {
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(binding.etSearch.getWindowToken(), 0);
                    callApi(binding.etSearch.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }


    private void setActionOnCatSearch() {
        binding.bottomLayout.ftCategory.etCatSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(binding.bottomLayout.ftCategory.etCatSearch.getWindowToken(), 0);
                    viewModel.getListOfCateegory(cat_type, binding.bottomLayout.ftCategory.etCatSearch.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    private String cat_ids = "";
    private String state = "";

    private void applyFilter() {
        cat_ids = "";
        int distance = binding.bottomLayout.ftDistance.distanceValue.getProgress() * 10;
        int no_of_job = binding.bottomLayout.ftJob.vsJob.getProgress() * 10;
        for (int j = 0; j < categoryListBeans.size(); j++) {
            if (categoryListBeans.get(j).isCheck()) {
                cat_ids = cat_ids + categoryListBeans.get(j).getId() + ",";
            }
        }
        if (!cat_ids.equalsIgnoreCase("") && cat_ids.length() > 1) {
            cat_ids = cat_ids.substring(0, cat_ids.length() - 1);
        }
        viewModel.addFilter(cat_ids, rate, distance, state, no_of_job);
    }

}
