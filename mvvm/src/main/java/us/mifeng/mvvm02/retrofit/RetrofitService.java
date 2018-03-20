package us.mifeng.mvvm02.retrofit;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import us.mifeng.mvvm02.bean.MKTBean;

/**
 * Created by 黑夜之火 on 2018/3/20.
 */

public interface RetrofitService {
    //http://api.dameiketang.com/
    @POST("manager.php?m=Admin&c=Threevesion&a=IndexPageData")
    @FormUrlEncoded
    Observable<MKTBean> getMktData(@Field("id")String id);
}
