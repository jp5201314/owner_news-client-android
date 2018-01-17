package cn.cnlinfo.ccf.fragment;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class CCMallFragment extends BaseFragment {
    private Unbinder unbinder;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_cc_mall);
        unbinder = ButterKnife.bind(this,getContentView());
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }

}
