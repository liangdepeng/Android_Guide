package okhttp

import android.util.Log
import com.example.common_module.userTag
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ldp.
 *
 * Date: 2021/11/16
 *
 * Summary:
 */
class RetrofitKtTest {

    fun ktTest(){

//        runBlocking {
//
//        }

        // kotlin + 协程

        GlobalScope.launch {
            val retrofit = Retrofit.Builder()
                .baseUrl("baseurl")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
            val httpApi = retrofit.create(HttpApiKt::class.java)
            val response = httpApi.getTest(hashMapOf())

            // 处理

            Log.e(userTag,response.toString())

        }




    }
}