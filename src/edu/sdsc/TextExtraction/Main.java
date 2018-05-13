package edu.sdsc.TextExtraction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import edu.sdsc.Utils.PostgreDataIngestor;

public class Main {
	final static String UPDATE = "UPDATE newschina SET content = '%s' WHERE id"
			+ " = %s;";
	
	public static void main(String[] args) {
		try(Scanner sc = new Scanner(new FileReader(args[0]))) {
			PostgreDataIngestor pdi = new PostgreDataIngestor();
			NewsCrawler crawler = new NewsCrawler();
			Connection conn = pdi.getConn("10.128.36.22/postgres", "postgres", 
					args[1]);
			Segmenter seg = new Segmenter();
			while(sc.hasNextLine()) {
				String[] cols = sc.nextLine().split("\t");
				String content = seg.segment(crawler.getContent(cols[2]));
				if(content == null)
					continue;
				pdi.query(conn, String.format(UPDATE, content, cols[0]));
				System.out.println(cols[0]);
			}
			conn.close();
		} catch (FileNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}