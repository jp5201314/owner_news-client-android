package cn.cnlinfo.news.rx.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JP on 2018/3/5 0005.
 * 视屏实体类
 */

public class NeteastVideoSummary implements Parcelable{


    /**
     * sizeHD : 0
     * videoTopic : {"ename":"T1490587584933","tname":"究竟视频","alias":"第一财经出品财经短视频","topic_icons":"http://dingyue.nosdn.127.net/PdpnRxPKMdp14bZQR4KPfqdmyd7YuHKI8Xs=ljD1zB1wG1490587584737.jpg","tid":"T1490587584933"}
     * mp4Hd_url : null
     * description :
     * title : 美法官裁定加密货币是商品 多家机构参与监管丨视点
     * mp4_url : http://flv3.bn.netease.com/videolib3/1803/08/wvRho2801/SD/wvRho2801-mobile.mp4
     * vid : VYBFB7NRH
     * cover : http://vimg1.ws.126.net/image/snapshot/2018/3/R/I/VYBFB7NRI.jpg
     * sizeSHD : 0
     * playersize : 1
     * ptime : 2018-03-08 17:54:23
     * m3u8_url : http://flv.bn.netease.com/videolib3/1803/08/wvRho2801/SD/movie_index.m3u8
     * topicImg : http://vimg2.ws.126.net/image/snapshot/2017/3/D/I/VCFK9TCDI.jpg
     * votecount : 0
     * length : 153
     * videosource : 新媒体
     * m3u8Hd_url : null
     * sizeSD : 6655
     * topicSid : VCFK9TCD7
     * playCount : 0
     * replyCount : 0
     * replyBoard : video_bbs
     * replyid : YBFB7NRH050835RB
     * topicName : 究竟视频
     * sectiontitle :
     * topicDesc : 究竟视频是第一财经所打造的一个纯网络财经视频的视频品牌。它的短视频完全基于互联网和移动互联网而生，在选题、语态和调性上，相比传统电视有着天然的互联网基因；但精良的制作、独家的内容源和专业公信力又使它的产品区别于良莠不齐的互联网视频平台。
     */

    private int sizeHD;
    private VideoTopicBean videoTopic;
    private Object mp4Hd_url;
    private String description;
    private String title;
    private String mp4_url;
    private String vid;
    private String cover;
    private int sizeSHD;
    private int playersize;
    private String ptime;
    private String m3u8_url;
    private String topicImg;
    private int votecount;
    private int length;
    private String videosource;
    private Object m3u8Hd_url;
    private int sizeSD;
    private String topicSid;
    private int playCount;
    private int replyCount;
    private String replyBoard;
    private String replyid;
    private String topicName;
    private String sectiontitle;
    private String topicDesc;

    protected NeteastVideoSummary(Parcel in) {
        sizeHD = in.readInt();
        description = in.readString();
        title = in.readString();
        mp4_url = in.readString();
        vid = in.readString();
        cover = in.readString();
        sizeSHD = in.readInt();
        playersize = in.readInt();
        ptime = in.readString();
        m3u8_url = in.readString();
        topicImg = in.readString();
        votecount = in.readInt();
        length = in.readInt();
        videosource = in.readString();
        sizeSD = in.readInt();
        topicSid = in.readString();
        playCount = in.readInt();
        replyCount = in.readInt();
        replyBoard = in.readString();
        replyid = in.readString();
        topicName = in.readString();
        sectiontitle = in.readString();
        topicDesc = in.readString();
    }

    public static final Creator<NeteastVideoSummary> CREATOR = new Creator<NeteastVideoSummary>() {
        @Override
        public NeteastVideoSummary createFromParcel(Parcel in) {
            return new NeteastVideoSummary(in);
        }

        @Override
        public NeteastVideoSummary[] newArray(int size) {
            return new NeteastVideoSummary[size];
        }
    };


    public int getSizeHD() {
        return sizeHD;
    }

    public void setSizeHD(int sizeHD) {
        this.sizeHD = sizeHD;
    }

    public VideoTopicBean getVideoTopic() {
        return videoTopic;
    }

    public void setVideoTopic(VideoTopicBean videoTopic) {
        this.videoTopic = videoTopic;
    }

    public Object getMp4Hd_url() {
        return mp4Hd_url;
    }

    public void setMp4Hd_url(Object mp4Hd_url) {
        this.mp4Hd_url = mp4Hd_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMp4_url() {
        return mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getSizeSHD() {
        return sizeSHD;
    }

    public void setSizeSHD(int sizeSHD) {
        this.sizeSHD = sizeSHD;
    }

    public int getPlayersize() {
        return playersize;
    }

    public void setPlayersize(int playersize) {
        this.playersize = playersize;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getM3u8_url() {
        return m3u8_url;
    }

    public void setM3u8_url(String m3u8_url) {
        this.m3u8_url = m3u8_url;
    }

    public String getTopicImg() {
        return topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public int getVotecount() {
        return votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getVideosource() {
        return videosource;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource;
    }

    public Object getM3u8Hd_url() {
        return m3u8Hd_url;
    }

    public void setM3u8Hd_url(Object m3u8Hd_url) {
        this.m3u8Hd_url = m3u8Hd_url;
    }

    public int getSizeSD() {
        return sizeSD;
    }

    public void setSizeSD(int sizeSD) {
        this.sizeSD = sizeSD;
    }

    public String getTopicSid() {
        return topicSid;
    }

    public void setTopicSid(String topicSid) {
        this.topicSid = topicSid;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getReplyBoard() {
        return replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getSectiontitle() {
        return sectiontitle;
    }

    public void setSectiontitle(String sectiontitle) {
        this.sectiontitle = sectiontitle;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sizeHD);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeString(mp4_url);
        dest.writeString(vid);
        dest.writeString(cover);
        dest.writeInt(sizeSHD);
        dest.writeInt(playersize);
        dest.writeString(ptime);
        dest.writeString(m3u8_url);
        dest.writeString(topicImg);
        dest.writeInt(votecount);
        dest.writeInt(length);
        dest.writeString(videosource);
        dest.writeInt(sizeSD);
        dest.writeString(topicSid);
        dest.writeInt(playCount);
        dest.writeInt(replyCount);
        dest.writeString(replyBoard);
        dest.writeString(replyid);
        dest.writeString(topicName);
        dest.writeString(sectiontitle);
        dest.writeString(topicDesc);
    }

    public static class VideoTopicBean {
        /**
         * ename : T1490587584933
         * tname : 究竟视频
         * alias : 第一财经出品财经短视频
         * topic_icons : http://dingyue.nosdn.127.net/PdpnRxPKMdp14bZQR4KPfqdmyd7YuHKI8Xs=ljD1zB1wG1490587584737.jpg
         * tid : T1490587584933
         */

        private String ename;
        private String tname;
        private String alias;
        private String topic_icons;
        private String tid;

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTopic_icons() {
            return topic_icons;
        }

        public void setTopic_icons(String topic_icons) {
            this.topic_icons = topic_icons;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
}
