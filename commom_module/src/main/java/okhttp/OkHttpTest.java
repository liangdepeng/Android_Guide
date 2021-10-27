package okhttp;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/18
 * <p>
 * Summary:
 */
public class OkHttpTest {

    private final ExecutorService executorService;

    public OkHttpTest() {
        executorService = Executors.newCachedThreadPool();
    }

    // 同步请求
    public void syncGet() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //                .addInterceptor()
                //                .addNetworkInterceptor()
                //                .connectTimeout()
                .build();
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()//.post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 异步请求
    public void asyncGet() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()//.post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }
}
