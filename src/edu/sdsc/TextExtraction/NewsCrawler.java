package edu.sdsc.TextExtraction;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsCrawler {
	static final int TIMEOUT = 10000;
	
	public String getContent(String url) {
		StringBuilder contentBuf = new StringBuilder();
		try {
			Document doc = Jsoup.connect(url.trim()).timeout(TIMEOUT).get();
			Elements paragraphs = doc.getElementById("articleContent").select("p");
			for(Element paragraph : paragraphs)
				contentBuf.append(paragraph.text().trim() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuf.toString();
	}
	
	public static void main(String[] args) {
		NewsCrawler nc = new NewsCrawler();
		String text = nc.getContent("http://paper.people.com.cn/rmrb/html/"
				+ "2018-02/01/nw.D110000renmrb_20180201_4-17.htm");
		Segmenter seg = new Segmenter();
		String tokens = seg.segment(text);
		System.out.println(tokens);
	}
}