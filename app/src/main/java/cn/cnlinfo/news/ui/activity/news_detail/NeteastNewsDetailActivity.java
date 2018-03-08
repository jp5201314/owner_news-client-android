package cn.cnlinfo.news.ui.activity.news_detail;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.rx.entity.NeteastNewsDetail;
import cn.cnlinfo.news.ui.activity.BaseActivity;
import cn.cnlinfo.news.utils.GlideUtils;
import cn.cnlinfo.news.utils.RxToast;
import zhou.widget.RichText;

public class NeteastNewsDetailActivity extends BaseActivity implements NeteastNewsDetailContact.View,View.OnClickListener{

    @BindView(R.id.iv_news_detail_photo)
    ImageView ivNewsDetailPhoto;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_news_detail_title)
    TextView tvNewsDetailTitle;
    @BindView(R.id.tv_news_detail_from)
    TextView tvNewsDetailFrom;
    @BindView(R.id.tv_news_detail_body)
    RichText tvNewsDetailBody;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private String postId;
    private Unbinder unbinder;
    private NeteastNewsDetailPresenter presenter;
    private String imgSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neteast_news_detail);
        unbinder = ButterKnife.bind(this);
        postId = getIntent().getStringExtra("postId");
        imgSrc = getIntent().getStringExtra("imgSrc");
        fab.setOnClickListener(this);
        initToolBar(toolbar,true,"新闻详情");
        presenter = new NeteastNewsDetailPresenter(this,postId);
        presenter.toGainNewsDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void successReceiveNewsDatail(NeteastNewsDetail neteastNewsDetail) {
        if (neteastNewsDetail!=null){
            GlideUtils.loadDefault(imgSrc,ivNewsDetailPhoto,false,null, DiskCacheStrategy.RESULT);
            tvNewsDetailTitle.setText(neteastNewsDetail.getTitle());
            tvNewsDetailFrom.setText(getString(R.string.from,neteastNewsDetail.getSource(),neteastNewsDetail.getPtime()));
            tvNewsDetailBody.setRichText(neteastNewsDetail.getBody());
        }
    }

    @Override
    public void fialReceiveNewsDatail(String msg) {
        RxToast.info(msg);
    }

    @Override
    public void onClick(View v) {

    }
}
