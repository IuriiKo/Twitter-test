package com.kushyk.twittertest.ui.tweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kushyk.twittertest.App;
import com.kushyk.twittertest.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xaocu on 19.03.2017.
 */

public class TimeLineFragment extends Fragment {
    private static final String LOG_TAG = TimeLineFragment.class.getSimpleName();
    private static final int REQUEST_CODE = 11;
    public static final String SCREEN_NAME = "screenName";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.actionButton)
    FloatingActionButton actionButton;

    private final CompositeDisposable disposable = new CompositeDisposable();
    private TweetTimeLineAdapter adapter;
    private boolean isLoading;
    private Unbinder unbinder;

    public static Fragment init(String screenName) {
        Fragment fragment = new TimeLineFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SCREEN_NAME, screenName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_line, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        getUserTimeline(null);
        return view;
    }

    private void initRecyclerView() {
        adapter = new TweetTimeLineAdapter(null);
        adapter.setTweetClickListener(this::openTimeLineActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                    Tweet lastTweet = adapter.getLastItem();
                    if (lastTweet != null ) {
                        getUserTimeline(lastTweet.id);
                    }
                }
            }
        });
    }

    private void openTimeLineActivity(String screenName) {
        startActivity(TimeLineActivity.init(getActivity(), screenName));
    }

    @OnClick(R.id.actionButton)
    void writeTweet() {
        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        if (session == null) {
            startActivityForResult(TwitterLoginActivity.init(getActivity()), REQUEST_CODE);
        } else {
            startCreateTweetActivity(session);
        }
    }

    private void startCreateTweetActivity(TwitterSession session) {
        startActivity(App.getTweetManager().createTweetIntent(getActivity(),session));
    }

    private void getUserTimeline(Long maxId) {
        isLoading = true;
        disposable.add(App.getTweetManager().getTimeLie(getArguments().getString(SCREEN_NAME), maxId)
                .flatMap(Observable::fromIterable)
                .filter(item -> !adapter.getTweets().contains(item))
                .switchIfEmpty(Observable.error(new Throwable("getUserTimeline() List<Tweet> is empty")))
                .toList()
                .doFinally(() -> isLoading = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessGetUserTimeline, this::onErrorGetUserTimeline));
    }

    private void onSuccessGetUserTimeline(List<Tweet> tweets) {
        adapter.addTweets(tweets);
    }

    private void onErrorGetUserTimeline(Throwable throwable) {
        Log.e(LOG_TAG, "onErrorGetUserTimeline()", throwable);
        Toast.makeText(getActivity(), R.string.erro_get_data, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                writeTweet();
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
        unbinder.unbind();
    }
}
