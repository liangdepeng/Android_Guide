package com.derry.lib;

import com.derry.annotation.BindMyView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/10
 * <p>
 * Summary: 只要成为 AbstractProcessor的子类，就已经是注解处理器了
 */
@AutoService(Processor.class)// Google的这个AutoService可以去生成配置文件
@SupportedSourceVersion(SourceVersion.RELEASE_7)// // 配置版本（Java编译时的版本）
@SupportedAnnotationTypes({"com.derry.annotation.BindMyView"})// // 允许注解处理器  可以去处理的注解，不是所有的注解处理器都可以去处理
@SupportedOptions("value")//// 注解处理器能够接收的参数（例如：如果想把AndroidApp信息传递到这个注解处理器(Java工程)，是没法实现的，所以需要通过这个才能接收到）
public class CustomProcessor extends AbstractProcessor {

    // 注解节点
    private Elements elementUtils;
    // 类信息
    private Types typeUtils;
    // 专用日志
    private Messager envMessager;
    //
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        envMessager = processingEnv.getMessager();
        filer = processingEnv.getFiler();

        String value = processingEnv.getOptions().get("value");
        envMessager.printMessage(Diagnostic.Kind.NOTE, "init方法打印 --> 从Android App " + "Gradle" +
                "中传递过来的值value:" + value);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements =
                roundEnvironment.getElementsAnnotatedWith(BindMyView.class);

        printMessage(">>> size:" + elements.size());

        for (Element element : elements) {

            MethodSpec main = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "这样厉害的人，世界上还有几人 Hello, JavaPoet!")
                    .build();

            printMessage("1111111111...main 方法完成");

            TypeSpec typeSpec =
                    TypeSpec.classBuilder(element.getSimpleName().toString()).addModifiers(Modifier.PUBLIC).addMethod(main).build();

            printMessage("2222222222...class 生成完毕");

            JavaFile javaFile =
                    JavaFile.builder("com.example.processor", typeSpec).build();

            printMessage("333333333... java file准备完毕开始写入");

            try {
                javaFile.writeTo(filer);
            } catch (Exception e) {
                printMessage("写入异常  生成java类异常" + e.getMessage());
                e.printStackTrace();
            }
        }

        printMessage("执行完毕了...");

        return true;
    }

    private void printMessage(String message) {
        envMessager.printMessage(Diagnostic.Kind.NOTE, message);
    }
}
