package cn.cnlinfo.ccf.rx.response.entity;

/**
 * Created by JP on 2018/1/9 0009.
 */

public class HttpResult<T> {


    /**
     * MessageID : 0
     * Success : true
     * Content : 登录成功
     * EntityList : []
     * Data : {"userinfo":{"UserID":"4","UserCode":"1001","NickName":"1001","Mobile":"15982013084","InvitationCode":"","TodayStep":"2134"}}
     */

    private int MessageID;
    private boolean Success;
    private String Content;
    private T Data;

    public int getMessageID() {
        return MessageID;
    }

    public void setMessageID(int messageID) {
        MessageID = messageID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
