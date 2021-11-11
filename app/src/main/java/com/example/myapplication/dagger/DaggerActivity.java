package com.example.myapplication.dagger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.derry.annotation.BindMyView;
import com.example.myapplication.R;
import com.example.myapplication.dagger.expand.StudentMutil;

import javax.inject.Inject;

/**
 * Dagger 2 是 Java 和 Android 下的一个完全静态、编译时生成代码的依赖注入框架，由 Google 维护，早期的版本 Dagger 是由 Square 创建的。
 * Dagger 2 是基于 Java Specification Request(JSR) 330标准。利用 JSR 注解在编译时生成代码，来注入实例完成依赖注入。
 *
 * 下面是 Dagger 2 的一些资源地址：
 * Github：https://github.com/google/dagger
 * 官方文档：https://google.github.io/dagger//
 * API：http://google.github.io/dagger/api/latest/
 *
 */
@BindMyView("value123213")
public class DaggerActivity extends AppCompatActivity {


    // 使用javax.inject.Inject注解来标注需要 Dagger 2 注入的依赖，
    // build 后可以在build/generated/source/apt目录下看到 Dagger 2
    // 编译时生成的成员属性注入类。
    // 以@Inject标注的成员属性不能是private的，不然无法注入
    @Inject // 使用 @Inject 标注需要注入的依赖
    public Student student;
    @Inject
    public StudentMutil studentMutil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

        // 依赖注入绑定
        DaggerStudentComponent.create().injectDaggerActivity(this);

        View view = findViewById(R.id.textview);

        view.postDelayed(() -> Toast.makeText(DaggerActivity.this,
                student.name + "  " + student.age + "  " + student.phone,
                Toast.LENGTH_SHORT).show(), 2000);
        view.postDelayed(() -> Toast.makeText(DaggerActivity.this,
                studentMutil.name + "  " + studentMutil.age + "  " + studentMutil.phone,
                Toast.LENGTH_SHORT).show(), 5000);

    }
}
/**
 *
 * 需要注入依赖的目标类，需要注入的实例属性由@Inject标注。
 *
 * 提供依赖对象实例的工厂，用@Inject标注构造函数或定义Module这两种方式都能提供依赖实例，Dagger 2
 * 的注解处理器会在编译时生成相应的工厂类。Module的优先级比@Inject标注构造函数的高，意味着 Dagger 2 会先从 Module 寻找依赖实例。
 *
 * 把依赖实例工厂创建的实例注入到目标类中的 Component。
 *
 * 下面再讲述上面提到的在 Dagger 2 种几个注解的用法：
 *
 * @Inject 一般情况下是标注成员属性和构造函数，标注的成员属性不能是private，Dagger 2 还支持方法注入，@Inject还可以标注方法。
 * @Provides 只能标注方法，必须在 Module 中。
 * @Module 用来标注 Module 类
 * @Component 只能标注接口或抽象类，声明的注入接口的参数类型必须和目标类一致。
 *
 * 作者：JohnnyShieh
 * 链接：https://www.jianshu.com/p/26d9f99ea3bb
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 */