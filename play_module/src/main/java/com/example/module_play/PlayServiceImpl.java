package com.example.module_play;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.commom_module.IPlayService;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/2
 * <p>
 * Summary: playmoudle组件和其他组件 通信实现类
 */
public class PlayServiceImpl implements IPlayService {

    @Override
    public void launchPlayTest(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PlayTestActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
