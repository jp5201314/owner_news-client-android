package cn.cnlinfo.ccf.rx.response.exception;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class ApiException extends Exception {
    private int  exceptionCode;
    private String errorMsg;

    public ApiException(int exceptionCode, String errorMsg) {
        this.exceptionCode = exceptionCode;
        this.errorMsg = errorMsg;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
