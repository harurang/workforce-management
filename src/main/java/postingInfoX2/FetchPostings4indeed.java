package postingInfoX2; 

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector.SelectorParseException; 

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList; 

public class FetchPostings4indeed { 
	final static String contentRootPat = "table#resultsBody table#pageContent"; // root of contents
	final static String postingRootPat = "table > tbody > tr > td#resultsCol"; // root of postings
	final static String postingPat = "div[data-tn-component='organicJob']"; // 

	private String destFolderName; 
	private File destFolder; 
	private String sourceHost; 
	private Document doc; 
	private Elements contentRoot; 
	
	/*
	 * Constructor
	 * @url - URL of the job posting page from indeed.com  
	 * @destFolderName - the destination subdirectory  
	 */
	public FetchPostings4indeed(String url, String destFolderName) { 
		this.destFolderName = destFolderName; 	
		this.destFolder = new File(destFolderName); 
		if (!Util.verifyUsableDir(destFolder)) {
			System.err.println("Choose a usable directory (folder)."); 
			System.exit(0); 
		} 
		try { 
			this.sourceHost = new URL(url).getHost(); 
		} catch (MalformedURLException mfu) {
			System.err.println("Bad URL."); 
			return;
		}
		try {
			doc = Jsoup.connect(url).get(); 
			contentRoot = doc.select(contentRootPat); 
		} catch (IOException ioe) { 
			System.err.println("Failed in accessing URL " + url); 
			System.exit(-2); 
		} 
	} 
	
	public FetchPostings4indeed(String url, File destFolder) { 
		this.destFolderName = destFolder.getName(); 	
		this.destFolder = destFolder; 
		if (!Util.verifyUsableDir(destFolder)) {
			System.err.println("The directory (folder) is not usable."); 
			System.exit(0); 
		} 
		try { 
			this.sourceHost = new URL(url).getHost(); 
		} catch (MalformedURLException mfu) {
			System.err.println("Bad URL."); 
			System.exit(-1);
		}
		try {
			doc = Jsoup.connect(url).get(); 
			contentRoot = doc.select(contentRootPat); 
		} catch (IOException ioe) { 
			System.err.println("Failed in accessing URL " + url); 
			System.exit(-2); 
		}
	} 
	
	/*
	 * To collect the hyperlinks from the subtree of contentRootSelector 
	 * @return ArrayList<Title_ahref> - each item is made of title and href of a hyperlink 
	 */
	public ArrayList<PostingInfo> getPostingList(String postingRootSelector, String hyperLinkSelector) { 
		ArrayList<PostingInfo> postingList = new ArrayList<PostingInfo>(); 
		Elements postingRoot = contentRoot.select(postingRootSelector); 
		if (postingRoot == null) return null; 
		Elements postings = postingRoot.select(hyperLinkSelector); 
		if (postings == null) return null; 
		for (Element aEle : postings) { 
			String id = aEle.attr("id"); 
			Element h2_a = Util.getElementBySelector(aEle, "h2.jobtitle > a"); 
			String title = h2_a.attr("title"); 
			String href = h2_a.attr("href"); 
			href = "https://" + sourceHost + href; 
			System.out.println(href); 
			String postingText = h2_a.text(); 
			if (!title.equals(postingText)) System.out.println("warning: " + title + " != " + postingText); 
			String company = Util.getTextBySelector(aEle, "span.company > span[itemprop='name']"); 
			String location = Util.getTextBySelector(aEle, "span[itemprop='jobLocation'] > span.location > span[itemprop='addressLocality'"); 
			String dateStr = Util.getTextBySelector(aEle, "table > tbody > tr > td.snip > div.result-link-bar-container span.date"); 
			String summary = Util.getTextBySelector(aEle, "table > tbody > tr > td.snip > div > span.summary"); 
			PostingInfo info = new PostingInfo(title, id, href, company, location, dateStr, summary); 
			postingList.add(info); 
		}
		return postingList; 
	}
	
	public File getDestFolder() {
		return destFolder;
	}

	public static void main(String[] args) { 
		if (args.length == 3) { 
			Integer.parseInt(args[2]); 
		} else if (args.length != 2) { 
			System.err.println("usage: java FetchPostings url destFolder"); 
			System.exit(-1);
		}
		FetchPostings4indeed fetcher = new FetchPostings4indeed(args[0], args[1]); 
		System.out.println("The posting pages are in folder " + args[1]); 
		ArrayList<PostingInfo> postings = fetcher.getPostingList(postingRootPat, postingPat); 
		for (PostingInfo posting : postings) { 
			System.out.println(posting);
		} 
		Util.downloadHTMLs(postings, fetcher.getDestFolder());
		System.out.println("FetchPostings: Done.");
	}
}
