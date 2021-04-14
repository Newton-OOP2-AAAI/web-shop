package org.newton.webshop.rest;

import org.newton.webshop.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Trend;
import twitter4j.TwitterException;

import java.util.List;


@RestController
@RequestMapping("/twitter")
public class TwitterController {
    private final TwitterService twitterService;

    @Autowired
    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    /**
     * Get trends from chosen location (woeid)
     *
     * @param woeid
     * @return top ten trends for given location
     * @throws TwitterException
     */
    @GetMapping("/trends/{woeid}")
    public List<Trend> getTrendsByWoeId(@PathVariable int woeid) throws TwitterException {
        return TwitterService.twitterTrends(woeid);
    }


    /**
     * Get global twitter trends
     *
     * @return top ten trending globally
     * @throws TwitterException
     */
    @GetMapping("/trends/global")
    public List<Trend> getGlobalTrends() throws TwitterException {
        int globalWoeid = 1;
        return TwitterService.twitterTrends(globalWoeid);
    }

    /**
     * Get twitter trends from uk
     *
     * @return top ten trending in the uk
     * @throws TwitterException
     */
    @GetMapping("/trends/uk")
    public List<Trend> getUkTrends() throws TwitterException {
        int ukWoeid = 23424975;

        return TwitterService.twitterTrends(ukWoeid);
    }
}

