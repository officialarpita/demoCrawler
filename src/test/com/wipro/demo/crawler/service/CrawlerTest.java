package wipro.demo.crawler.service;

import com.wipro.demo.crawler.controller.WebCrawlerController;
import org.junit.Test;

import static org.junit.Assert.*;


public class CrawlerTest {

    @Test
    public void testGetIt() {
        WebCrawlerController webcrawlercontroller =new WebCrawlerController();
        String result=webcrawlercontroller.search("http://www.wiprodigital.com");
        System.out.println(result);
        assertTrue(result.contains("http://www.wiprodigital.com"));

    }

    @Test
    public void testNullUrl() {
        WebCrawlerController webcrawlercontroller =new WebCrawlerController();
        String result=webcrawlercontroller.search("");
        System.out.println(result);
        assertEquals("URL is empty. Please provide proper URL",result);

    }

    @Test
    public void testInvalidUrl() {
        WebCrawlerController webcrawlercontroller =new WebCrawlerController();
        String result=webcrawlercontroller.search("http://www.hrjeh.com/");
        System.out.println(result);
        assertEquals("Could not retrieve data from URL : http://www.hrjeh.com/",result);

    }

}