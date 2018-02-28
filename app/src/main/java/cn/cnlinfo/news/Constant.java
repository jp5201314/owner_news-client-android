package cn.cnlinfo.news;

import cn.cnlinfo.news.manager.PhoneManager;

/**
 * Created by JP on 2017/10/11 0011.
 */

public class Constant {

    private static final String REQUEST_HOST = "http://ccf.hrkji.com/RegUser.asmx/";
    private static final String REQUEST_HOST_FOR_TEST = "http://ccf.hrkji.com/RegUser.asmx/";
    public  static final String DB_NAME = "NewsChannelTable";
    public static final String APP_NAME = "";
    //新闻
    private static final String NEWS_HOST = "";
    private static final String NEWS_HOST_FOR_TEST = "";
    //图片
    private static final String PIC_HOST = "";
    private static final String PIC_HOST_FOR_TEST = "";
    //视频
    private static final String VIDEO_HOST = "";
    private static final String VIDEO_HOST_FOR_TEST = "";
    public static final String APP_DIR = PhoneManager.getSdCardRootPath() + "/ccf/";//app文件目录
    public static final String IMAGE_CACHE_DIR_PATH = APP_DIR + "cache/";// 图片缓存地址
    public static final String UPLOAD_FILES_DIR_PATH = APP_DIR + "upload/";//上传文件、零时文件存放地址
    public static final String DOWNLOAD_DIR_PATH = APP_DIR + "downloads/";// 下载文件存放地址

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static String getHost(int type) {
        if (type==1){
            if (isDebug()) {
                return REQUEST_HOST_FOR_TEST;
            } else {
                return REQUEST_HOST;
            }
        }else if(type == 2){
            if (isDebug()) {
                return NEWS_HOST_FOR_TEST;
            } else {
                return NEWS_HOST;
            }
        }else if(type == 3){
            if (isDebug()) {
                return PIC_HOST_FOR_TEST;
            } else {
                return PIC_HOST;
            }
        }else if(type == 4){
            if (isDebug()) {
                return VIDEO_HOST_FOR_TEST;
            } else {
                return VIDEO_HOST;
            }
        }
        return null;
    }
}
