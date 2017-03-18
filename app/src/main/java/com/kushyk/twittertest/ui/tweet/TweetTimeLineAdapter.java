package com.kushyk.twittertest.ui.tweet;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.klinker.android.link_builder.TouchableMovementMethod;
import com.kushyk.twittertest.R;
import com.kushyk.twittertest.util.LinkedTextUtil;
import com.kushyk.twittertest.util.PatternUtil;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xaocu on 18.03.2017.
 */

public class TweetTimeLineAdapter extends RecyclerView.Adapter<TweetTimeLineAdapter.TweetViewHolder> implements Link.OnClickListener{
    private List<Tweet> tweets = new ArrayList<>();
    private TweetClickListener tweetClickListener;
    private Link link = LinkedTextUtil.getTweetLink(this);

    public TweetTimeLineAdapter(List<Tweet> tweets) {
        if(tweets != null){
            this.tweets.addAll(tweets);
        }
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TweetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tweet, parent, false));
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        CharSequence text = LinkedTextUtil.getLinkedText(holder.tweetView.getContext(), tweets.get(position).text, link);
        holder.tweetView.setText(text);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    @Override
    public void onClick(String clickedText) {
        if (tweetClickListener != null) {
            tweetClickListener.onClickScreenName(clickedText);
        }
    }

    public void setTweetClickListener(TweetClickListener tweetClickListener) {
        this.tweetClickListener = tweetClickListener;
    }

    class TweetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tweetView)
        TextView tweetView;

        public TweetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tweetView.setMovementMethod(TouchableMovementMethod.getInstance());
        }
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(@NonNull List<Tweet> tweets) {
        this.tweets.clear();
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    public interface TweetClickListener{
        void onClickScreenName(String clickedText);
    }
}
