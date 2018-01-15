package cn.cnlinfo.ccf.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tendcloud.tenddata.TCAgent;

import cn.cnlinfo.ccf.R;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class GaugePanelFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TCAgent.onPageStart(getActivity(), "仪表盘");
        View view = inflater.inflate(R.layout.fragment_gauge_panel, container, false);

        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TCAgent.onPageEnd(getActivity(), "仪表盘");
    }

}
