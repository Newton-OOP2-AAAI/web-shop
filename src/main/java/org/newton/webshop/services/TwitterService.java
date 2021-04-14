package org.newton.webshop.services;


import org.springframework.stereotype.Component;
import twitter4j.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component

public class TwitterService {

    private static TwitterFactory tf = new TwitterFactory();

    /**
     * Takes a "Where on earth id" (woeid) and returns top ten twitter trends for that location
     *
     * @param woeid =  either from URL or in boiler plate code in TwitterController
     * @return a list of the top ten twitter trends for the country/location specified by woeid
     * @throws TwitterException
     */
    public static List<Trend> twitterTrends(int woeid) throws TwitterException {

        Twitter twitter = tf.getInstance();
        Trends trends = twitter.getPlaceTrends(woeid);

        return Arrays.stream(trends.getTrends())
                .limit(10L)
                .collect(Collectors.toList());
    }
}
