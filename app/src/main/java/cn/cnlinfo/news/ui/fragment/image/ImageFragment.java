package cn.cnlinfo.news.ui.fragment.image;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.shizhefei.mvc.MVCHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.manager.PhoneManager;
import cn.cnlinfo.news.mvc.datasource.ImageListDataSource;
import cn.cnlinfo.news.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.news.rx.entity.NeteastNewsSummary;
import cn.cnlinfo.news.rx.entity.SinaPhotoDetail;
import cn.cnlinfo.news.ui.fragment.BaseFragment;
import cn.cnlinfo.news.ui.fragment.image.adapter.ShowImageListAdapter;
import cn.cnlinfo.news.ui.fragment.news.adapter.BaseRecyclerAdapter;
import cn.cnlinfo.news.utils.BaseSpacesItemDecoration;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;


/**
 * Created by Jp on 2017/10/11 0011.
 */

public class ImageFragment extends BaseFragment  {

    @BindView(R.id.rotate_header_recycle_view)
    RecyclerView rotateHeaderRecycleView;
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout rotateHeaderListViewFrame;
    private Unbinder unbinder;
    private MVCHelper mvcHelper;
    private ShowImageListAdapter adapter;


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_image);
        unbinder = ButterKnife.bind(this, getContentView());
        setImageListData();
    }

    private void setImageListData() {
        setMaterialHeader(rotateHeaderListViewFrame);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//不设置的话，图片闪烁错位，有可能有整列错位的情况。
        rotateHeaderRecycleView.setLayoutManager(layoutManager);
        rotateHeaderRecycleView.addItemDecoration(new BaseSpacesItemDecoration(PhoneManager.dip2px(8)));
        mvcHelper = new MVCUltraHelper<List<NeteastNewsSummary>>(rotateHeaderListViewFrame);
        // 设置数据源
        mvcHelper.setDataSource(new ImageListDataSource());
        adapter = new ShowImageListAdapter(getActivity());
        // 设置适配器
        mvcHelper.setAdapter(adapter);
        // 加载数据
        mvcHelper.refresh();
        adapter.setItemClickCallback(new BaseRecyclerAdapter.ItemClickCallback<SinaPhotoDetail>() {
            @Override
            public void onItemClicked(int position, SinaPhotoDetail entity) {

            }
        });
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }
}
