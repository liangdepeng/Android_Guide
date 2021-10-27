package okhttp;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/27
 * <p>
 * Summary:
 */
public interface HttpApi {

    @GET("urlurl")
    Call<Object> getTestApi();

    @GET("urlurl")
    Observable<Object> getRxTestApi();

    @GET("/test/{userid}/123213")
    Call<Object> getTest(@Path("userid") String userid);

    @Streaming // 下载大文件
    @GET("test/test/tesdsad")
    Call<Object> getTest2(@Query("userid") String userid, @Query("name") String name);

    @GET("test/test")
    Call<Object> getTest(@QueryMap Map<String, String> paramsMap);

    @POST("testurl")
    Call<Object> postTest(@Field("userid") String userid);

    @POST("TETSURL")
    Call<Object> postTest2(@FieldMap Map<String, String> paramMap);

    @GET // 动态设置 url
    Call<Object> getTest3(@Url String url,@QueryMap Map<String,String> paramMap);
}
