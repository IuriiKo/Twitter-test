package com.kushyk.twittertest.util;

import java.util.regex.Pattern;

/**
 * Created by xaocu on 19.03.2017.
 */

public class PatternUtil {
    private static final String TWEETER_SCREEN_NAME = "@\\w{1,15}";
    public static final Pattern TWEETER_SCREEN_NAME_PATTERN = pattern(TWEETER_SCREEN_NAME);

    private static Pattern pattern(String pattern) {
        return Pattern.compile(pattern);
    }
}
