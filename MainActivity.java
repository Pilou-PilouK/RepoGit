package com.example.topquizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.topquizz.model.User;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Button mscore;
    private User mUser;
    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    public static final int CLASSEMENT_REQUEST_CODE = 12;
    private SharedPreferences mPreferences;

    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";

    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";

    public MainActivity() {
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new User();
        mPreferences = getPreferences(MODE_PRIVATE);
        mGreetingText = findViewById(R.id.Greeting);
        mNameInput =  findViewById(R.id.Name);
        mPlayButton =  findViewById(R.id.Play);
        mscore = findViewById(R.id.TopScore);


        mPlayButton.setEnabled(false);

        greetUser();

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mPlayButton.setEnabled(s.toString().length() != 0);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = mNameInput.getText().toString();
                mUser.setmFirstName(firstname);

                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                preferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getmFirstName()).apply();

                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);

            }
        });

        mscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent classement = new Intent(MainActivity.this, ClassementActivity.class);
                startActivityForResult(classement, CLASSEMENT_REQUEST_CODE);
            }
        });
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {

            // Fetch the score from the Intent

            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();

            greetUser();


        }

    }

    private void greetUser(){
        String firstname = getPreferences(MODE_PRIVATE).getString(PREF_KEY_FIRSTNAME, null);

        if(firstname!=null){
            int score = mPreferences.getInt(PREF_KEY_SCORE,0);

            String fulltext = "Welcome back, " + firstname
                    +"!\nYour last score was : "+score
                    +", will you do better this time?";
            mGreetingText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }

    }



    @Override

    protected void onStart() {

        super.onStart();

        System.out.println("MainActivity::onStart()");

    }



    @Override

    protected void onResume() {

        super.onResume();

        System.out.println("MainActivity::onResume()");

    }



    @Override

    protected void onPause() {

        super.onPause();

        System.out.println("MainActivity::onPause()");

    }



    @Override

    protected void onStop() {

        super.onStop();

        System.out.println("MainActivity::onStop()");

    }



    @Override

    protected void onDestroy() {

        super.onDestroy();

        System.out.println("MainActivity::onDestroy()");

    }
}
