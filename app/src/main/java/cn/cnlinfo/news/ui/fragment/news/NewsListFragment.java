package cn.cnlinfo.news.ui.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.shizhefei.mvc.MVCHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.manager.PhoneManager;
import cn.cnlinfo.news.mvc.datasource.NeteastNewsSummaryDataSource;
import cn.cnlinfo.news.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.news.rx.entity.NeteastNewsSummary;
import cn.cnlinfo.news.rx.rxbus.RxBus;
import cn.cnlinfo.news.ui.activity.news_detail.NeteastNewsDetailActivity;
import cn.cnlinfo.news.ui.fragment.BaseFragment;
import cn.cnlinfo.news.ui.fragment.news.adapter.BaseRecyclerAdapter;
import cn.cnlinfo.news.ui.fragment.news.adapter.NeteastNewsSummaryListAdapter;
import cn.cnlinfo.news.utils.BaseSpacesItemDecoration;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class NewsListFragment extends BaseFragment implements BaseRecyclerAdapter.ItemClickCallback<NeteastNewsSummary>{

    private static String CHANNELIDKEY = "channelId";
    private static String CHANNELTYPEKEY = "channelType";
    private static String CHANNELINDEXKEY = "channelIndex";
    @BindView(R.id.rotate_header_recycle_view)
    RecyclerView rotateHeaderRecycleView;
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;
    Unbinder unbinder;
    private static String channel_id ;
    private static String channel_type ;
    private static long channel_index ;
    private MVCHelper mvcHelper;
    private NeteastNewsSummaryListAdapter adapter;
    private Observable<Integer> refreshData;

    public static NewsListFragment newInstance(String channelId, String channelType, long channelIndex) {
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
            channel_index = getArguments().getLong(CHANNELINDEXKEY);
//            Logger.d(channel_index);
        }
        //注册tag刷新事件
        initRefreshData();
    }

    public void initRefreshData() {
        refreshData = RxBus.get().register("enableRefreshLayoutOrScrollRecyclerView",Integer.class);
        refreshData.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                if (integer.equals(channel_index)){
                    mvcHelper.refresh();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list,container,false);
        unbinder = ButterKnife.bind(this,view);
        setNewsListData();
        return view;
    }

    private void setNewsListData(){
        setMaterialHeader(rotateHeaderListViewFrame);
        rotateHeaderRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        rotateHeaderRecycleView.addItemDecoration(new BaseSpacesItemDecoration(PhoneManager.dip2px(8)));
        mvcHelper = new MVCUltraHelper<List<NeteastNewsSummary>>(rotateHeaderListViewFrame);
        Logger.d(channel_type+":"+channel_id);
        // 设置数据源
        mvcHelper.setDataSource(new NeteastNewsSummaryDataSource(channel_id,channel_type));
        adapter = new NeteastNewsSummaryListAdapter(getActivity());
        adapter.setItemClickCallback(this);
        // 设置适配器
        mvcHelper.setAdapter(adapter);
        // 加载数据
        mvcHelper.refresh();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        RxBus.get().unregister("enableRefreshLayoutOrScrollRecyclerView",refreshData);
    }

    @Override
    public void onItemClicked(int position, @NonNull NeteastNewsSummary entity) {
        Logger.d(position);
        Intent intent = new Intent(getActivity(), NeteastNewsDetailActivity.class);
        intent.putExtra("postId",entity.getPostid());
        intent.putExtra("imgSrc",entity.getImgsrc());
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_entry,R.anim.fade_exit);
    }
}
