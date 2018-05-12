package edu.sdsc.TextExtraction;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Segmenter {
	public static String segment(String text) {
		StringBuilder tokens = new StringBuilder();
		Properties props = new Properties();
		try {
			props.load(IOUtils.readerFromString("chinese.properties"));
			StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
			Annotation annot = new Annotation(text);
			pipeline.annotate(annot);

			List<CoreMap> sentences = 
					annot.get(CoreAnnotations.SentencesAnnotation.class);
			for (CoreMap sentence : sentences){
				for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)){
					String word = token.get(CoreAnnotations.TextAnnotation.class);
					tokens.append(word + " ");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tokens.toString();
	}
}