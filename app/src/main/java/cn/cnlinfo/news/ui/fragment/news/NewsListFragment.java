package cn.cnlinfo.news.ui.fragment.news;

import android.os.Bundle;

import cn.cnlinfo.news.R;
import cn.cnlinfo.news.ui.fragment.BaseFragment;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class NewsListFragment extends BaseFragment{

    private static String CHANNELIDKEY = "channelId";
    private static String CHANNELTYPEKEY = "channelType";
    private static String CHANNELINDEXKEY = "channelIndex";
    private String channel_id = null;
    private String channel_type = null;
    private String channel_index = null;

    public static NewsListFragment newInstace(String channelId,String channelType,long channelIndex){
            NewsListFragment newsListFragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CHANNELIDKEY,channelId);
            bundle.putString(CHANNELTYPEKEY,channelType);
            bundle.putLong(CHANNELINDEXKEY,channelIndex);
            newsListFragment.setArguments(bundle);
            return newsListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            channel_id = getArguments().getString(CHANNELIDKEY);
            channel_type = getArguments().getString(CHANNELTYPEKEY);
            channel_index = getArguments().getString(CHANNELINDEXKEY);
        }
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_news_list);
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();

    }
}
