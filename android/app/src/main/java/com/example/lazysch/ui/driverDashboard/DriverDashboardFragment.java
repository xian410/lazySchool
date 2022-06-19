package com.example.lazysch.ui.driverDashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.lazysch.FragmentListPageDriver;
import com.example.lazysch.R;
import com.example.lazysch.databinding.FragmentDriverdashboardBinding;
import com.example.lazysch.FragmentListPage;
import com.example.lazysch.ListPageAdapter;

import java.util.ArrayList;

public class DriverDashboardFragment extends Fragment {

    private FragmentDriverdashboardBinding binding;
    View root = null;
    ViewPager2 viewPager2;
    private LinearLayout ll_going,ll_completed,ll_canceled;
    private TextView tv_going,tv_completed,tv_canceled,tv_current;;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DriverDashboardViewModel driverDashboardViewModel =
                new ViewModelProvider(this).get(DriverDashboardViewModel.class);

        binding = FragmentDriverdashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        initPager();
        initTabView();
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void initTabView(){
        ll_going = root.findViewById(R.id.ll_going);
        ll_going.setOnClickListener(this::onClick);
        ll_completed = root.findViewById(R.id.ll_completed);
        ll_completed.setOnClickListener(this::onClick);
        ll_canceled = root.findViewById(R.id.ll_canceled);
        ll_canceled.setOnClickListener(this::onClick);

        tv_going = root.findViewById(R.id.tv_going);
        tv_completed = root.findViewById(R.id.tv_completed);
        tv_canceled = root.findViewById(R.id.tv_canceled);
        tv_going.setSelected(true);
        tv_current = tv_going;
    }

    private void initPager(){
        viewPager2 = root.findViewById(R.id.listpage_viewpage);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentListPageDriver.newInstance("1"));
        fragments.add(FragmentListPageDriver.newInstance("2"));
        fragments.add(FragmentListPageDriver.newInstance("3"));
        ListPageAdapter pageadapter = new ListPageAdapter(getChildFragmentManager(),getLifecycle(),fragments);
        viewPager2.setAdapter(pageadapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }


    private void changeTab(int position) {
        tv_current.setSelected(false);
        switch (position){
            case R.id.ll_going:
                viewPager2.setCurrentItem(0);
            case 0:
                tv_going.setSelected(true);
                tv_current = tv_going;
                break;

            case R.id.ll_completed:
                viewPager2.setCurrentItem(1);
            case 1:
                tv_completed.setSelected(true);
                tv_current = tv_completed;
                break;

            case R.id.ll_canceled:
                viewPager2.setCurrentItem(2);
            case 2:
                tv_canceled.setSelected(true);
                tv_current = tv_canceled;
                break;
        }
    }

    public void onClick(View view) {
        changeTab(view.getId());
    }
}