package com.example.android.querymaster;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerDetailsActivity extends AppCompatActivity {

    private static QueryObject mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_details);

        TextView query1 = findViewById(R.id.QueryName);
        TextView name1 = findViewById(R.id.UserName);
        TextView time1 = findViewById(R.id.AnswerTime);
        TextView answer1 = findViewById(R.id.QueryAnswer);
        Button upvoteButton=findViewById(R.id.UpvoteButton);
        Button tagButton=findViewById(R.id.TagButton);
        Button shareButton=findViewById(R.id.ShareButton);

        query1.setText(mQuery.getmQuery());
        name1.setText(mQuery.getmName());
        time1.setText(mQuery.getmTime());
        answer1.setText(mQuery.getmAnswer());
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
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.sign_out_menu).setTitle("BOOKMARK").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_out_menu) {
            if (QueryListActivity.searchInBookMarkList(mQuery)) {
                Snackbar.make(findViewById(R.id.AnswerDetailView),
                        "Already Saved", Snackbar.LENGTH_SHORT).show();
            } else {
                QueryListActivity.setBookmark(mQuery);
                Snackbar.make(findViewById(R.id.AnswerDetailView),
                        "Saved Answer", Snackbar.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setMQuery(QueryObject query)
    {
        mQuery=query;
    }
}
