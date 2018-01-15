package cn.cnlinfo.ccf.fragment;

import android.widget.Toast;

import cc.cloudist.acplibrary.ACProgressFlower;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.dialog.DialogCreater;
import cn.cnlinfo.ccf.inter.IComponentContainer;
import cn.cnlinfo.ccf.inter.IFragment;
import cn.cnlinfo.ccf.inter.ILifeCycleComponent;
import cn.cnlinfo.ccf.manager.LifeCycleComponentManager;
import cn.cnlinfo.ccf.view.RefreshHeaderView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class BaseFragment extends LazyFragment implements IFragment, IComponentContainer {
    private boolean mFirstResume = true;
    private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();
    protected ACProgressFlower waitingDialog;
    @Override
    public void addComponent(ILifeCycleComponent component) {
        this.mComponentContainer.addComponent(component);
    }

    @Override
    public void onEnter(Object data) {

    }


    @Override
    public void onLeave() {
        this.mComponentContainer.onBecomesTotallyInvisible();
    }

    @Override
    public void onBack() {
        this.mComponentContainer.onBecomesVisibleFromTotallyInvisible();
    }

    @Override
    public void onBackWithData(Object data) {
        this.mComponentContainer.onBecomesVisibleFromTotallyInvisible();
    }

    @Override
    public boolean processBackPressed() {
        return false;
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        this.onLeave();
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        if (!this.mFirstResume) {
            this.onBack();
        }

        if (this.mFirstResume) {
            this.mFirstResume = false;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mComponentContainer.onDestroy();
    }

    protected void toast(int rsId) {
        toast(getString(rsId));
    }


    protected void toast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }


    protected void showWaitingDialog(boolean show) {
        showWaitingDialog(show, getString(R.string.please_wait));
    }

    protected void showWaitingDialog(boolean show, String waitingNotice) {
        if (!show) {
            waitingDialog.dismiss();
            return;
        }
        waitingDialog = DialogCreater.createProgressDialog(getContext(), waitingNotice);
        waitingDialog.show();
    }

    protected void setMaterialHeader(PtrClassicFrameLayout ptr) {
        RefreshHeaderView ptrHeader = new RefreshHeaderView(getApplicationContext());
        ptrHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        ptrHeader.setPtrFrameLayout(ptr);

        ptr.setLoadingMinTime(800);
        ptr.setDurationToCloseHeader(800);
        ptr.setHeaderView(ptrHeader);
        ptr.addPtrUIHandler(ptrHeader);
    }
    /**
     * Override this function when you need control whether you will cancel OkHttpFinal after Destroy
     *
     * @return boolean
     */
    protected boolean cancelOkHttpFinalAfterDestory() {
        return true;
    }
}
