package postingInfoX2;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Util {

	// TOC lines: div.mw-content-ltr#mw-Content-text > div.toc#toc > ul > li > a > span.toctext
	public final static String PAT_toc = "div.mw-content-ltr#mw-content-text > div.toc#toc > ul > li > a > span.toctext"; 

	static String normalize(String str) { 		// erase extra spaces 
		StringBuffer sb = new StringBuffer(str.trim()); 
		final String doubleSp = "  "; 
		int ind = sb.indexOf(doubleSp); 
		while (ind >= 0) {
			sb.deleteCharAt(ind); 
			ind = sb.indexOf(doubleSp); 
		} 
		return sb.toString(); 
	} 
	
	public static String sp2Underscore(String str) { 
		String fn = normalize(str); 
		StringBuffer sb = new StringBuffer(fn.trim()); 
		// remove comer 
		int k = sb.indexOf(","); 
		while (k > -1) {
			sb.deleteCharAt(k); 
			k = sb.indexOf(","); 
		} 
		// replace space by underscore 
		k = sb.indexOf(" ");  
		while (k > -1) { 
			sb.setCharAt(k, '_');
			k = sb.indexOf(" "); 
		} 
		String noSp = sb.toString(); 
		return noSp; 
	} 
	
	/*
	 * check if "dir" does not exist then create a folder, or if it is a
	 * directory then use it.
	 * 
	 * @return true if the condition meets above
	 */
	public static boolean verifyUsableDir(File dir) {
		boolean veri = true;
		if (!dir.exists()) {
			if (!dir.mkdir()) {
				veri = false;
				System.err.println("Failed in creating destination folder.");
				System.exit(-1);
			}
		} else {
			if (!dir.isDirectory()) {
				veri = false;
				System.err.println(dir + " is an existing file. Destination must be an existing or a new FOLDER name!");
				System.exit(0);
			}
		}
		return veri;
	}

	public static Element getElementBySelector(Element current, String selector) { 
		Elements eles = current.select(selector); 
		if (eles == null || eles.size() == 0) return null; 
		Element ele = eles.get(0); 
		return ele; 
	}
	
	public static String getTextBySelector(Element current, String selector) {
		Elements eles = current.select(selector); 
		if (eles == null || eles.size() == 0) return null; 
		Element ele = eles.get(0); 
		return ele.text(); 
	}

	public static void download1HTML(String urlStr, File destFile) throws Exception { 
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
	
	public static void downloadHTMLs(ArrayList<PostingInfo> postings, File destDir) { 
		if (!verifyUsableDir(destDir)) return; 
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

}
