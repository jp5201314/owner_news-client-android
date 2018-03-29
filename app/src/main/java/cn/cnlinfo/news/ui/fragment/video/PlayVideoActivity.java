package cn.cnlinfo.news.ui.fragment.video;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.news.R;
import cn.cnlinfo.news.listener.VideoPlayerListener;
import cn.cnlinfo.news.ui.activity.BaseActivity;
import cn.cnlinfo.news.view.VideoPlayerIJK;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by Administrator on 2018/3/20.
 */

public class PlayVideoActivity extends BaseActivity {

    @BindView(R.id.ijk_player)
    VideoPlayerIJK ijkPlayer;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra("videoName"));
        //加载so文件
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
            loadVideo(getIntent().getStringExtra("videoUrl"));
        } catch (Exception e) {
            this.finish();
        }
        ijkPlayer.setListener(new VideoPlayerListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            }

            @Override
            public void onCompletion(IMediaPlayer mp) {
                mp.seekTo(0);
                mp.start();
            }

            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                return false;
            }

            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                return false;
            }

            @Override
            public void onPrepared(IMediaPlayer mp) {
                mp.start();
            }

            @Override
            public void onSeekComplete(IMediaPlayer mp) {
                   mp.stop();
            }

            @Override
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
                Logger.d(width+":"+height);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height, Gravity.CENTER);
                //获取到视频的宽和高
                ijkPlayer.setLayoutParams(params);
            }
        });
    }

    public void loadVideo(String path) {
        ijkPlayer.setVideoPath(path);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (ijkPlayer!=null)
            ijkPlayer.reset();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ijkPlayer!=null){
            ijkPlayer.release();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ijkPlayer!=null){
            ijkPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        IjkMediaPlayer.native_profileEnd();
        if (ijkPlayer!=null){
            ijkPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
