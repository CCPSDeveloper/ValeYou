package com.marius.valeyou_admin.ui.activity.tourpage;

import android.app.Activity;
import android.content.Intent;
import android.view.View;


import com.marius.valeyou_admin.R;
import com.marius.valeyou_admin.data.beans.base.TourBean;
import com.marius.valeyou_admin.databinding.ActivityTourBinding;
import com.marius.valeyou_admin.di.base.adapter.PagerAdapterList;
import com.marius.valeyou_admin.di.base.view.AppActivity;
import com.marius.valeyou_admin.ui.activity.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

public class TourActivity extends AppActivity<ActivityTourBinding, TourActivityVM> {

    public static Intent newInstance(Activity activity) {
        Intent intent = new Intent(activity, TourActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<TourActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_tour, TourActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(TourActivityVM vm) {
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view.getId()) {
                    case R.id.b_start_signup:
                        if (binding.vpTour.getCurrentItem() == 2) {
                            Intent intent = LoginActivity.newIntent(TourActivity.this);
                            startNewActivity(intent, true);
                        } else {
                            binding.vpTour.setCurrentItem(binding.vpTour.getCurrentItem() + 1);
                        }
                        break;
                }
            }
        });
        setViewpager();
    }

    private void setViewpager() {
        binding.setValue(getResources().getString(R.string.next));
        PagerAdapterList pagerAdapterList = new PagerAdapterList(getSupportFragmentManager(), getTourBeans());
        binding.vpTour.setAdapter(pagerAdapterList);
        binding.tabDots.setViewPager(binding.vpTour);
        binding.vpTour.setCurrentItem(0);
        binding.vpTour.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    binding.setValue(getResources().getString(R.string.get_started));
                } else {
                    binding.setValue(getResources().getString(R.string.next));
                }
            }
        });
    }

    public List<TourBean> getTourBeans() {
        List<TourBean> tourBeans = new ArrayList<>();
        tourBeans.add(new TourBean(1, "Find the tight trusted of caregiver for your family", "Book Clearers at the Comfort\nof you home.", R.drawable.tour_one));
        tourBeans.add(new TourBean(2, "Find the tight trusted of One place for repairing solution", "Book Clearers at the Comfort\nof you home.", R.drawable.tour_two));
        tourBeans.add(new TourBean(3, "Find the tight trusted of One place for repairing solutions", "Book Clearers of the comfort\nof you home.", R.drawable.tour_three));
        return tourBeans;
    }

}
