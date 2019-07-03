package com.example.android.querymaster;

public class QueryObject {

    private String mKey;
    private String mQuery;
    private String mAnswer;
    private String mTime;//time of Query Asked
    private String mName;

    public QueryObject() {//default constructor required for firebase
    }

    public QueryObject(String mQuery) {//constructor used
        this.mQuery = mQuery;
        mAnswer=null;
    }

    public String getmQuery() {
        return mQuery;
    }

    public String getmAnswer() {
        return mAnswer;
    }

    public String getmKey() {
        return mKey;
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

    public void setmAnswer(String mAnswer) {
        this.mAnswer = mAnswer;
    }
}
