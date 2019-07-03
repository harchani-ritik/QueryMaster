package com.example.android.querymaster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerDetailsActivity extends AppCompatActivity {

    private String query;
    private String answer;
    private String name;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_details);
        getIncomingIntent();
        TextView query1 = findViewById(R.id.QueryName);
        TextView name1 = findViewById(R.id.UserName);
        TextView time1 = findViewById(R.id.AnswerTime);
        TextView answer1 = findViewById(R.id.QueryAnswer);
        Button upvoteButton=findViewById(R.id.UpvoteButton);
        Button tagButton=findViewById(R.id.TagButton);
        Button shareButton=findViewById(R.id.ShareButton);

        query1.setText(query);
        name1.setText(name);
        time1.setText(time);
        answer1.setText(answer);
        upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnswerDetailsActivity.this,"Upvoted",Toast.LENGTH_SHORT).show();
            }
        });
        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnswerDetailsActivity.this,"Insufficient Reputation",Toast.LENGTH_SHORT).show();
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AnswerDetailsActivity.this,"Share",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getIncomingIntent()
    {
        query=getIntent().getStringExtra("query");
        name=getIntent().getStringExtra("name");
        time=getIntent().getStringExtra("time");
        answer=getIntent().getStringExtra("answer");
    }
}
