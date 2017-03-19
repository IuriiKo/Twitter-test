package com.kushyk.twittertest.ui.tweet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.TouchableMovementMethod;
import com.kushyk.twittertest.R;
import com.kushyk.twittertest.util.LinkedTextUtil;
import com.kushyk.twittertest.util.ViewUtil;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xaocu on 18.03.2017.
 */

public class TweetTimeLineAdapter extends RecyclerView.Adapter<TweetTimeLineAdapter.TweetViewHolder> implements Link.OnClickListener{
    private static final int TWEET_TYPE = 0;
    private static final int RETWEET_TYPE = 1;
    private final List<Tweet> tweets = new ArrayList<>();
    private TweetClickListener tweetClickListener;
    private final Link link = LinkedTextUtil.getTweetLink(this);

    public TweetTimeLineAdapter(List<Tweet> tweets) {
        if(tweets != null){
            this.tweets.addAll(tweets);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return tweets.get(position).retweeted ? RETWEET_TYPE : TWEET_TYPE;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TweetViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType == TWEET_TYPE ? R.layout.item_tweet : R.layout.item_retweet, parent, false));
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);
        holder.tweetView.setText(getTweetText(holder.tweetView.getContext(), tweet));
        ViewUtil.loadCircleUserImage(holder.userImageView, tweet.user.profileImageUrl);
    }

    private CharSequence getTweetText(Context context, Tweet tweet) {
        CharSequence text = LinkedTextUtil.getLinkedText(context, tweet.text, link);
        if (text == null) {
            text = tweet.text;
        }
        return text;
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

    public List<Tweet> getTweets() {
        return tweets;
    }

    @Nullable
    public Tweet getLastItem() {
        return tweets.isEmpty() ? null : tweets.get(tweets.size() - 1);
    }

    public void setTweets(@NonNull List<Tweet> tweets) {
        this.tweets.clear();
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    public void addTweets(@NonNull List<Tweet> tweets) {
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    class TweetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tweetView)
        TextView tweetView;
        @BindView(R.id.userImageView)
        ImageView userImageView;
        public TweetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tweetView.setMovementMethod(TouchableMovementMethod.getInstance());
        }

    }

    public interface TweetClickListener{
        void onClickScreenName(String clickedText);
    }
}
