import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

class MyTest {

	static CrawlConfig config;
	static PageFetcher pageFetcher;
	static RobotstxtConfig robotstxtConfig;
	static RobotstxtServer robotstxtServer;
	static CrawlController controller;
	static List<Object> datas;
	
	private static final int MAX_LINKS = 5000;

	@BeforeEach
	public void setup() throws Exception {

		int numberOfCrawlers = 1; 
		String crawlStorageFolder = "data/";

		config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setIncludeBinaryContentInCrawling(false);
		config.setMaxDepthOfCrawling(-1); 
		config.setMaxOutgoingLinksToFollow(MAX_LINKS);

		pageFetcher = new PageFetcher(config);                            
		robotstxtConfig = new RobotstxtConfig();
		robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

		controller = new CrawlController(config, pageFetcher, robotstxtServer);
		controller.addSeed("http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/Machine_learning.html");    
		controller.start(MyCrawler.class, numberOfCrawlers); 

		datas = controller.getCrawlersLocalData();
	}

	//	@Test 
	//	public void hasUpperCase() 
	//	{
	//		boolean hasUpperCase = false;
	//		boolean originalHasUpperCase = false;
	//		
	//		
	//		
	//		
	//		assertTrue(hasUpperCase);
	//	}

		@Test
	public void noOutgoingLinksLimitError() 
		{
			boolean correctLimit = true;
			
			if (config.getMaxOutgoingLinksToFollow()!=MAX_LINKS)
			{
				correctLimit=false;
			}
			
			assertTrue(correctLimit);
		}

	@Test
	public void noForbiddenLinks()
	{
		boolean noForbiddenLinks = true;
		MyCrawler mC = new MyCrawler();

		String link = "http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/";
		String URL;

		for (Object data : datas) 
		{
			try {
				@SuppressWarnings("unchecked")
				ArrayList<Page> pages = (ArrayList<Page>) data;

				for (Page page: pages)
				{	
					URL = page.getWebURL().toString();
					
//					if (!mC.shouldVisit(page, page.getWebURL()))
//					{
//						noForbiddenLinks = false;
//					}

					if (!URL.startsWith(link))
					{
						noForbiddenLinks = false;
					}	
				}
			} catch (ClassCastException e) {
				System.out.println("Unable to extract page data.");
			}
		}

		assertTrue(noForbiddenLinks);
	}

	//	@Test
	//	public void dataIsCorrect() 
	//	{
	//
	//	}
} 
