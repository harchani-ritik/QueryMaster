
package com.example.android.querymaster;

import java.util.ArrayList;

public class QueryObject {
    private String mQuery;
    private ArrayList<String> mAnswers;
    public QueryObject() {
    }

    public QueryObject(String mQuery) {
        this.mQuery = mQuery;
        mAnswers.set(0,"No Answer Till Now");
    }

    public void setmAnswers(String answer) {
        mAnswers.add(answer);
    }

    public String getmQuery() {
        return mQuery;
    }

    public ArrayList<String> getmAnswers() {
        return mAnswers;
    }
}
