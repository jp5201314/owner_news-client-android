package cn.cnlinfo.news.event;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class AddOrDeleteChannelEvent {
    private String channelName;
    private boolean isSelected;

    public AddOrDeleteChannelEvent(String channelName, boolean isSelected) {
        this.channelName = channelName;
        this.isSelected = isSelected;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
