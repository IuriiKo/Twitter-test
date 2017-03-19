package com.kushyk.twittertest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kushyk.twittertest.R;
import com.kushyk.twittertest.ui.tweet.TimeLineFragment;

public class MainActivity extends AppCompatActivity {
    private static final String FIRST_SCREEN_NAME = "qwerty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, TimeLineFragment.init(FIRST_SCREEN_NAME))
                .commit();
    }
}
