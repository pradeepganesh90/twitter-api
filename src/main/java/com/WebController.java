package com;


import java.util.List;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/")
public class WebController {

	private static final String PATH = "redirect:/connect/twitter";
	
	private Twitter twitter;

	private ConnectionRepository connectionRepository;

	@Inject
	public WebController(Twitter twitter, ConnectionRepository connectionRepository) {
		this.twitter = twitter;
		this.connectionRepository = connectionRepository;
	}

	@RequestMapping(method=RequestMethod.GET)
	public String helloTwitter(Model model) {
		if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
			return PATH;
		}
		return "twitterConnected";
	}

	@RequestMapping(value="api/list-tweets/userName/{uname}", method = RequestMethod.GET)
	@ResponseBody
	public String getUsertimelineTweets(@PathVariable("uname") String uname, @RequestParam(value = "count", required = false, defaultValue = "20") Integer count) throws JsonProcessingException {

		if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
			return PATH;
		}
		ObjectMapper mapper = new ObjectMapper();        
		List<Tweet> tweets = twitter.timelineOperations().getUserTimeline(uname, count);
		return mapper.writeValueAsString(tweets);
	}
	
	@RequestMapping(value="/api/tweet-stats/userName/{uname}", method = RequestMethod.GET)
	@ResponseBody
	public String getTweetsStats(@PathVariable("uname") String uname, @RequestParam(value = "count", required = false, defaultValue = "20") Integer count) throws JsonProcessingException {

		if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
			return PATH;
		}
		ObjectMapper mapper = new ObjectMapper();        
		List<Tweet> tweets = twitter.timelineOperations().getUserTimeline(uname, count);
		return mapper.writeValueAsString(tweets);
	}

}
