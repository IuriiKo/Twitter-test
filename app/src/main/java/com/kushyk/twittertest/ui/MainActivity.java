package com.kushyk.twittertest.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.kushyk.twittertest.App;
import com.kushyk.twittertest.R;
import com.kushyk.twittertest.ui.tweet.TweetTimeLineAdapter;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.ListService;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetcomposer.TweetUploadService;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CompositeDisposable disposable = new CompositeDisposable();
    private TweetTimeLineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();
        getUserTimeline();
    }

    private void initRecyclerView() {
        adapter = new TweetTimeLineAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void getUserTimeline() {
        disposable.add(App.getTweetManager().getTimeLie("qwerty")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessGetUserTimeline, this::onErrorGetUserTimeline));
    }

    private void onSuccessGetUserTimeline(List<Tweet> tweets) {
        adapter.setTweets(tweets);
    }

    private void onErrorGetUserTimeline(Throwable throwable) {
        Log.e(LOG_TAG, "onErrorGetUserTimeline()", throwable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
