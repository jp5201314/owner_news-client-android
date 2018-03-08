package cn.cnlinfo.news.ui.fragment.image.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.rx.entity.SinaPhotoDetail;
import cn.cnlinfo.news.ui.fragment.news.adapter.BaseRecyclerAdapter;
import cn.cnlinfo.news.utils.GlideUtils;

/**
 * Created by JP on 2018/3/8 0008.
 */

public class ShowImageListAdapter extends BaseRecyclerAdapter<SinaPhotoDetail> {

    private Context context;

    public ShowImageListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (list != null && getItemCount() > 0) {
                SinaPhotoDetail sinaPhotoDetail = list.get(position);
                if (position%2==0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(((ViewHolder) holder).img.getLayoutParams());
                    lp.setMargins(0,24,0,0);
                    lp.height =  new Random().nextInt(100)+240;
                    Log.d("TAG",lp.height+"");
                    ((ViewHolder) holder).img.setLayoutParams(lp);
                } else {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(((ViewHolder) holder).img.getLayoutParams());
                    lp.setMargins(0,0,0,0);
                    lp.height =  new Random().nextInt(100)+240;
                    Log.d("TAG",lp.height+"");
                    ((ViewHolder) holder).img.setLayoutParams(lp);
                }
                GlideUtils.loadDefault(sinaPhotoDetail.getUrl(),((ViewHolder) holder).img,false,null, DiskCacheStrategy.RESULT);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
