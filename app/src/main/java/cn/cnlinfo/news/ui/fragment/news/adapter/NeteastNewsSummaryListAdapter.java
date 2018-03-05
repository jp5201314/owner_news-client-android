package cn.cnlinfo.news.ui.fragment.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import cn.cnlinfo.news.rx.entity.NeteastNewsSummary;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class NeteastNewsSummaryListAdapter extends BaseRecyclerAdapter<List<NeteastNewsSummary>>{

    private Context context;
    public NeteastNewsSummaryListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
