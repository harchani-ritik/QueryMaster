package com.example.android.querymaster;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AnsweringActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public static final String TAG="AnsweringActivity";
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
        Button uploadImageButton=findViewById(R.id.uploadimageButton);

        getIncomingIntent();

        /*uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });*/
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView answerImageView = findViewById(R.id.answerImageView);
            answerImageView.setVisibility(View.VISIBLE);
            answerImageView.setImageBitmap(imageBitmap);
        }
    }*/

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
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
                    //Toast.makeText(getApplicationContext(), "Submitting Answer", Toast.LENGTH_SHORT).show();

                    mAnswer = answerEditText.getText().toString();
                    answerEditText.setText("");
                    sendAnswerToDatabase();
                    Intent myIntent = new Intent(this, MainActivity.class);
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
