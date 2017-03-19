package com.kushyk.twittertest.ui.tweet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.kushyk.twittertest.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TwitterLoginActivity extends AppCompatActivity {
    private static final String LOG_TAG = TwitterLoginActivity.class.getSimpleName();
    @BindView(R.id.loginButton)
    TwitterLoginButton loginButton;

    public static Intent init(Context context) {
        return new Intent(context, TwitterLoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_register);
        ButterKnife.bind(this);
        initLoginButton();
    }

    private void initLoginButton() {
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e(LOG_TAG, "initLoginButton()", exception);
                Toast.makeText(TwitterLoginActivity.this, R.string.erro_twitter_signin, Toast.LENGTH_LONG).show();
                setResult(RESULT_CANCELED);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
