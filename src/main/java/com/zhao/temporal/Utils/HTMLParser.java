package com.zhao.temporal.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
//	import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
//	import org.jsoup.select.NodeVisitor;

public class HTMLParser {

	public static List<String> getPfromHTML(String historicalPagePath, int minLength) throws IOException {
		
		List<String> paragraphs = new ArrayList<String>();
		File input = new File(historicalPagePath);
		Document doc = Jsoup.parse(input, "UTF-8");
		
		//	Elements phase = doc.select("p");
			Elements phase = doc.getElementsByTag("p");
		//	Elements phase = doc.select("(text)");
		//	Elements phase = doc.getElementsContainingOwnText(" ");
		
		for (Element link : phase) {
		  String linkText = link.text();
		  if (linkText.length() >= minLength){
			  //	System.out.println(linkText);
			  //	System.out.println();
			  paragraphs.add(linkText);
		  }
		}		
		return paragraphs;
	}
	
	public static List<String> getPfromHTML(File file_HistoricalPage, int minLength) throws IOException {
		List<String> paragraphs = new ArrayList<String>();
		
		Document doc = Jsoup.parse(file_HistoricalPage, "UTF-8");

		//Elements phase = doc.select("p");
		Elements phase = doc.getElementsByTag("p");
		
		for (Element link : phase) {
		  String linkText = link.text();
		  if (linkText.length() >= minLength){
			  //	System.out.println(linkText);
			  //	System.out.println();
			  paragraphs.add(linkText);
		  }
		}		
		return paragraphs;
	}
	
	public static String extractText(File file) throws IOException {
	    
		StringBuilder sb = new StringBuilder();
	    
	    InputStream fis = new FileInputStream(file);
	    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	    String line;
	    
	    while ( (line=br.readLine()) != null) {
	    	sb.append(line);
	    }
	    String textOnly = Jsoup.parse(sb.toString()).text();
	    
	    br.close();
	    br = null;
	    fis = null;
	    
	    return textOnly;
	  }
	
	public static String br2nl(String html) {
	    if(html==null)
	        return html;
	    Document document = Jsoup.parse(html);
	    document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
	    document.select("br").append("\\n");
	    document.select("p").prepend("\\n\\n");
	    String s = document.html().replaceAll("\\\\n", "\n");
	    return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
	}
	
	public static String br2nl(File htmlFile) throws IOException {

	    Document document = Jsoup.parse(htmlFile, "UTF-8");
	    document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
	    document.select("br").append("\\n");
	    document.select("p").prepend("\\n\\n");
	    String s = document.html().replaceAll("\\\\n", "\n");
	    return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
	}
	
	public static List<String> file2Para(File htmlFile) throws IOException {
		
		List<String> list_Strings = new ArrayList<String>();
		//	String file2String = br2nl(htmlFile);
		//	TODO filter some elements we do not need, like &nbsp;
		return list_Strings;
	}

    public static void main( String[] args ) throws IOException {
    	//getPfromHTML("/Users/yuezhao/GoogleDrive/HistoricalPagesForClueweb12/downloadPages5/ffd3c78233e9259fc1f5edbf96ba486c/201211110446.html", 50);
    	List<String> testSrings = getPfromHTML("/Users/yuezhao/Desktop/clueweb12-0000wb-31-12737.html", 50);
    	for (String testString: testSrings)
    		System.out.println(testString);
    	System.out.println("----------------------------------------");
    	File testFile = new File("/Users/yuezhao/Desktop/clueweb12-0000wb-31-12737.html");
    	String br2blString = br2nl(testFile);
    	System.out.println(br2blString);
    	
//		File input = new File("/Users/yuezhao/Desktop/clueweb12-0000wb-31-12737.html");
//		Document doc = Jsoup.parse(input, "UTF-8");
//		doc.traverse(new NodeVisitor() {
//		    public void head(Node node, int depth) {
//		        System.out.println("Entering tag: " + node.nodeName());
//		        System.out.println("Depth: " + depth);
//		    }
//		    public void tail(Node node, int depth) {
//		        System.out.println("Exiting tag: " + node.nodeName());
//		        System.out.println("Depth: " + depth);
//		    }
//		});
    }

}
