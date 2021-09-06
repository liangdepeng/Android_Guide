package com.example.myapplication.bean;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/6
 * <p>
 * Summary:
 */
public class WordBean
{
    public String word;
    public String translate;
    public String en_pronunciation;
    public String us_pronunciation;
    public boolean isCollect;

    public WordBean(String word, String translate, boolean isCollect) {
        this.word = word;
        this.translate = translate;
        this.isCollect = isCollect;
    }
}
