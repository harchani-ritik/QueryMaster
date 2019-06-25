package com.example.android.querymaster;

import java.util.ArrayList;

public class QueryObject {
    private String mQuery;
    private String mTime;
    private int mNumberOfAnswers;
    private ArrayList<String> mAnswers=new ArrayList<>();

    public QueryObject() {//default constructor required for firebase
    }

    public QueryObject(String mQuery) {//constructor used
        this.mQuery = mQuery;
        mAnswers.add("No Answers till Now");
        mNumberOfAnswers=mAnswers.size();
    }

    public String getmQuery() {
        return mQuery;
    }

    public ArrayList<String> getmAnswers() {
        return mAnswers;
    }

    public int getmNumberOfAnswers() {
        return mNumberOfAnswers;
    }

    public void setmAnswers(ArrayList<String> mAnswers) {
        this.mAnswers = mAnswers;
        mNumberOfAnswers=this.mAnswers.size();
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
