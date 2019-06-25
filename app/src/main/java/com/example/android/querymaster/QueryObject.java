package com.example.android.querymaster;

import java.util.ArrayList;

public class QueryObject {
    private String mKey;
    private String mQuery;
    private ArrayList<String> mAnswers=new ArrayList<>();
    private int mNumberOfAnswers;
    private String mTime;

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

    public String getmKey() {
        return mKey;
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

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

}
