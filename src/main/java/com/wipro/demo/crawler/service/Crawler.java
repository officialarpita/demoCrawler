package com.wipro.demo.crawler.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class Crawler {

    final static Logger logger = Logger.getLogger(Crawler.class);
    private static final String MAX_PAGES_TO_SEARCH = "MAX_PAGES_TO_SEARCH";

    private static Properties prop;
    private Set<String> pagesVisited = new HashSet<>();
    private List<String> pagesToVisit = new LinkedList<>();

    /**
     * static block to read properties file.
     */
    static {
        try (InputStream is = Crawler.class.getResourceAsStream("/config.properties")) {
            logger.info("loading properties file");
            if (is != null) {
                prop = new Properties();
                prop.load(is);
                logger.info("properties file loaded successfully.");
            } else {
                logger.error("Could not find properties file.");
            }
        } catch (IOException e) {
            logger.error("Could not find properties file.", e);
        }
    }

    /**
     * search method will search for the URL by traversing different pages and
     * stores the URL in pagesVisited.Internally this will call nextUrl method for traversing
     * @param url web url example http://www.google.com
     * @return data
     */
    public String search(String url) {

        int maxPageToSearch = 2;  //default value if properties not found.
        if (getPropertyValue(MAX_PAGES_TO_SEARCH) != null) {
            maxPageToSearch = Integer.parseInt(getPropertyValue(MAX_PAGES_TO_SEARCH));
        }
        while (this.pagesVisited.size() < maxPageToSearch) {
            String currentUrl;
            final CrawlerSearch crawlersearch = new CrawlerSearch();
            if (this.pagesToVisit.isEmpty()) {
                currentUrl = url;
                this.pagesVisited.add(url);
            } else {
                currentUrl = this.nextUrl();
            }
            if(!crawlersearch.crawl(currentUrl)) {
                logger.info("Could not retrieve full data from URL : "+url+" -- Either could not access or not able valid web page. Received uncompleted response.");
                return "Could not retrieve data from URL : "+url;
            }
            this.pagesToVisit.addAll(crawlersearch.getLinks());
        }

        StringBuilder data = new StringBuilder("Here is web crawler output.....");
        for (String page : pagesVisited) {
            data.append("\n");
            data.append(page);
        }
        return data.toString();

    }

    public static String getPropertyValue(String key) {
        return prop.getProperty(key);
    }


    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }


}
