package cn.cnlinfo.news.ui.activity.channel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.List;

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
    public NewsChannelAdapter(Context context,List<NewsChannel> newsChannelList) {
        this.context = context;
        this.newsChannelList = newsChannelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final RecyclerView.ViewHolder holder = new NewsChannelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news_channel, parent, false));

        if (mItemTouchHelperCallback != null) {
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 触摸事件发生的时候，如果是定死频道，直接不给拖拽
                    if (newsChannelList.get(holder.getLayoutPosition()).getNewsChannelFixed()) {
                        Logger.e("触摸事件发生的时候，如果是定死频道，直接不给拖拽");
                        mItemTouchHelperCallback.setLongPressDragEnabled(false);
                        return true;
                    } else {
                        mItemTouchHelperCallback.setLongPressDragEnabled(true);
                    }

                    return false;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsChannelViewHolder) {
            Button btnItemChannel = holder.itemView.findViewById(R.id.btn_item_channel);
            if (newsChannelList!=null&&newsChannelList.size()!=0){
                final NewsChannel newsChannel = newsChannelList.get(position);
                btnItemChannel.setText(newsChannel.getNewsChannelName());
                if (newsChannel.getNewsChannelSelect()){//被选择就是被订阅的频道
                    if (newsChannel.getNewsChannelFixed()){
                        holder.itemView.setSelected(newsChannel.getNewsChannelFixed());
                        holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_gray_a5a5a6));
                        holder.itemView.setClickable(false);
                    }else {
                        //没有被固定并且点击后就是删除现有的订阅频道
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RxBus.get().post("AddOrDeleteChannel",new AddOrDeleteChannelEvent(newsChannel.getNewsChannelName(),false,holder.getLayoutPosition()));
                            }
                        });
                    }
                }else {//没有被选择，就是没有被订阅频道，点击后就是增加订阅频道
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RxBus.get().post("AddOrDeleteChannel",new AddOrDeleteChannelEvent(newsChannel.getNewsChannelName(),true,holder.getLayoutPosition()));
                        }
                    });
                }
            }
        }
    }

    public void addItem(int position,NewsChannel newsChannel){
        //修改增加项的被选择订阅的状态
        if (newsChannel.getNewsChannelSelect()){
            newsChannel.setNewsChannelSelect(false);
        }else {
            newsChannel.setNewsChannelSelect(true);
        }
        newsChannelList.add(position,newsChannel);
        this.notifyItemInserted(position);
    }

    public void deleteItem(int pos,NewsChannel newsChannel){
        newsChannelList.remove(pos);
        newsChannelList.remove(newsChannel);
        this.notifyItemRemoved(pos);
    }

    public List<NewsChannel> getData(){
        return newsChannelList;
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
        // 前三固定死不能交换
        if (!newsChannelList.get(fromPosition).getNewsChannelFixed() && !newsChannelList.get(toPosition)
                .getNewsChannelFixed()) {
            //交换mItems数据的位置
            Collections.swap(newsChannelList, fromPosition, toPosition);
            //交换RecyclerView列表中item的位置
            notifyItemMoved(fromPosition, toPosition);

            if (mItemMoveListener != null) {
                mItemMoveListener.onItemMove(fromPosition, toPosition);
            }

            return true;
        }
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        //删除mItems数据
        newsChannelList.remove(position);
        //删除RecyclerView列表对应item
        notifyItemRemoved(position);
    }
    public void setItemTouchHelper(SimpleItemTouchHelperCallback callback1) {
        mItemTouchHelperCallback = callback1;
    }

    class NewsChannelViewHolder extends RecyclerView.ViewHolder   implements SimpleItemTouchHelperCallback.OnStateChangedListener {
        public NewsChannelViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onItemSelected() {
            // Enable设为false，改变背景颜色
            itemView.setEnabled(false);
            itemView.setBackgroundResource(R.drawable.shape_dialog);
            itemView.setBackgroundColor(context.getResources().getColor(R.color.color_gray_3e4143));
        }

        @Override
        public void onItemClear() {
            // Enable设为true，恢复背景颜色
            itemView.setEnabled(true);
            itemView.setBackgroundResource(R.drawable.shape_dialog);
        }
    }
}
