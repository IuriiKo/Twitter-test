package com.kushyk.twittertest.ui.tweet;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kushyk.twittertest.R;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xaocu on 18.03.2017.
 */

public class TweetTimeLineAdapter extends RecyclerView.Adapter<TweetTimeLineAdapter.TweetViewHolder> {
    private List<Tweet> tweets = new ArrayList<>();

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TweetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tweet, parent, false));
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        holder.tweetView.setTweet(tweets.get(position));
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    class TweetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tweetView)
        TweetView tweetView;

        public TweetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(@NonNull List<Tweet> tweets) {
        this.tweets.clear();
        this.tweets.addAll(tweets);
    }
}
