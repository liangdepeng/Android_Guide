package com.example.myapplication.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.base.BaseRecyclerAdapter;
import com.example.myapplication.base.BaseViewHolder;
import com.example.myapplication.bean.WordBean;
import com.example.myapplication.databinding.ActivityDataBaseBinding;
import com.example.myapplication.help.SpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * sqlite 数据库 演示
 */
public class DataBaseActivity extends BaseActivity {

    private ActivityDataBaseBinding binding;
    private DataBaseAdapter dataBaseAdapter;
    private List<WordBean> wordList;
    // 线程池
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataBaseBinding.inflate(LayoutInflater.from(this), null, false);
        setContentView(binding.getRoot());
        initDataBase();
        initView();
    }

    private void initView() {
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        dataBaseAdapter = new DataBaseAdapter(this);
        binding.recyclerview.setAdapter(dataBaseAdapter);
    }

    private void initDataBase() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    WeakReference<DataBaseActivity> reference = new WeakReference<>(DataBaseActivity.this);

                    boolean wordsInit = SpUtils.getBoolean(MyApplication.getAppContext(), "words_init", false);

                    if (!wordsInit) {
                        showProgressDialog("");
                        InputStream inputStream = getAssets().open("words_105k.sql");
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String str = "";
                        boolean insert;
                        int count = 0;
                        while ((str = bufferedReader.readLine()) != null) {
                            insert = DataBaseDao.INSTANCE.insertBySqlStr(str);
                            count++;
                            // 因数据过于庞大 每100次进行 进度更新
                            if (count % 10 == 0) {
                                // int progress = (int) (count / 103976f * 100);
                                //int progress = (int) (count / 100000f * 100);
                                int progress = (int) (count / 2000f * 100);
                                if (reference.get() != null) {
                                    Message message = Message.obtain();
                                    message.what = 2001;
                                    message.arg1 = progress;
                                    reference.get().handler.sendMessage(message);
                                }
                            }

                            // 单词库有10万多 节省时间演示  所以写入2000
                            if (count > 2000) {
                                break;
                            }

                        }
                        SpUtils.putBoolean(MyApplication.getAppContext(), "words_init", true);
                    }

                    if (reference.get() != null) {
                        reference.get().wordList = DataBaseDao.INSTANCE.queryWords();
                        reference.get().handler.sendEmptyMessage(200);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.execute(runnable);
    }

    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 200:
                    dataBaseAdapter.setList(wordList);
                    dismissProgressDialog();
                    break;
                case 2001:
                    // 进度更新
                    progressDialog.setProgress(msg.arg1);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    static class DataBaseAdapter extends BaseRecyclerAdapter<WordBean, BaseViewHolder> {

        public DataBaseAdapter(Context context) {
            super(context);
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_word_layout, parent, false);
            return new BaseViewHolder(view);
        }

        @Override
        protected void onBindItemView(BaseViewHolder holder, WordBean wordBean, int position) {
            holder.setText(R.id.word_tv, wordBean.word)
                    .setText(R.id.trans_tv, wordBean.translate)
                    .setVisibility(R.id.line, isLastPosition(position) ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void showProgressDialog(String tips) {
        getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(DataBaseActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMax(100);
                    progressDialog.setTitle("提示");
                    progressDialog.setMessage("请稍后，数据库初始化...");
                }
                progressDialog.setProgress(0);
                progressDialog.show();
            }
        });
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
    }
}