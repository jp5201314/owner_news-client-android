package cn.cnlinfo.news.ui.fragment.video.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.rx.entity.NeteastVideoSummary;
import cn.cnlinfo.news.ui.fragment.news.adapter.BaseRecyclerAdapter;

/**
 * Created by JP on 2018/3/8 0008.
 */

public class ShowVideoListAdapter extends BaseRecyclerAdapter<NeteastVideoSummary> {

    private Context context;

    public ShowVideoListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (list != null && getItemCount() > 0) {
                NeteastVideoSummary neteastVideoSummary = list.get(position);
                if (position%2==0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(((ViewHolder) holder).ijkPlayer.getLayoutParams());
                    lp.setMargins(0,24,0,0);
                    lp.height =  new Random().nextInt(100)+240;
                    Log.d("TAG",lp.height+"");
                    ((ViewHolder) holder).ijkPlayer.setLayoutParams(lp);
                } else {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(((ViewHolder) holder).ijkPlayer.getLayoutParams());
                    lp.setMargins(0,0,0,0);
                    lp.height =  new Random().nextInt(100)+240;
                    Log.d("TAG",lp.height+"");
                    ((ViewHolder) holder).ijkPlayer.setLayoutParams(lp);
                }
                Glide.with(context).load(neteastVideoSummary.getCover()).asBitmap().into(((ViewHolder) holder).ijkPlayer);
                ((ViewHolder) holder).title.setText(neteastVideoSummary.getTitle());
                holder.itemView.setOnClickListener(new OnItemClick(position,neteastVideoSummary) {
                    @Override
                    protected void onItemClicked(int position, NeteastVideoSummary entity) {

                    }
                });
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ijkPlayer)
        ImageView ijkPlayer;
        @BindView(R.id.title)
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
