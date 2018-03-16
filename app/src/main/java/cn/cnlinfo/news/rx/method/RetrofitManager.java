package cn.cnlinfo.news.rx.method;

import android.util.SparseArray;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobUser;
import cn.cnlinfo.news.API;
import cn.cnlinfo.news.Constant;
import cn.cnlinfo.news.OwnerNewsApplication;
import cn.cnlinfo.news.rx.BaseObservableTransfer;
import cn.cnlinfo.news.rx.entity.NeteastNewsDetail;
import cn.cnlinfo.news.rx.entity.NeteastNewsSummary;
import cn.cnlinfo.news.rx.entity.NeteastVideoSummary;
import cn.cnlinfo.news.rx.entity.SinaPhotoDetail;
import cn.cnlinfo.news.rx.net_inter.HttpService;
import cn.cnlinfo.news.rx.response.entity.ResponseData;
import cn.cnlinfo.news.ui.callback.HandleRequestCallBack;
import cn.cnlinfo.news.utils.NetUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * /**
 * ClassName: RetrofitManager<p>
 * Author: JP<p>
 * Fuction: Retrofit请求管理类<p>
 * CreateDate:2016/2/13 20:34<p>
 * UpdateUser:<p>
 * UpdateDate:<p>
 */

public class RetrofitManager {
    // 设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    // 30秒内直接读缓存
    private static final long CACHE_AGE_SEC = 0;
    private static HttpService httpService;
    private static RetrofitManager retrofitManager = null;
    //存储主机地址生成不同的RetrofitManager实例
    private static SparseArray<RetrofitManager> retrofitManagerSparseArray = new SparseArray<>();

    //指定缓存路径,缓存大小100Mb
    private static final Cache cache = new Cache(new File(Constant.IMAGE_CACHE_DIR_PATH, "HttpCache"), 1024 * 1024 * 100);
    private static volatile OkHttpClient okHttpClient = null;

    /**
     * 1是新闻
     * 2是图片
     * 是视频
     *
     * @param type
     */
    private RetrofitManager(int type) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.getHost(type)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(getOkHttpClientInstance()).build();
        httpService = retrofit.create(HttpService.class);
    }

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            // 在这里统一配置请求头缓存策略以及响应头缓存策略
            if (NetUtil.isConnected(OwnerNewsApplication.getContext())) {
                // 在有网的情况下CACHE_AGE_SEC秒内读缓存，大于CACHE_AGE_SEC秒后会重新请求数据
                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
                Response response = chain.proceed(request);
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
            } else {
                // 无网情况下CACHE_STALE_SEC秒内读取缓存，大于CACHE_STALE_SEC秒缓存无效报504
                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
                Response response = chain.proceed(request);
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
            }

        }
    };

    // 打印返回的json数据拦截器
    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            request = requestBuilder.build();

            final Response response = chain.proceed(request);

            Logger.e("请求网址: \n" + request.url() + " \n " + "请求头部信息：\n" + request.headers() + "响应头部信息：\n" + response.headers());

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    Logger.e("");
                    Logger.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
                Logger.v("--------------------------------------------开始打印返回数据----------------------------------------------------");
                Logger.json(buffer.clone().readString(charset));
                Logger.v("--------------------------------------------结束打印返回数据----------------------------------------------------");
            }

            return response;
        }
    };


    public static RetrofitManager getInstance(int type) {
        synchronized (RetrofitManager.class) {
            retrofitManager = retrofitManagerSparseArray.get(type);
            if (retrofitManager == null) {
                retrofitManager = new RetrofitManager(type);
                retrofitManagerSparseArray.put(type, retrofitManager);
            } else {
                return retrofitManager;
            }
            return retrofitManagerSparseArray.get(type);
        }
    }

    //获取OkHttpClient实例对象
    public OkHttpClient getOkHttpClientInstance() {
        synchronized (RetrofitManager.class) {
            if (okHttpClient == null) {
                //加入cache缓存和增加拦截器，打印输出拦截的包的数据
                okHttpClient = new OkHttpClient().newBuilder().cache(cache).addInterceptor(mLoggingInterceptor).addNetworkInterceptor(mRewriteCacheControlInterceptor).connectTimeout(5, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
            }
        }
        return okHttpClient;
    }


    /**
     * 登录操作
     *
     * @param subscriber 观察者，也叫订阅者
     * @param userName   用户名
     * @param passWord   密码
     */
/*    public Subscription startLogin(Subscriber<User> subscriber, String userName, String passWord) {
        return httpService.login(userName, passWord).map(new ResponseChecker<User>()).compose(new BaseObservableTransfer<User>()).subscribe(subscriber);
    }*/
    public Subscription startLogin(Subscriber<BmobUser> subscriber, String userName, String passWord) {
        /**
         * http://doc.bmob.cn/data/android/develop_doc/#_76
         */
        return BmobUser.loginByAccountObservable(BmobUser.class,userName,passWord).compose(new BaseObservableTransfer<BmobUser>()).subscribe(subscriber);
    }

    /**
     * 获取新闻列表操作
     * @param subscriber  获取新闻列表的数据回调
     * @param channelId   订阅的频道的ID
     * @param channelType  订阅的频道类型
     * @param startPage  开始的页面号  每次加载10条
     * @return
     */
    public Subscription toLoadNeteastNewsSummaryListData(HandleRequestCallBack<List<NeteastNewsSummary>> subscriber, final String channelId, final String channelType, int startPage){
        //Logger.e("新闻列表：" + channelType + ";" + channelId+":"+startPage);

        return  httpService.getNewsList(channelType,channelId,startPage).compose(new BaseObservableTransfer<Map<String,List<NeteastNewsSummary>>>()).flatMap(new Func1<Map<String, List<NeteastNewsSummary>>, Observable<?>>() {
            @Override
            public Observable<?> call(Map<String, List<NeteastNewsSummary>> stringListMap) {
                if (channelId.equals(API.HOUSE_ID)){
                    return Observable.just(stringListMap.get("北京"));
                }
               /* for (NeteastNewsSummary neteastNewsSummary : stringListMap.get(channelId)) {
                    Logger.e(neteastNewsSummary.toString());
                }*/
                //just是将List直接返回   from是将List中的每一项直接返回
                return Observable.just(stringListMap.get(channelId));
            }
        }).subscribe((HandleRequestCallBack)subscriber);
    }

    /**
     * 获取新闻详细信息
     * @param requestCallBack  获取新闻详细信息的数据回调
     * @param postId  新闻列表项的详细信息id
     * @return
     */
    public Subscription toGainNeteastNewsDatailData(HandleRequestCallBack<NeteastNewsDetail> requestCallBack,final String postId){
        return httpService.getNewsDetail(postId).compose(new BaseObservableTransfer<Map<String,NeteastNewsDetail>>()).flatMap(new Func1<Map<String, NeteastNewsDetail>, Observable<?>>() {
            @Override
            public Observable<?> call(Map<String, NeteastNewsDetail> stringNeteastNewsDetailMap) {
                NeteastNewsDetail neteastNewsDetail = stringNeteastNewsDetailMap.get(postId);
                return Observable.just(neteastNewsDetail);
            }
        }).subscribe((HandleRequestCallBack)requestCallBack);
    }

    /**
     * 获取图片数据信息
     * @param requestCallBack  获取图片详细信息的数据回调
     * @param startPage  图片的起始页面0 1 2 3
     * @return
     */
    public Subscription toLoadImageListData(HandleRequestCallBack<List<SinaPhotoDetail>> requestCallBack,final  int startPage){
        return httpService.getSinaPhotoDetail(startPage).compose(new BaseObservableTransfer<ResponseData>()).flatMap(new Func1<ResponseData, Observable<?>>() {
            @Override
            public Observable<?> call(ResponseData responseData) {
                return Observable.just(responseData.getResults());
            }
        }).subscribe((HandleRequestCallBack)requestCallBack);
    }

    /**
     * 获取视屏的列表
     * @param requestCallBack 获取视频详细信息的数据回调
     * @param videoId  视屏的id值
     * @param startPage  视屏列表加载的起始页面
     * @return
     */
    public Subscription toLoadVideoListData(HandleRequestCallBack<List<NeteastVideoSummary>> requestCallBack, final String videoId, final int startPage){
        return httpService.getVideoList(videoId,startPage).compose(new BaseObservableTransfer<Map<String,List<NeteastVideoSummary>>>()).flatMap(new Func1<Map<String, List<NeteastVideoSummary>>, Observable<?>>() {
            @Override
            public Observable<?> call(Map<String, List<NeteastVideoSummary>> stringListMap) {
                return Observable.just(stringListMap.get(videoId));
            }
        }).subscribe((HandleRequestCallBack)requestCallBack);
    }
}
