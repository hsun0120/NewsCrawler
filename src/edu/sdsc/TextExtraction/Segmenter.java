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
	private StanfordCoreNLP pipeline;

	public Segmenter() {
		Properties props = new Properties();
		try {
			props.load(IOUtils.readerFromString("chinese.properties"));
			this.pipeline = new StanfordCoreNLP(props);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String segment(String text) {
		StringBuilder tokens = new StringBuilder();
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
		return tokens.toString();
	}
}