package cn.cnlinfo.news.ui.fragment.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import cn.cnlinfo.news.ui.fragment.news.NewsListFragment;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class NewsChannelFragmentAdapter extends FragmentStatePagerAdapter {

    private List<NewsListFragment> listFragments;
    private List<String> tabTitle;
    private FragmentManager mFragmentManager;
    public NewsChannelFragmentAdapter(FragmentManager fm, List<NewsListFragment> listFragments, List<String> tabTitle) {
        super(fm);
        this.mFragmentManager = fm;
        this.listFragments = listFragments;
        this.tabTitle = tabTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
    }

    /**
     * 更新频道，前面固定的不更新，后面一律更新
     *
     * @param fragments
     * @param titles
     */
    public void updateFragments(List<NewsListFragment> fragments, List<String> titles) {
        for (int i = 0; i < listFragments.size(); i++) {
            final NewsListFragment fragment = listFragments.get(i);
            final FragmentTransaction ft = mFragmentManager.beginTransaction();
            if (i > 2) {
                ft.remove(fragment);
                listFragments.remove(i);
                i--;
            }
            ft.commit();
        }
        for (int i = 0; i < fragments.size(); i++) {
            if (i > 2) {
                listFragments.add(fragments.get(i));
            }
        }
        this.tabTitle = titles;
        notifyDataSetChanged();
    }
}
