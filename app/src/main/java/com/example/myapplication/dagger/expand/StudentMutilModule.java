package com.example.myapplication.dagger.expand;

import com.example.myapplication.dagger.Student;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/10
 * <p>
 * Summary: 使用@Inject标注构造函数来提供依赖的对象实例的方法，不是万能的，
 * 在以下几种场景中无法使用：
 * 接口没有构造函数、第三方库的类不能被标注、构造函数中的参数必须配置
 *
 * 这时，就可以用@Provides标注的方法来提供依赖实例，方法的返回值就是依赖的对象实例，
 * @Provides方法必须在Module中，Module 即用@Module标注的类。所以
 * Module 是提供依赖的对象实例的另一种方式
 *
 */
@Module
public class StudentMutilModule {

    @Provides
    public StudentMutil getStudentMutil(){
        return new StudentMutil(100,"ldp","123321123");
    }

    @Provides
    public Student getStudent(){
        return new Student();
    }

}
