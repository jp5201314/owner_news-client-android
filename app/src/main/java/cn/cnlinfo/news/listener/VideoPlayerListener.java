package cn.cnlinfo.news.listener;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by Administrator on 2018/3/19.
 */

public abstract class VideoPlayerListener implements IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnInfoListener, IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnSeekCompleteListener {
}
