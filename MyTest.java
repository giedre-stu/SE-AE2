import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

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

		String crawlStorageFolder = "data/";

		config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setIncludeBinaryContentInCrawling(false);
		config.setMaxDepthOfCrawling(-1); 

		pageFetcher = new PageFetcher(config);                            
		robotstxtConfig = new RobotstxtConfig();
		robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
	}


	/** 
	 * 1) what aspect of the code you are covering (incl. class name) and why
	 * 2) what inputs/outputs you are testing and why
	 * 3) what error you think this demonstrates. 
	 */
	@Test 
	public void hasUpperCase() 
	{
		String testString = "Tom Sawyer";

		HtmlParseData testHtmlParseData = new HtmlParseData();
		testHtmlParseData.setHtml(testString);
		assertEquals(testString,testHtmlParseData);
	}

	/** 
	 * 1) what aspect of the code you are covering (incl. class name) and why
	 * 2) what inputs/outputs you are testing and why
	 * 3) what error you think this demonstrates. 
	 */		
	@Test
	public void noOutgoingLinksLimitError() 
	{
		config.setMaxOutgoingLinksToFollow(MAX_LINKS);
		assertEquals(config.getMaxOutgoingLinksToFollow(), MAX_LINKS);
	}

	/** 
	 * 1) what aspect of the code you are covering (incl. class name) and why
	 * 2) what inputs/outputs you are testing and why
	 * 3) what error you think this demonstrates. 
	 */
	@Test
	public void noForbiddenLinks()
	{
		boolean noForbiddenLinks = true;
		String forbiddenLink = "http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2private/IDA.html";
		try {
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
			controller.addSeed(forbiddenLink);    
			controller.start(MyCrawler.class, 1); 
			datas = controller.getCrawlersLocalData();

			if (datas.size()!=0)
			{
				noForbiddenLinks = false;
			}
		}
		catch (Exception e) {
			System.out.println("Unable to extract page data.");
		}

		assertTrue(noForbiddenLinks);
	}


	/** 
	 * 1) what aspect of the code you are covering (incl. class name) and why
	 * 2) what inputs/outputs you are testing and why
	 * 3) what error you think this demonstrates. 
	 */
	@Test
	public void dataIsCorrect() 
	{
		//		
		//			boolean dataIsCorrect = true; 
		//			
		//			for (Object data : datas) 
		//			{
		//				@SuppressWarnings("unchecked")
		//				ArrayList<Page> pages = (ArrayList<Page>) data;
		//				
		//				for (Page page: pages)
		//				{	
		//					if (page.getParseData() instanceof TextParseData) {
		//						
		//						
		//						
		//					}
		//				}
		//			}
		//			
		//			assertTrue(dataIsCorrect);

	}
} 
