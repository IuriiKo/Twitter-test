package com.kushyk.twittertest.util;

import android.content.Context;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

/**
 * Created by xaocu on 19.03.2017.
 */

public class LinkedTextUtil {

    public static Link getTweetLink(Link.OnClickListener listener) {
        return new Link(PatternUtil.TWEETER_SCREEN_NAME_PATTERN)
                    .setOnClickListener(listener);
    }

    public static CharSequence getLinkedText(Context context, String text, Link link) {
        return LinkBuilder.from(context, text)
                .addLink(link)
                .build();
    }

}
