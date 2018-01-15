package cn.cnlinfo.ccf.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tendcloud.tenddata.TCAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;

/**
 * Created by JP on 2017/10/11 0011.
 */

public class TradingCenterFragment extends BaseFragment {
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trading_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        TCAgent.onPageStart(getActivity(), "交易中心");
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        TCAgent.onPageEnd(getActivity(), "交易中心");
    }
}
