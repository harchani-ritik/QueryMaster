package com.example.android.querymaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AnsweringActivity extends AppCompatActivity {

    private TextView questionTextView;
    private TextView answerEditText;
    private String mAnswer;
    private String questionName;
    private String mTime;
    private String ObjKey;
    private Boolean isObjectAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answering_);
        questionTextView=findViewById(R.id.questionTextView);//fill in this using intent from main activity
        answerEditText=findViewById(R.id.answerEditText);

        getIncomingIntent();

    }

    private void getIncomingIntent()
    {
            questionName=getIntent().getStringExtra("question");
            questionTextView.setText(questionName);

            mTime=getIntent().getStringExtra("queryTime");
            if(getIntent().hasExtra("objKey"))
            {
                isObjectAdded=true;
                ObjKey=getIntent().getStringExtra("objKey");
            }
            else
                isObjectAdded=false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Use to inflate the SignOut Options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_menu:
                if(!answerEditText.getText().toString().equals("")) {
                    mAnswer = answerEditText.getText().toString();
                    answerEditText.setText("");
                    sendAnswerToDatabase();
                    Intent myIntent = new Intent(this, MainActivity.class);
                    myIntent.putExtra("showSnackBar",true);
                    startActivity(myIntent);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void sendAnswerToDatabase()
    {
        String time= Calendar.getInstance().getTime().toString().split("G")[0];
        String username = MainActivity.getmUsername();
        FirebaseDatabase mFirebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference mMessagesDatabaseReference=mFirebaseDatabase.getReference().child("queries");
        if(isObjectAdded)
        {
            mMessagesDatabaseReference.child(ObjKey).child("mAnswerTime").setValue(time);
            mMessagesDatabaseReference.child(ObjKey).child("mAnswer").setValue(mAnswer);
            mMessagesDatabaseReference.child(ObjKey).child("mName").setValue(username);
        }
        else
        {
            QueryObject queryObject = new QueryObject(questionName);
            queryObject.setmTime(mTime);
            queryObject.setmAnswer(mAnswer);
            queryObject.setmAnswerTime(time);
            queryObject.setmName(username);
            String key=mMessagesDatabaseReference.push().getKey();
            queryObject.setmKey(key);
            if(key!=null)
                mMessagesDatabaseReference.child(key).setValue(queryObject);
        }
    }

}
