package com.kushyk.twittertest.managers;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xaocu on 18.03.2017.
 */

public class TweetManager {
    TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
    StatusesService statusesService = twitterApiClient.getStatusesService();

    public Observable<List<Tweet>> getTimeLie(String screenName) {
        return Observable.create(tweetSubscribe -> {
            Call<List<Tweet>> call = statusesService.userTimeline(null, screenName, null, null, null, null, null, null, null);
            call.enqueue(new Callback<List<Tweet>>() {
                @Override
                public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            tweetSubscribe.onNext(response.body());
                            tweetSubscribe.onComplete();
                        } else {
                            tweetSubscribe.onError(new Throwable("getTimeLie() response.body = null"));
                        }
                    } else {
                        tweetSubscribe.onError(new Throwable("getTimeLie() response error: " + response.errorBody().toString()));
                    }
                }

                @Override
                public void onFailure(Call<List<Tweet>> call, Throwable t) {
                    tweetSubscribe.onError(t);
                }
            });
        });

    }
}
