package com.example.android.querymaster;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Answering_Activity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView answerImageView = findViewById(R.id.answerImageView);
            answerImageView.setVisibility(View.VISIBLE);
            answerImageView.setImageBitmap(imageBitmap);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answering_);
        TextView questionTextView=findViewById(R.id.questionTextView);//fill in this using intent from main activity
        EditText answerEditText=findViewById(R.id.answerEditText);
        Button submitButton=findViewById(R.id.submitButton);
        Button uploadimageButton=findViewById(R.id.uploadimageButton);
        questionTextView.setText("This is question space");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //use an intent to send text and image to whichever activity you want!
                Toast toast= Toast.makeText(getApplicationContext(),"Submit button clicked",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        uploadimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }
}
