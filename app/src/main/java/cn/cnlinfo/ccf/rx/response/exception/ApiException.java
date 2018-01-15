package cn.cnlinfo.ccf.rx.response.exception;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class ApiException extends Exception {
    private int  exceptionCode;
    public  ApiException(int exceptionCode){
        this.exceptionCode = exceptionCode;
    }

}
