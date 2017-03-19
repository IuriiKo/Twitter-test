package com.kushyk.twittertest.ui.tweet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kushyk.twittertest.R;

public class TimeLineActivity extends AppCompatActivity {

    public static Intent init(Context context, String screenName) {
        Intent intent = new Intent(context, TimeLineActivity.class);
        intent.putExtra(TimeLineFragment.SCREEN_NAME, screenName);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, TimeLineFragment.init(getIntent().getStringExtra(TimeLineFragment.SCREEN_NAME)))
                .commit();
    }

}
