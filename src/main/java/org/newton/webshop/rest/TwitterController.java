package org.newton.webshop.rest;

import org.newton.webshop.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Trend;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/twitter")
public class TwitterController {
    private final TwitterService twitterService;

    @Autowired
    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping("/trends/uk")
    public List<Trend> ukTrends() throws TwitterException, IOException {
        int ukWoeid = 23424975;
        return TwitterService.twitterTrends(ukWoeid);
    }

    @GetMapping("/trends/global")
    public List<Trend> globalTrends() throws TwitterException, IOException {
        int globalWoeid = 1;
        return TwitterService.twitterTrends(globalWoeid);
    }
}

