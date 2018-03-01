package cn.cnlinfo.news.ui.activity.channel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.bean.NewsChannel;
import cn.cnlinfo.news.event.AddOrDeleteChannelEvent;
import cn.cnlinfo.news.rx.rxbus.RxBus;
import cn.cnlinfo.news.ui.callback.SimpleItemTouchHelperCallback;

/**
 * Created by JP on 2018/3/1 0001.
 */

public class NewsChannelAdapter extends RecyclerView.Adapter implements SimpleItemTouchHelperCallback.OnMoveAndSwipeListener{

    private Context context;
    private List<NewsChannel> newsChannelList;
    private SimpleItemTouchHelperCallback mItemTouchHelperCallback;
    private OnItemMoveListener mItemMoveListener;
    public NewsChannelAdapter(Context context, List<NewsChannel> newsChannels) {
        this.context = context;
        this.newsChannelList = newsChannels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsChannelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news_channel, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsChannelViewHolder) {
            if (newsChannelList!=null&&newsChannelList.size()!=0){
                final NewsChannel newsChannel = newsChannelList.get(position);
                ((NewsChannelViewHolder) holder).btnItemChannel.setText(newsChannel.getNewsChannelName());
                if (newsChannel.getNewsChannelSelect()){//被选择就是被订阅的频道
                    if (newsChannel.getNewsChannelFixed()){
                        ((NewsChannelViewHolder) holder).btnItemChannel.setSelected(newsChannel.getNewsChannelFixed());
                        ((NewsChannelViewHolder) holder).btnItemChannel.setBackgroundColor(context.getResources().getColor(R.color.color_gray_a5a5a6));
                        ((NewsChannelViewHolder) holder).btnItemChannel.setClickable(false);
                    }else {
                        //没有被固定并且点击后就是删除现有的订阅频道
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RxBus.get().post("AddOrDeleteChannel",new AddOrDeleteChannelEvent(newsChannel.getNewsChannelName(),false));
                            }
                        });
                    }
                }else {//没有被选择，就是没有被订阅频道，点击后就是增加订阅频道
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RxBus.get().post("AddOrDeleteChannel",new AddOrDeleteChannelEvent(newsChannel.getNewsChannelName(),true));
                        }
                    });
                }
            }
        }
    }
    /**
     * 设置item拖拽移动的监听
     * @param itemMoveListener item移动时的监听
     */
    public void setItemMoveListener(OnItemMoveListener itemMoveListener) {
        mItemMoveListener = itemMoveListener;
    }

    public interface OnItemMoveListener {
        void onItemMove(int fromPosition, int toPosition);
    }
    @Override
    public int getItemCount() {
        return newsChannelList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
    public void setItemTouchHelper(SimpleItemTouchHelperCallback callback1) {
        mItemTouchHelperCallback = callback1;
    }

    class NewsChannelViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_item_channel)
        Button btnItemChannel;
        public NewsChannelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
