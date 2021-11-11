package com.derry.annotation;


/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/10
 * <p>
 * Summary:
 */
@BindMyView("value123")
public class TestJava {
    public static void main(String[] args) {

        // 利用反射获取注解标识
        testZhuJie();
    }

    private static void testZhuJie() {
        try {
             Class<?> studentClass = Class.forName("com.example.java_lib.Student")
                     .newInstance().getClass();

//            Student student = new Student("123");
//            Class<? extends Student> studentClass = student.getClass();

            if (studentClass.isAnnotationPresent(TestZhuJie.class)) {

                System.out.println("student 有 TestZhuJie 注解 被获取到了");

                // 解析注解
                TestZhuJie annotation = studentClass.getAnnotation(TestZhuJie.class);
                String value = annotation.value();
                boolean testParam = annotation.isTestParam();

                System.out.println("     " + value + "   " + testParam);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
