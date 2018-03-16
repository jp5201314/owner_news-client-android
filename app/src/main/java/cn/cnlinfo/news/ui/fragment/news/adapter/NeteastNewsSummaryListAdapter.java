package cn.cnlinfo.news.ui.fragment.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.rx.entity.NeteastNewsSummary;
import cn.cnlinfo.news.utils.GlideUtils;

/**
 * Created by JP on 2018/3/5 0005.
 * 新闻列表适配器
 */

public class NeteastNewsSummaryListAdapter extends BaseRecyclerAdapter<NeteastNewsSummary>  {

    private Context context;

    public NeteastNewsSummaryListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news_summary, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
//            Logger.d(list.toString());
            if (list!=null&&list.size()>0){
                NeteastNewsSummary neteastNewsSummary = list.get(position);
                if (neteastNewsSummary!=null){
                    GlideUtils.loadDefault(neteastNewsSummary.getImgsrc(),((ViewHolder) holder).ivNewsSummaryPhoto,null,null, DiskCacheStrategy.RESULT);
                    //Glide.with(context).load(neteastNewsSummary.getImgsrc()).asBitmap().fitCenter().error(R.drawable.ic_header).into(((ViewHolder) holder).ivNewsSummaryPhoto);
                    ((ViewHolder) holder).tvNewsSummaryTitle.setText(neteastNewsSummary.getTitle());
                    ((ViewHolder) holder).tvNewsSummaryDigest.setText(neteastNewsSummary.getDigest());
                    ((ViewHolder) holder).tvNewsSummaryPtime.setText(neteastNewsSummary.getPtime());
                    holder.itemView.setOnClickListener(new OnItemClick(position,neteastNewsSummary) {
                        @Override
                        protected void onItemClicked(int position, NeteastNewsSummary entity) {

                        }
                    });
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_news_summary_photo)
        ImageView ivNewsSummaryPhoto;
        @BindView(R.id.tv_news_summary_title)
        TextView tvNewsSummaryTitle;
        @BindView(R.id.tv_news_summary_digest)
        TextView tvNewsSummaryDigest;
        @BindView(R.id.tv_news_summary_ptime)
        TextView tvNewsSummaryPtime;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
