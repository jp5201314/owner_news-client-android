package cn.cnlinfo.news.fragment;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;

/**
 * Created by JP on 2017/10/11 0011.
 */

public class TradingCenterFragment extends BaseFragment {
    private Unbinder unbinder;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_trading_center);
        unbinder = ButterKnife.bind(this, getContentView());
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }

}
