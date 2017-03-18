package com.kushyk.twittertest;

import android.app.Application;

import com.kushyk.twittertest.managers.TweetManager;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by xaocu on 18.03.2017.
 */

public class App extends Application {
    private static TweetManager tweetManager;
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "hNMQUhZlEPDUYSluxrT0jFdgt";
    private static final String TWITTER_SECRET = "ml82tGfs0YJSx5D22UCwgoZX3KfEIwYBZRh1WRvqWtz8U89E2c";

    @Override
    public void onCreate() {
        super.onCreate();
        initTwitterApi();


    }

    private void initTwitterApi() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        tweetManager = new TweetManager();
    }

    public static TweetManager getTweetManager() {
        return tweetManager;
    }

}
