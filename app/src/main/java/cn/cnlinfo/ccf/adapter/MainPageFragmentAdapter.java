package cn.cnlinfo.ccf.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.cnlinfo.ccf.fragment.CCMallFragment;
import cn.cnlinfo.ccf.fragment.CCUnionFragment;
import cn.cnlinfo.ccf.fragment.GaugePanelFragment;
import cn.cnlinfo.ccf.fragment.MainPageFragment;
import cn.cnlinfo.ccf.fragment.TradingCenterFragment;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class MainPageFragmentAdapter extends FragmentPagerAdapter {


    public MainPageFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MainPageFragment();
                break;
            case 1:
                fragment = new GaugePanelFragment();
                break;
            case 2:
                fragment = new TradingCenterFragment();
                break;
            case 3:
                fragment = new CCMallFragment();
                break;
            case 4:
                fragment = new CCUnionFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
