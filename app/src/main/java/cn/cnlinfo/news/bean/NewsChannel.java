package cn.cnlinfo.news.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2018/2/28 0028.
 */
@Keep
@Entity
public class NewsChannel {
    /** Not-null value. */
    @NotNull
    private String newsChannelName;
    /** Not-null value. */
    @NotNull
    private String newsChannelId;
    /** Not-null value. */
    @NotNull
    private String newsChannelType;
    private boolean newsChannelSelect;
    @Id
    @Property(nameInDb = "NEWS_CHANNEL_INDEX")
    @Unique
    private long newsChannelIndex;
    private boolean newsChannelFixed;

    public NewsChannel() {
    }

    public NewsChannel(String newsChannelName, String newsChannelId, String newsChannelType, boolean newsChannelSelect, long newsChannelIndex, Boolean newsChannelFixed) {
        this.newsChannelName = newsChannelName;
        this.newsChannelId = newsChannelId;
        this.newsChannelType = newsChannelType;
        this.newsChannelSelect = newsChannelSelect;
        this.newsChannelIndex = newsChannelIndex;
        this.newsChannelFixed = newsChannelFixed;
    }

    public String getNewsChannelName() {
        return newsChannelName;
    }

    public void setNewsChannelName(String newsChannelName) {
        this.newsChannelName = newsChannelName;
    }

    public String getNewsChannelId() {
        return newsChannelId;
    }

    public void setNewsChannelId(String newsChannelId) {
        this.newsChannelId = newsChannelId;
    }

    public String getNewsChannelType() {
        return newsChannelType;
    }

    public void setNewsChannelType(String newsChannelType) {
        this.newsChannelType = newsChannelType;
    }

    public boolean getNewsChannelSelect() {
        return newsChannelSelect;
    }

    public void setNewsChannelSelect(boolean newsChannelSelect) {
        this.newsChannelSelect = newsChannelSelect;
    }

    public long getNewsChannelIndex() {
        return newsChannelIndex;
    }

    public void setNewsChannelIndex(long newsChannelIndex) {
        this.newsChannelIndex = newsChannelIndex;
    }

    public boolean getNewsChannelFixed() {
        return newsChannelFixed;
    }

    public void setNewsChannelFixed(boolean newsChannelFixed) {
        this.newsChannelFixed = newsChannelFixed;
    }

    @Override
    public String toString() {
        return "NewsChannel{" +
                "newsChannelName='" + newsChannelName + '\'' +
                ", newsChannelId='" + newsChannelId + '\'' +
                ", newsChannelType='" + newsChannelType + '\'' +
                ", newsChannelSelect=" + newsChannelSelect +
                ", newsChannelIndex=" + newsChannelIndex +
                ", newsChannelFixed=" + newsChannelFixed +
                '}';
    }
}
