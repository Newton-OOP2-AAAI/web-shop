package org.newton.webshop.services;

import org.newton.webshop.repositories.TwitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Component
public class TwitterService {
    private final TwitterRepository twitterRepository;


    @Autowired
    public TwitterService(TwitterRepository twitterRepository) {
        this.twitterRepository = twitterRepository;
    }

    public static List<Trend> twitterTrends(int woeid) throws TwitterException, IOException {
        // create a reader object on the properties file
        FileReader reader = null;
        try {
            reader = new FileReader("apikey.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // create properties object
        Properties p = new Properties();

        // Add a wrapper around reader object
        p.load(reader);

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(p.getProperty("consumerKey"))
                .setOAuthConsumerSecret(p.getProperty("consumerSecret"))
                .setOAuthAccessToken(p.getProperty("accessToken"))
                .setOAuthAccessTokenSecret(p.getProperty("accessTokenSecret"));
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Trends trends = twitter.getPlaceTrends(woeid);
        List<Trend> trendList = new ArrayList<>();
        int count = 0;
        for (
                Trend trend : trends.getTrends()) {
            if (count < 10) {
                count++;
                trendList.add(trend);
            }
        }
        return trendList;
    }
}

