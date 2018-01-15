package cn.cnlinfo.ccf.library.abslistview.base;


import cn.cnlinfo.ccf.library.abslistview.ViewHolder;

/**
 * Created by iTant on 2017/1/15.
 */
public interface ItemViewDelegate<T>
{

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ViewHolder holder, T t, int position);



}
