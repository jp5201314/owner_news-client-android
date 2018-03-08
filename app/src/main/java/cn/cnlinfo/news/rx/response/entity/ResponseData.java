package cn.cnlinfo.news.rx.response.entity;

import java.util.List;

import cn.cnlinfo.news.rx.entity.SinaPhotoDetail;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public class ResponseData {

    /**
     * error : false
     * results : [{"_id":"5a8e0c41421aa9133298a259","createdAt":"2018-02-22T08:18:09.547Z","desc":"2-22","publishedAt":"2018-02-22T08:24:35.209Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1foowtrkpvkj20sg0izdkx.jpg","used":true,"who":"代码家"},{"_id":"5a7b93d2421aa90d2cd3d7f8","createdAt":"2018-02-08T08:03:30.905Z","desc":"2-8","publishedAt":"2018-02-08T08:13:24.479Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180208080314_FhzuAJ_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a6e5f88421aa9115696004f","createdAt":"2018-01-29T07:40:56.269Z","desc":"1-29","publishedAt":"2018-01-29T07:53:57.676Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180129074038_O3ydq4_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a65381a421aa91156960022","createdAt":"2018-01-22T09:02:18.715Z","desc":"1-22","publishedAt":"2018-01-23T08:46:45.132Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180122090204_A4hNiG_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a5bfc29421aa9115489927b","createdAt":"2018-01-15T08:56:09.429Z","desc":"1-15","publishedAt":"2018-01-16T08:40:08.101Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180115085556_8AeReR_taeyeon_ss_15_1_2018_7_58_51_833.jpeg","used":true,"who":"daimajia"},{"_id":"5a5411fb421aa90fef2035f3","createdAt":"2018-01-09T08:51:07.91Z","desc":"1-9","publishedAt":"2018-01-10T07:57:19.486Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180109085038_4A7atU_rakukoo_9_1_2018_8_50_25_276.jpeg","used":true,"who":"daimajia"},{"_id":"5a443fcb421aa90fe72536ed","createdAt":"2017-12-28T08:50:19.747Z","desc":"12-28","publishedAt":"2018-01-04T13:45:57.211Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg","used":true,"who":"代码家"},{"_id":"5a4ad432421aa90fe2f02d1a","createdAt":"2018-01-02T08:37:06.235Z","desc":"1-2","publishedAt":"2018-01-02T08:43:32.216Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180102083655_3t4ytm_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a431acd421aa90fe50c02a8","createdAt":"2017-12-27T12:00:13.664Z","desc":"12-27","publishedAt":"2017-12-27T12:13:22.418Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171227115959_lmlLZ3_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a392689421aa90fe50c0293","createdAt":"2017-12-19T22:47:37.468Z","desc":"12-19","publishedAt":"2017-12-25T08:28:04.64Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171219224721_wFH5PL_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a388e4c421aa90fe2f02ced","createdAt":"2017-12-19T11:58:04.567Z","desc":"12-19","publishedAt":"2017-12-19T12:00:28.893Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171219115747_tH0TN5_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a2f2486421aa90fe2f02cd2","createdAt":"2017-12-12T08:36:22.670Z","desc":"12-12","publishedAt":"2017-12-15T08:59:11.361Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171212083612_WvLcTr_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a2dd04e421aa90fe2f02ccc","createdAt":"2017-12-11T08:24:46.981Z","desc":"12-11","publishedAt":"2017-12-11T08:43:39.682Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171211082435_CCblJd_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a273d40421aa90fef203585","createdAt":"2017-12-06T08:43:44.386Z","desc":"12-6","publishedAt":"2017-12-06T08:49:34.303Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171206084331_wylXWG_misafighting_6_12_2017_8_43_16_390.jpeg","used":true,"who":"daimajia"},{"_id":"5a20ace0421aa90fe50c024c","createdAt":"2017-12-01T09:14:08.63Z","desc":"12-1","publishedAt":"2017-12-05T08:48:31.384Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171201091356_OPqmuO_kanna399_1_12_2017_9_13_42_126.jpeg","used":true,"who":"daimajia"},{"_id":"5a1ad043421aa90fe725366c","createdAt":"2017-11-26T22:31:31.363Z","desc":"11-26","publishedAt":"2017-11-30T13:11:10.665Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171126223118_jeCYtY_chayexiaoguo_apple_26_11_2017_22_30_59_409.jpeg","used":true,"who":"代码家"},{"_id":"5a16171d421aa90fef203553","createdAt":"2017-11-23T08:32:29.546Z","desc":"11-23","publishedAt":"2017-11-24T11:08:03.624Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171123083218_5mhRLg_sakura.gun_23_11_2017_8_32_9_312.jpeg","used":true,"who":"daimajia"},{"_id":"5a121895421aa90fe50c021e","createdAt":"2017-11-20T07:49:41.797Z","desc":"2017-11-20","publishedAt":"2017-11-20T12:42:06.454Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171120074925_ZXDh6l_joanne_722_20_11_2017_7_49_16_336.jpeg","used":true,"who":"daimajia"},{"_id":"5a0e4a0d421aa90fe7253643","createdAt":"2017-11-17T10:31:41.155Z","desc":"11-17","publishedAt":"2017-11-17T12:39:48.189Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-11-17-22794158_128707347832045_9158114204975104000_n.jpg","used":true,"who":"代码家"},{"_id":"5a0d0c97421aa90fe2f02c60","createdAt":"2017-11-16T11:57:11.4Z","desc":"11-16","publishedAt":"2017-11-16T12:01:05.619Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171116115656_vnsrab_Screenshot.jpeg","used":true,"who":"代码家"}]
     */

    private boolean error;
    private List<SinaPhotoDetail> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<SinaPhotoDetail> getResults() {
        return results;
    }

    public void setResults(List<SinaPhotoDetail> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
