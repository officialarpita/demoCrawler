package com.wipro.demo.crawler.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.wipro.demo.crawler.service.Crawler;



@Path("searchurl")
public class WebCrawlerController {
	
	private final Crawler cw = new Crawler();
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String search(@QueryParam("url") String url) {

		if(url == null || "".equals(url.trim())) {
			return "URL is empty. Please provide proper URL";
		} else {
			return cw.search(url);
		}
    }

}
