package postingInfoX2;

import org.jsoup.Jsoup;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class Posting1PageExtractor {
	private File dFolder;

	final static String PAT_seealso = "h2 > span.mw-headline#See_also";
	final static String PAT_externalLink = "h2 > span.mw-headline#External_link"; 
	final static String PAT_references = "h2 > span.mw-headline#References"; 
	final static String PAT_history = "h2:has(span.mw-headline#History)";
	final static String PAT_display = "p, h2 > span, h3 > span, ul > li, ol > li"; 
	final static String PAT_href = "ul > li > a[href]"; 
	final static String PAT_contentroot = "div.mw-content-ltr#mw-content-text"; 

	public Posting1PageExtractor(String destFolderName) {
		dFolder = new File(destFolderName);
		if (!Util.verifyUsableDir(dFolder)) {
			System.err.println("Choose a usable directory (folder)."); 
			System.exit(0); 
		}
	}

	public Posting1PageExtractor(File destFolder) {
		this.dFolder = destFolder; 
		if (!Util.verifyUsableDir(dFolder)) {
			System.err.println("Choose a usable directory (folder)."); 
			System.exit(0); 
		}
	} 
	
	int extract(String url, String root) { 
		if (url == null) return -2; 
		PrintWriter pw; 
		// make a file name using the last fragment string 
		String fn = null; 
		int ind = url.lastIndexOf("/");
		if (ind >= 0)
			fn = url.substring(ind, url.length());
		else {
			System.err.println(url + " does not contain a title."); 
			fn = "File" + (int)Math.random()*10000; 
		} 
		try {
			pw = new PrintWriter(new FileWriter(new File(dFolder, fn))); 
		} catch (IOException ioe) { 
			System.err.println("Failed in creating destination file print writer."); 
			return -1; 
		} 
		// fetch the Wikipedia page 
		System.out.println("WP1PageExtractor: url=" + url);
		Document doc; 
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException ioe) { 
			System.err.println("Failed in accessing URL " + url); 
			pw.close();
			return 0; 
		}
		Elements contentRoot = doc.select(root); 
		if (contentRoot.size() == 0) return 0; 
		Element content = contentRoot.get(0);
		int cnt = 0;
		Element nextSib = content.children().get(0);
		String nextTag = nextSib.tagName();
		while (nextSib != null) { 
			nextTag = nextSib.tagName(); 
			// check if reached to See_also section. if so, terminate
			Elements check = nextSib.select(PAT_seealso);
			Elements check2 = nextSib.select(PAT_externalLink); 
			Elements check3 = nextSib.select(PAT_references); 
			if (check != null && check.size() > 0 || check2 != null && check2.size() > 0 
					|| check3 != null && check3.size() > 0) {
				break;
			}
			// check if reached to History section. if so, skip
			check = nextSib.select(PAT_history);
			if (check != null && check.size() > 0) {
				nextSib = check.get(0);
				nextTag = "";
				if (nextSib != null) {
					do {
						nextTag = nextSib.tagName();
						nextSib = nextSib.nextElementSibling();
					} while (!nextTag.equals("h2") && nextTag != null);
					continue;
				}
			}
			// display contents: h2, h3, p, ul, ol only
			if (nextTag.equals("p") || nextTag.equals("h2") || nextTag.equals("h3")
					|| nextTag.equals("ul") || nextTag.equals("ol")) {
				Elements texts = nextSib.select(PAT_display);
				for (Element line : texts) {
					pw.println(line.text());
//					System.out.println(line.text());
					cnt++;
				}
			} 
			nextSib = nextSib.nextElementSibling(); 
		}
		pw.close();
		return cnt;
	}

	public static void main(String[] args) {
		// URL: https://en.wikipedia.org/wiki/Heliograph
		Validate.isTrue(args.length == 2, "usage: java WP1PagetExtractor source-url destination-folder ");
		System.out.println("Extracting " + args[0]);
		Posting1PageExtractor extractor = new Posting1PageExtractor(args[1]);
		int nlines = extractor.extract(args[0], PAT_contentroot);
		System.out.println("Contents from " + args[0] + " has been extracted into files in folder " 
							+ args[1] + ": " + nlines + " lines.");
	}
}
