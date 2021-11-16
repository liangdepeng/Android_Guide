package okhttp

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import rx.Observable

/**
 * Created by ldp.
 *
 *
 * Date: 2021/10/27
 *
 *
 * Summary:
 */
interface HttpApiKt {
//    @get:GET("urlurl")
//    val testApi: Call<Any?>?
//
//    @get:GET("urlurl")
//    val rxTestApi: Observable<Any?>?
//
//    @GET("/test/{userid}/123213")
//    fun getTest(@Path("userid") userid: String?): Call<Any?>?
//
//    @GET("test/test/tesdsad")
//    fun getTest2(@Query("userid") userid: String?, @Query("name") name: String?): Call<Any?>?
//
//    @Streaming // 下载文件 大文件要加 否则OOM
//    @GET
//    operator fun get(@Url url: String?): Call<ResponseBody?>?

    @GET("test/test")
    suspend fun getTest(@QueryMap paramsMap: HashMap<String, String>): Any

    @POST("testurl")
    suspend fun postTest(@Field("userid") userid: String): Any

    @POST("TETSURL")
    suspend fun postTest2(@FieldMap paramMap: Map<String, String>): Any

    @GET  // 动态设置 url
    suspend fun getTest3(@Url url: String?, @QueryMap paramMap: Map<String, String>): Any
}