package com.pertxt;

/**
 * This is the pre-alpha version of PerTxT
 */

import com.pertxt.dataType.Document;
import com.pertxt.dataType.Sentence;
import com.pertxt.normalizer.Normalizer;
import com.pertxt.posTagger.POSTagger;
import com.pertxt.tokenizer.SentenceTokenizer;
import com.pertxt.tokenizer.WordTokenizer;

public class Processor {

  public static void main(String[] args) {

    Normalizer normalizer = new Normalizer();
    WordTokenizer wordTokenizer = new WordTokenizer();
    POSTagger posTagger = new POSTagger();

    System.out.println("PerTxT: Pre-alpha version!");
    String inSentence;
    while (true) {
      System.out.println("Enter your sentence: ");
      java.util.Scanner scanner = new java.util.Scanner(System.in);
      inSentence = scanner.nextLine();
      Sentence sentence = new Sentence(inSentence);

      if (inSentence.length() == 0) {
        break;
      }
      normalizer.process(sentence);
      System.out.println("Normalizer: Your sentence is: " + sentence.toString());

      SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
      Document document = sentenceTokenizer.process(sentence);
      System.out.println("Number of sentences: " + document.size());

      for (int i = 0; i < document.size(); i++) {
        wordTokenizer.process(document.get(i));
        System.out.println("Sentence number " + (i + 1) + " is: " + document.get(i).toString());
        posTagger.process(document.get(i));
        System.out
            .println("Tagged sentence " + (i + 1) + " is: " + document.get(i).toStringWordPOS("-"));
      }

      wordTokenizer.process(sentence);
      System.out.println("WordTokenizer: Your sentence is: " + sentence.toString());
    }
  }
}

