package com.example.android.querymaster;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ANONYMOUS = "Anonymous";
    public static final int RC_SIGN_IN = 1;
    public boolean EXPERT_LOGIN;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private DatabaseReference mUserDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ArrayList<QueryObject> queryObjectArrayList;
    private ArrayList<User> userObjectArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView myText;

    private String mUsername;
    private String mUseremail;
    private Button submitQueryButton;
    private EditText queryEditText;
    private DrawerLayout mDrawerLayout;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationview = findViewById(R.id.nav_view);
        navigationview.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        constraintLayout=findViewById(R.id.main_root_view);
        getIncomingIntent();

        queryObjectArrayList = new ArrayList<>();
        userObjectArrayList=new ArrayList<>();
        myText = findViewById(R.id.display_text_view);
        submitQueryButton = findViewById(R.id.submit_query_button);
        queryEditText = findViewById(R.id.query_edit_text);
        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();

        queryEditText.setFocusable(false);
        queryEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryEditText.setFocusableInTouchMode(true);
            }
        });
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("queries");
        mUserDatabaseReference=mFirebaseDatabase.getReference().child("users");

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    updateUserDatabase(user);
                    onSignedInInitialize(user.getDisplayName(),user.getEmail());
                } else {
                    // User is signed out
                    onSignedOutCleanup();
                    startActivityForResult(

                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setTheme(R.style.AppTheme)
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyRecyclerViewAdapter(queryObjectArrayList);
        mRecyclerView.setAdapter(mAdapter);

        queryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    submitQueryButton.setEnabled(true);
                } else {
                    submitQueryButton.setEnabled(false);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        submitQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Query Submitted",Toast.LENGTH_SHORT).show();
                String query=queryEditText.getText().toString();
                QueryObject queryObject = new QueryObject(query);
                Date currentTime = Calendar.getInstance().getTime();
                queryObject.setmTime(currentTime.toString());
                String key=mMessagesDatabaseReference.push().getKey();
                queryObject.setmKey(key);
                if(key!=null)
                    mMessagesDatabaseReference.child(key).setValue(queryObject);
                queryEditText.setText("");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("MainActivity", " Clicked on Item " + position);
                //Intent to Master Activity for showing list of answers of a Query
                /*String key=queryObjectArrayList.get(position).getmKey();
                Intent myIntent = new Intent(MainActivity.this, QueryListActivity.class);
                myIntent.putExtra("ObjKey",key);
                startActivity(myIntent);*/
            }
        });
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialize(String username,String Useremail) {
        mUsername = username;
        mUseremail= Useremail;
        myText.setText("Welcome "+mUsername+" to QueryMaster!");
        attachDatabaseReadListener();
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        detachDatabaseReadListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Use to inflate the SignOut Options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //The function is used to implement SignOut Option
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void attachDatabaseReadListener() {
        Toast.makeText(MainActivity.this, "Loading Queries", Toast.LENGTH_SHORT).show();


        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    QueryObject queryObject = dataSnapshot.getValue(QueryObject.class);
                    queryObjectArrayList.add(queryObject);
                    mAdapter = new MyRecyclerViewAdapter(queryObjectArrayList);
                    mRecyclerView.setAdapter(mAdapter);
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private void updateUserDatabase(FirebaseUser user) {
        String uid= user.getUid();
        int flag=0;
        for( User u : userObjectArrayList)
            if(u.getUid().equals(uid))
                flag=1;
        if(flag==0) {
            User newUser = new User(user.getDisplayName(), user.getEmail(), false,uid);
            userObjectArrayList.add(newUser);
            mUserDatabaseReference.child(user.getUid()).setValue(newUser);
        }

    }

    public void getIncomingIntent() {
        int ObjPosition;String ObjKey;
        ArrayList<String> answerArrayList;
        if (getIntent().hasExtra("objPosition") && getIntent().hasExtra("answersList") && getIntent().hasExtra("objKey")) ;
        {
            answerArrayList=getIntent().getStringArrayListExtra("answersList");
            ObjPosition=getIntent().getIntExtra("objPosition",-1);
            ObjKey=getIntent().getStringExtra("objKey");
        }
        if(ObjPosition!=-1) {
            Snackbar.make(constraintLayout,"Answer Submitted", Snackbar.LENGTH_SHORT).show();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("queries");
            mMessagesDatabaseReference.child(ObjKey).child("mAnswers").setValue(answerArrayList);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.user_profile: {
                //Toast.makeText(this, " User Profile clicked", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MainActivity.this, UserProfile.class);
                myIntent.putExtra("name",mUsername);
                myIntent.putExtra("email",mUseremail);
                startActivity(myIntent);
                break;
            }
            case R.id.home: {
                Toast.makeText(this, " Home clicked", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.Suggestions: {
                Toast.makeText(this, " Suggestions clicked", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed () {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

}

