package cn.cnlinfo.ccf.rx.response.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class User implements Serializable{


    /**
     * UserID : 4
     * UserCode : 1001
     * NickName : 1001
     * Mobile : 15982013084
     * InvitationCode :
     * TodayStep : 2134
     */

    private String UserID;
    private String UserCode;
    private String NickName;
    private String Mobile;
    private String InvitationCode;
    private String TodayStep;

    @Override
    public String toString() {
        return "User{" +
                "UserID='" + UserID + '\'' +
                ", UserCode='" + UserCode + '\'' +
                ", NickName='" + NickName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", InvitationCode='" + InvitationCode + '\'' +
                ", TodayStep='" + TodayStep + '\'' +
                '}';
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getInvitationCode() {
        return InvitationCode;
    }

    public void setInvitationCode(String InvitationCode) {
        this.InvitationCode = InvitationCode;
    }

    public String getTodayStep() {
        return TodayStep;
    }

    public void setTodayStep(String TodayStep) {
        this.TodayStep = TodayStep;
    }
}
