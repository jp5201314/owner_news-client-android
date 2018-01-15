package cn.cnlinfo.ccf.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class MainPageFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    public MainPageFragmentAdapter(List<Fragment> fragmentList,FragmentManager fm) {
        super(fm);
        this.fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
