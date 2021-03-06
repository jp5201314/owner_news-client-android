package cn.cnlinfo.ccf.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.cnlinfo.ccf.R;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class ToastTool {
    private static long mShortMillis;
    private static String mLastShortText;

    private static long mLongMillis;
    private static String mLastLongText;

    /**
     * 短时间Toast
     * @param context
     * @param text
     */
    public static void showShort(Context context, String text) {

        if (TextUtils.equals(text, mLastShortText) && System.currentTimeMillis()-mShortMillis < 3000) {
            // 防止频繁Toast
            return;
        }

        if (context == null) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.widget_toast, null);
        TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
        tv_toast.setText(text);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();

        mShortMillis = System.currentTimeMillis();
        mLastShortText = text;
    }

    /**
     * 短时间Toast红色
     * @param context
     * @param text
     */
    public static void showShortRed(Context context, String text) {
        if (TextUtils.equals(text, mLastShortText) && System.currentTimeMillis()-mShortMillis < 3000) {
            // 防止频繁Toast
            return;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.widget_toast_red, null);
        TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
        tv_toast.setText(text);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();

        mShortMillis = System.currentTimeMillis();
        mLastShortText = text;
    }

    /**
     * 长时间Toast
     * @param context
     * @param text
     */
    public static void showLong(Context context, String text) {
        if (TextUtils.equals(text, mLastLongText) && System.currentTimeMillis()-mLongMillis < 5000) {
            // 防止频繁Toast
            return;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.widget_toast, null);
        TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
        tv_toast.setText(text);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();

        mLongMillis = System.currentTimeMillis();
        mLastLongText = text;
    }

    /**
     * 长时间Toast
     * @param context
     * @param text
     */
    public static void showLongSelfDefine(Context context, String text,int horizontal,int vertical) {
        if (TextUtils.equals(text, mLastLongText) && System.currentTimeMillis()-mLongMillis < 5000) {
            // 防止频繁Toast
            return;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.widget_toast, null);
        TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
        tv_toast.setText(text);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,horizontal,vertical);
        toast.show();

        mLongMillis = System.currentTimeMillis();
        mLastLongText = text;
    }
    /**
     * 长时间Toast
     * @param context
     * @param text
     */
    public static void showShortSelfDefine(Context context, String text,int horizontal,int vertical) {
        if (TextUtils.equals(text, mLastLongText) && System.currentTimeMillis()-mLongMillis < 5000) {
            // 防止频繁Toast
            return;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.widget_toast, null);
        TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
        tv_toast.setText(text);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,horizontal,vertical);
        toast.show();

        mLongMillis = System.currentTimeMillis();
        mLastLongText = text;
    }
}