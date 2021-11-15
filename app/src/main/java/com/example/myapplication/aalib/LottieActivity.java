package com.example.myapplication.aalib;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/15
 * <p>
 * Summary:
 */
public class LottieActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);

        // lottie 动画 继承自 ImageView 是通过 canvas 不断绘制的 动画是属性动画
        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.lottie_view);
        animationView.setAnimation("loading_for_b.json");
        animationView.setRepeatCount(LottieDrawable.INFINITE);
        animationView.playAnimation();
    }
}
