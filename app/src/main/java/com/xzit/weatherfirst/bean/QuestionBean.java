package com.xzit.weatherfirst.bean;

import java.util.ArrayList;
import java.util.List;

//驾考试题类
public class QuestionBean {
    private String id;  //试题编号
    private String url;    //图片路径
    private String question; //试题
    private String item1;   //选项一
    private String item2;   //选项二
    private String item3; //选项三
    private String item4; //选项四
    private String answer; //试题答案
    private String explains;

    public QuestionBean(String url, String question,String item1,String item2 ,String item3,String item4,String answer,String explains) {
        this.url = url;
        this.question = question;
        this.explains = explains;
        this.answer = answer;
        this.item4 = item4;
        this.item3 = item3;
        this.item2 = item2;
        this.item1 = item1;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //选择题构造方法
    public QuestionBean(String id, String question, String item2, String item3, String item1, String answer, String item4, String explains, String url) {
        this.id = id;
        this.question = question;
        this.item2 = item2;
        this.item3 = item3;
        this.item1 = item1;
        this.answer = answer;
        this.item4 = item4;
        this.explains = explains;
        this.url = url;
    }
    //简答题构造方法

    public QuestionBean(String item1, String question, String id, String item2, String answer, String explains, String url) {
        this.item1 = item1;
        this.question = question;
        this.id = id;
        this.item2 = item2;
        this.answer = answer;
        this.explains = explains;
        this.url = url;
    }

    public QuestionBean() {
    }
//    public static List<QuestionBean> getQuestionList(){
//        List<QuestionBean> questions=new ArrayList<>();
//
//        return  questions;
//    }
}
