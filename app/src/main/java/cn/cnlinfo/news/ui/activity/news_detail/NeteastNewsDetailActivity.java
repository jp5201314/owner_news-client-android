package cn.cnlinfo.news.ui.activity.news_detail;

import android.os.Bundle;

import cn.cnlinfo.news.R;
import cn.cnlinfo.news.ui.activity.BaseActivity;

public class NeteastNewsDetailActivity extends BaseActivity {

    private String postId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neteast_news_detail);
        postId = getIntent().getStringExtra("postId");

    }
}
