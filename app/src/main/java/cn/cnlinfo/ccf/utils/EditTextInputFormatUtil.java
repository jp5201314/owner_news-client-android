package cn.cnlinfo.ccf.utils;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class EditTextInputFormatUtil {
    /**
       移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
       联通：130、131、132、152、155、156、185、186
       电信：133、153、180、189、（1349卫通）
       总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
   */
    public static boolean verifyPhoneNumFormat(String phone){
        String patter = "[1][358]\\d{9}";
        return phone.matches(patter);
    }
}
