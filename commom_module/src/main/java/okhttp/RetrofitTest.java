package okhttp;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/27
 * <p>
 * Summary:
 */
public class RetrofitTest {

    private final ThreadPoolExecutor poolExecutor;

    public RetrofitTest() {
        poolExecutor = ((ThreadPoolExecutor) Executors.newCachedThreadPool());
    }

    public void request() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        HttpApi httpApi = retrofit.create(HttpApi.class);

        Call<Object> objectCall = httpApi.getTestApi();

        // 异步请求
        objectCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.getMessage();
            }
        });

        // 同步请求
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Response<Object> response = null;
                try {
                    response = objectCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.body();
            }
        });


        // 结合Rxjava 使用
        Observable<Object> rxTestApi = httpApi.getRxTestApi();

        rxTestApi.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });

    }
}
