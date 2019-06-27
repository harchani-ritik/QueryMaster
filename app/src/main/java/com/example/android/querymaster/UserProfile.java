package com.example.android.querymaster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    private String mUsername;
    private String mUseremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getIncomingIntent();
        TextView nameView= findViewById(R.id.name_text_view);
        nameView.setText(mUsername);
        TextView emailView= findViewById(R.id.email_text_view);
        emailView.setText(mUseremail);

    }
    public void getIncomingIntent()
    {
        if (getIntent().hasExtra("name")&&getIntent().hasExtra("email"))
        {
            mUsername=getIntent().getStringExtra("name");
            mUseremail=getIntent().getStringExtra("email");
        }
    }
}
