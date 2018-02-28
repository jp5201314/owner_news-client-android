package cn.cnlinfo.news.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jaredrummler.android.widget.AnimatedSvgView;

import cn.cnlinfo.news.R;
import cn.cnlinfo.news.ui.activity.main.MainPageActivity;
import cn.cnlinfo.news.utils.SVG;

/**
 * Created by JP on 2018/2/26 0026.
 */

public class SVGActivity extends BaseActivity {
    private AnimatedSvgView mSvgView;
    private Handler checkhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            mAppName.setVisibility(View.VISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        mSvgView = findViewById(R.id.animated_svg_view);
        setSvg(SVG.PIKACHU);
        checkUpdate();
    }

    private void setSvg(SVG modelSvg) {
        mSvgView.setGlyphStrings(modelSvg.glyphs);
        mSvgView.setFillColors(modelSvg.colors);
        mSvgView.setViewportSize(modelSvg.width, modelSvg.height);
        mSvgView.setTraceResidueColor(0x32000000);
        mSvgView.setTraceColors(modelSvg.colors);
        mSvgView.rebuildGlyphData();
        mSvgView.start();
    }
    /**
     * 检查是否有新版本，如果有就升级
     */
    private void checkUpdate() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(500);
                    Message msg = checkhandler.obtainMessage();
                    checkhandler.sendMessage(msg);
                    Thread.sleep(1000);
                    toMain();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //跳转到主页
    private void toMain(){
        startActivity(new Intent(this,MainPageActivity.class));
        finish();
    }
}
