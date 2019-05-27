package com.example.android.querymaster;

public class QueryObject {
    private String mQuery;
    private String mAnswer="ANSWER";

    public QueryObject(String mQuery) {
        this.mQuery = mQuery;
    }
    public void setmAnswer(String mAnswer) {
        this.mAnswer = mAnswer;
    }
    public String getmQuery() {
        return mQuery;
    }
    public String getmAnswer() {
        return mAnswer;
    }
}
