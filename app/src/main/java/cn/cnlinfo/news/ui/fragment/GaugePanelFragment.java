package cn.cnlinfo.news.ui.fragment;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;


/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class GaugePanelFragment extends BaseFragment {

    private Unbinder unbinder;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_gauge_panel);
        unbinder = ButterKnife.bind(this,getContentView());
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }
}
