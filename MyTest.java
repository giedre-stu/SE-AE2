import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;
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
		assertEquals(testString,testHtmlParseData.getHtml());
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
		assertEquals(MAX_LINKS, config.getMaxOutgoingLinksToFollow());
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
		String message = "";
		String forbiddenLink = "http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2private/IDA.html";
		try {
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
			controller.addSeed(forbiddenLink);    
			controller.start(MyCrawler.class, 1); 
			datas = controller.getCrawlersLocalData(); 

			if (!datas.isEmpty())
			{
				noForbiddenLinks = false;
				message = "Forbidden URL: " + forbiddenLink;
			}
		}
		catch (Exception e) {
			System.out.println("Unable to extract page data.");
		}

		assertTrue(noForbiddenLinks, message);
	}


	/** 
	 * 1) what aspect of the code you are covering (incl. class name) and why
	 * 2) what inputs/outputs you are testing and why
	 * 3) what error you think this demonstrates. 
	 */
	@Test
	public void dataIsCorrect() 
	{
		String testString = "01011234";

		TextParseData testParseData = new TextParseData();
		testParseData.setTextContent(testString);
		assertEquals(testString,testParseData.getTextContent());
	}
} 
