package postingInfoX2; 

import org.jsoup.Jsoup;
import org.jsoup.Connection; 
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector.SelectorParseException; 

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager; 

public class FetchPostings4WorkNOLA { 
	final static String contentRootPat = "div > div > div.inside "; // root of contents
	final static String postingRootPat = "div.view-content > div.item-list > ul"; // root of postings
	final static String postingPat = "li"; // 

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
	public FetchPostings4WorkNOLA(String url, String destFolderName) { 
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
			Connection connection = Jsoup.connect(url); 
			connection.validateTLSCertificates(false); 
			doc = connection.get(); 
			contentRoot = doc.select(contentRootPat); 
		} catch (IOException ioe) { 
			System.err.println("Failed in accessing URL " + url); 
			ioe.printStackTrace(); 
			System.exit(-2); 
		} 
	} 
	
	public FetchPostings4WorkNOLA(String url, File destFolder) { 
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
			Connection connection = Jsoup.connect(url); 
			connection.validateTLSCertificates(false); 
			doc = connection.get(); 
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
			String id = null; 
			Element title_href = Util.getElementBySelector(aEle, "span[class='views-field views-field-title'] > span > a"); 
			String title = title_href.text(); 
			String href = title_href.attr("href"); 
			href = "https://" + sourceHost + href; 
			System.out.println(href); 
			String dateStr = Util.getTextBySelector(aEle, "span[class='views-field views-field-created'] > span"); 
			String company = Util.getTextBySelector(aEle, "span[class='views-field views-field-field-job-employer'] > span > a"); 
			String summary = Util.getTextBySelector(aEle, "span[class='views-field views-field-body'] > p.field-content"); 
			PostingInfo info = new PostingInfo(title, id, href, company, null, dateStr, summary); 
			postingList.add(info); 
		}
		return postingList; 
	}
	
	public File getDestFolder() {
		return destFolder;
	}
	
	void download1HTML(String urlStr, File destFile) throws Exception { 
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };
 
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
 
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
 
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        
		try { 
			URL url = new URL(urlStr); 
			URLConnection conn = url.openConnection(); 
			InputStream is = conn.getInputStream(); 
			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(is)));
			PrintWriter pw = new PrintWriter(destFile); 
			String inputLine; 
			while ((inputLine = br.readLine()) != null) {
				pw.println(inputLine);
			} 
			br.close();
			pw.close();
		} catch (MalformedURLException me) {
			System.err.println("download1HTML had MalformedURLException: " + me);
		} catch (IOException ioe) {
			System.err.println("download1HTML had IOException: " + ioe);
		}
	} 
	
	void downloadHTMLs(ArrayList<PostingInfo> postings, File destDir) { 
		if (!Util.verifyUsableDir(destDir)) return; 
		String pn = null; 
		int seqNum = 0; 
		for (PostingInfo pi : postings) { 
			try { 
				File destFile = null; 
				if (pi.getId() != null) destFile = new File(destDir, pi.getId() + ".html"); 
				else destFile = new File(destDir, "tempID" + seqNum + ".html"); 
				download1HTML(pi.getHref(), destFile); 
				seqNum++; 
				System.out.println("Downloaded posting: " + seqNum + ": " + pi.getHref());
				Thread.sleep(5000);
			} catch (InterruptedException itre) {
				System.err.println("Sleep interrupted: " + itre);
			} catch (Exception e) { 
				System.err.println("go on ...");
			}
		}
		System.out.println(seqNum + " job postings were downloaded.");
	} 
	
	public static void main(String[] args) { 
		if (args.length == 3) { 
			Integer.parseInt(args[2]); 
		} else if (args.length != 2) { 
			System.err.println("usage: java FetchPostings url destFolder"); 
			System.exit(-1);
		}
		FetchPostings4WorkNOLA fetcher = new FetchPostings4WorkNOLA(args[0], args[1]); 
		System.out.println("The posting pages are in folder " + args[1]); 
		ArrayList<PostingInfo> postings = fetcher.getPostingList(postingRootPat, postingPat); 
		for (PostingInfo posting : postings) { 
			System.out.println(posting);
		} 
		fetcher.downloadHTMLs(postings, fetcher.getDestFolder());
		System.out.println("FetchPostings: Done.");
	}
}
