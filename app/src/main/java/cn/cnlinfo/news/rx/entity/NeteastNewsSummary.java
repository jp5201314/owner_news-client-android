package cn.cnlinfo.news.rx.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class NeteastNewsSummary {

    /**
     * template : normal1
     * skipID : 00AN0001|2291157
     * lmodify : 2018-03-06 09:51:52
     * postid : PHOT25TEL000100A
     * source : 东方IC
     * title : 全国两会礼仪齐穿“中国红”吸睛
     * mtime : 2018-03-06 09:51:52
     * hasImg : 1
     * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg
     * digest :
     * photosetID : 00AN0001|2291157
     * boardid : photoview_bbs
     * alias : Top News
     * hasAD : 1
     * imgsrc : http://cms-bucket.nosdn.127.net/897d93eeb6f9470b96fbd5cc10701e0a20180306094528.jpeg
     * ptime : 2018-03-06 09:45:58
     * daynum : 17596
     * hasHead : 1
     * imgType : 1
     * order : 1
     * editor : []
     * votecount : 0
     * hasCover : false
     * docid : 9IG74V5H00963VRO_DC745IL5bjzhaoyapingupdateDoc
     * tname : 头条
     * priority : 360
     * ads : [{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2291155","tag":"photoset","title":"以色列总理访美 梅拉尼娅贴面欢迎","imgsrc":"bigimg","url":"00AO0001|2291155"},{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2291154","tag":"photoset","title":"男子凿穿30厘米冰层 在冰水中沐浴","imgsrc":"bigimg","url":"00AO0001|2291154"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2291150","tag":"photoset","title":"杭州西湖船只整齐停放码头如羽毛","imgsrc":"bigimg","url":"00AP0001|2291150"},{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2291119","tag":"photoset","title":"十三届全国人大一次会议开幕","imgsrc":"http://cms-bucket.nosdn.127.net/873f00e77d5c45369adfb50828c68e6620180305094203.jpeg","url":"00AN0001|2291119"},{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2291116","tag":"photoset","title":"人大首场代表通道开启 代表接受采访","imgsrc":"http://cms-bucket.nosdn.127.net/fd819257e4a64df696b87548ac32be2920180305083900.jpeg","url":"00AN0001|2291116"}]
     * ename : androidnews
     * replyCount : 0
     * imgsum : 3
     * hasIcon : false
     * skipType : photoset
     * cid : C1348646712614
     */

    public String postid;
    public boolean hasCover;
    public int hasHead;
    public int replyCount;
    public int hasImg;
    public String digest;
    public boolean hasIcon;
    public String docid;
    public String title;
    public int order;
    public int priority;
    public String lmodify;
    public String boardid;
    public String photosetID;
    public String template;
    public int votecount;
    public String skipID;
    public String alias;
    public String skipType;
    public String cid;
    public int hasAD;
    public String imgsrc;
    public String tname;
    public String ename;
    public String ptime;
    /**
     * title : 哈萨克斯坦中亚在建第1高楼爆炸起火
     * tag : photoset
     * imgsrc : http://img5.cache.netease.com/3g/2016/2/13/2016021318005710210.jpg
     * subtitle :
     * url : 00AN0001|110630
     */

    public List<AdsEntity> ads;
    /**
     * imgsrc : http://img5.cache.netease.com/3g/2016/2/13/201602131446132dc50.jpg
     */

    public List<ImgextraEntity> imgextra;

    public static class AdsEntity {
        public String title;
        public String tag;
        public String imgsrc;
        public String subtitle;
        public String url;
    }

    public static class ImgextraEntity {
        public String imgsrc;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public boolean isHasCover() {
        return hasCover;
    }

    public void setHasCover(boolean hasCover) {
        this.hasCover = hasCover;
    }

    public int getHasHead() {
        return hasHead;
    }

    public void setHasHead(int hasHead) {
        this.hasHead = hasHead;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getHasImg() {
        return hasImg;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public String getPhotosetID() {
        return photosetID;
    }

    public void setPhotosetID(String photosetID) {
        this.photosetID = photosetID;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getVotecount() {
        return votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public String getSkipID() {
        return skipID;
    }

    public void setSkipID(String skipID) {
        this.skipID = skipID;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getHasAD() {
        return hasAD;
    }

    public void setHasAD(int hasAD) {
        this.hasAD = hasAD;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<AdsEntity> getAds() {
        return ads;
    }

    public void setAds(List<AdsEntity> ads) {
        this.ads = ads;
    }

    public List<ImgextraEntity> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<ImgextraEntity> imgextra) {
        this.imgextra = imgextra;
    }

    public NeteastNewsSummary() {
    }

    public NeteastNewsSummary(String postid, boolean hasCover, int hasHead, int replyCount, int hasImg, String digest, boolean hasIcon, String docid, String title, int order, int priority, String lmodify, String boardid, String photosetID, String template, int votecount, String skipID, String alias, String skipType, String cid, int hasAD, String imgsrc, String tname, String ename, String ptime, List<AdsEntity> ads, List<ImgextraEntity> imgextra) {
        this.postid = postid;
        this.hasCover = hasCover;
        this.hasHead = hasHead;
        this.replyCount = replyCount;
        this.hasImg = hasImg;
        this.digest = digest;
        this.hasIcon = hasIcon;
        this.docid = docid;
        this.title = title;
        this.order = order;
        this.priority = priority;
        this.lmodify = lmodify;
        this.boardid = boardid;
        this.photosetID = photosetID;
        this.template = template;
        this.votecount = votecount;
        this.skipID = skipID;
        this.alias = alias;
        this.skipType = skipType;
        this.cid = cid;
        this.hasAD = hasAD;
        this.imgsrc = imgsrc;
        this.tname = tname;
        this.ename = ename;
        this.ptime = ptime;
        this.ads = ads;
        this.imgextra = imgextra;
    }

    @Override
    public String toString() {
        return "NeteastNewsSummary{" +
                "postid='" + postid + '\'' +
                ", hasCover=" + hasCover +
                ", hasHead=" + hasHead +
                ", replyCount=" + replyCount +
                ", hasImg=" + hasImg +
                ", digest='" + digest + '\'' +
                ", hasIcon=" + hasIcon +
                ", docid='" + docid + '\'' +
                ", title='" + title + '\'' +
                ", order=" + order +
                ", priority=" + priority +
                ", lmodify='" + lmodify + '\'' +
                ", boardid='" + boardid + '\'' +
                ", photosetID='" + photosetID + '\'' +
                ", template='" + template + '\'' +
                ", votecount=" + votecount +
                ", skipID='" + skipID + '\'' +
                ", alias='" + alias + '\'' +
                ", skipType='" + skipType + '\'' +
                ", cid='" + cid + '\'' +
                ", hasAD=" + hasAD +
                ", imgsrc='" + imgsrc + '\'' +
                ", tname='" + tname + '\'' +
                ", ename='" + ename + '\'' +
                ", ptime='" + ptime + '\'' +
                ", ads=" + ads +
                ", imgextra=" + imgextra +
                '}';
    }
}
