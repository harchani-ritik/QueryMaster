package com.example.android.querymaster;

import java.util.ArrayList;

public class QueryObject {
    private String mQuery;
    private String mTime;
    public ArrayList<String> mAnswers=new ArrayList<>();

    public QueryObject() {
    }

    public QueryObject(String mQuery) {
        this.mQuery = mQuery;
        mAnswers=new ArrayList<>();
    }

    public String getmQuery() {
        return mQuery;
    }

    public ArrayList<String> getmAnswers() {
        return mAnswers;
    }

    public void setmAnswers(ArrayList<String> mAnswers) {
        this.mAnswers = mAnswers;
    }

    public int getNumberOfAnswers()
    {
        return mAnswers.size();
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
