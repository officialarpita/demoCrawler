package com.wipro.demo.crawler.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerSearch {
	
	final static Logger logger = Logger.getLogger(CrawlerSearch.class);
	
	private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<>();
    public boolean crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            if(connection.response().statusCode() != 200) // 200 is the HTTP OK status code
            {
                logger.info("Could not access URL. Skipping this url : " + url);
                return false;
            }
            else
            {
                logger.info("validating web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                logger.error("HTML page not found");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            for(Element link : linksOnPage)
            {
                this.links.add(link.absUrl("href"));
            }
            return true;
        }
        catch(IOException | IllegalArgumentException e)
        {
        	logger.error("IO Exception found.Error message: ", e);
            return false;
        }
    }

    
    public List<String> getLinks()
    {
        return this.links;
    }

    
    
    

}
