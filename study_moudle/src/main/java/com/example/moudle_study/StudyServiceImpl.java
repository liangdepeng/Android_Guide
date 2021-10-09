package com.example.moudle_study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.example.common_module.IStudyService;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/2
 * <p>
 * Summary: Studymoudle组件和其他组件 通信实现类
 */
public class StudyServiceImpl implements IStudyService {
    @Override
    public void launchStudyTest(Context context, Bundle bundle) {
        Intent intent = new Intent(context, StudyTestMainActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
