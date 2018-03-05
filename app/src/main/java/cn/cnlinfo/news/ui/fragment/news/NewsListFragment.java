package cn.cnlinfo.news.ui.fragment.news;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.shizhefei.mvc.MVCHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.mvc.datasource.NeteastNewsSummaryDataSource;
import cn.cnlinfo.news.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.news.rx.entity.NeteastNewsSummary;
import cn.cnlinfo.news.ui.fragment.BaseFragment;
import cn.cnlinfo.news.ui.fragment.news.adapter.NeteastNewsSummaryListAdapter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class NewsListFragment extends BaseFragment {

    private static String CHANNELIDKEY = "channelId";
    private static String CHANNELTYPEKEY = "channelType";
    private static String CHANNELINDEXKEY = "channelIndex";
    @BindView(R.id.rotate_header_recycle_view)
    RecyclerView rotateHeaderRecycleView;
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;
    Unbinder unbinder;
    private String channel_id = null;
    private String channel_type = null;
    private String channel_index = null;
    private MVCHelper mvcHelper;

    public static NewsListFragment newInstace(String channelId, String channelType, long channelIndex) {
        NewsListFragment newsListFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CHANNELIDKEY, channelId);
        bundle.putString(CHANNELTYPEKEY, channelType);
        bundle.putLong(CHANNELINDEXKEY, channelIndex);
        newsListFragment.setArguments(bundle);
        return newsListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            channel_id = getArguments().getString(CHANNELIDKEY);
            channel_type = getArguments().getString(CHANNELTYPEKEY);
            channel_index = getArguments().getString(CHANNELINDEXKEY);
        }
        setNewsListData();
    }


    private void setNewsListData(){
        setMaterialHeader(rotateHeaderListViewFrame);
        mvcHelper = new MVCUltraHelper<List<NeteastNewsSummary>>(rotateHeaderListViewFrame);
        // 设置数据源
        mvcHelper.setDataSource(new NeteastNewsSummaryDataSource(channel_id,channel_type));
        // 设置适配器
        mvcHelper.setAdapter(new NeteastNewsSummaryListAdapter(getActivity()));
        // 加载数据
        mvcHelper.refresh();
    }
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_news_list);
        unbinder = ButterKnife.bind(this,getContentView());
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }

}
