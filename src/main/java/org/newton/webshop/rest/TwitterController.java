package org.newton.webshop.rest;

import org.newton.webshop.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Trend;
import twitter4j.TwitterException;

import java.util.List;


@RestController
@RequestMapping("/twitter")
public class TwitterController {
    private static final int GLOBAL_WOE_ID = 1;
    private static final int UK_WOE_ID = 23424975;
    private final TwitterService twitterService;

    @Autowired
    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    /**
     * Get trends by chosen location by WOEID (Where On Earth IDentifier, https://en.wikipedia.org/wiki/WOEID)
     *
     * @param woeId Where On Earth IDentifier.
     * @return top ten trends for given location
     * @throws TwitterException
     */
    @GetMapping(path = "/trends", params = {"woe_id"})
    public List<Trend> getTrendsByWoeId(@RequestParam(name = "woe_id") int woeId) throws TwitterException {
        return twitterService.twitterTrends(woeId);
    }


    /**
     * Get global twitter trends
     *
     * @return top ten trending globally
     * @throws TwitterException
     */
    @GetMapping("/trends/global")
    public List<Trend> getGlobalTrends() throws TwitterException {
        return twitterService.twitterTrends(GLOBAL_WOE_ID);
    }

    /**
     * Get twitter trends in UK
     *
     * @return top ten trending in the uk
     * @throws TwitterException
     */
    @GetMapping("/trends/uk")
    public List<Trend> getUkTrends() throws TwitterException {
        return twitterService.twitterTrends(UK_WOE_ID);
    }
}

