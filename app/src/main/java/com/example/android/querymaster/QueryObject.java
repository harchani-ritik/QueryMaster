package com.example.android.querymaster;

import java.util.ArrayList;

public class QueryObject {
  
    private String mKey;
    private String mQuery;
    private ArrayList<String> mAnswers=new ArrayList<>();
    private String mTime;

    public QueryObject() {//default constructor required for firebase
    }

    public QueryObject(String mQuery) {//constructor used
        this.mQuery = mQuery;
    }

    public String getmQuery() {
        return mQuery;
    }

    public ArrayList<String> getmAnswers() {
        return mAnswers;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmAnswers(ArrayList<String> mAnswers) {
        this.mAnswers = mAnswers;
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
