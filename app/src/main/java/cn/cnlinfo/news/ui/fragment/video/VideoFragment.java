package cn.cnlinfo.news.ui.fragment.video;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.shizhefei.mvc.MVCHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.API;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.manager.PhoneManager;
import cn.cnlinfo.news.mvc.datasource.VideoListDataSource;
import cn.cnlinfo.news.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.news.rx.entity.NeteastNewsSummary;
import cn.cnlinfo.news.ui.fragment.BaseFragment;
import cn.cnlinfo.news.ui.fragment.video.adapter.ShowVideoListAdapter;
import cn.cnlinfo.news.utils.BaseSpacesItemDecoration;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by JP on 2017/10/11 0011.
 */

public class VideoFragment extends BaseFragment {
    @BindView(R.id.rotate_header_recycle_view)
    RecyclerView rotateHeaderRecycleView;
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;
    private Unbinder unbinder;
    private MVCHelper mvcHelper;
    private ShowVideoListAdapter adapter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_video);
        unbinder = ButterKnife.bind(this, getContentView());
        setVideoListData();
    }

    private void setVideoListData() {
        setMaterialHeader(rotateHeaderListViewFrame);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//不设置的话，图片闪烁错位，有可能有整列错位的情况。
        rotateHeaderRecycleView.setLayoutManager(layoutManager);
        rotateHeaderRecycleView.addItemDecoration(new BaseSpacesItemDecoration(PhoneManager.dip2px(8)));
        mvcHelper = new MVCUltraHelper<List<NeteastNewsSummary>>(rotateHeaderListViewFrame);
        // 设置数据源
        mvcHelper.setDataSource(new VideoListDataSource(API.VIDEO_HOT_ID));
        adapter = new ShowVideoListAdapter(getActivity());
        // 设置适配器
        mvcHelper.setAdapter(adapter);
        // 加载数据
        mvcHelper.refresh();
    }


    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }
}
