package com.example.liaochieh_yu.gogo2.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liaochieh_yu.gogo2.R;

/**
 * Created by liaochieh-yu on 2018/3/20.
 */

public class HomeFragment extends Fragment{
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2  ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        /**
         *Inflate tab_layout and setup Views.
         */
        View view=inflater.inflate(R.layout.home_tab,null);
        tabLayout=view.findViewById(R.id.tabs);
        viewPager=view.findViewById(R.id.viewpager);
       

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        return view;

    }
    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter (FragmentManager fm) {
            super(fm);
         }
        /**
         * Return fragment with respect to Position .
         */
        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new HomeTabLeftFragment();
                case 1 : return new HomeTabRightFragment();
            }
            return null;
        }
        @Override
        public int getCount() {
            return int_items;
        }
        /**
         * This method returns the title of the tab according to the position.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return "最新活動";
                case 1 :
                    return "促銷商品";
             }
            return null;
        }
    }
}
