package com.pertxt.normalizer;

import com.pertxt.dataType.Sentence;
import com.pertxt.io.Reader;
import java.util.List;
import java.util.Map;

public class Normalizer {

  private final Map<Character, Character> charSet;
  private final List<Character> validCharSet;
  private String currentPath = System.getProperty("user.dir");

  public Normalizer() {
    charSet = new Reader().readMap(currentPath + "/resources/normalizer/replaceChars.txt");
    validCharSet = new Reader().readChars(currentPath + "/resources/normalizer/validChars.txt");
  }

  public Normalizer(String charSetFile, String validCharFile) {
    charSet = new Reader().readMap(charSetFile);
    validCharSet = new Reader().readChars(validCharFile);
  }


  private void replaceCharSet(Sentence sentence) {
    for (int i = 0; i < sentence.size(); i++) {
      StringBuilder result = new StringBuilder();
      String tmp = sentence.get(i).toString();
      for (int j = 0; j < tmp.length(); j++) {
        if (charSet.containsKey(tmp.charAt(j))) {
          result.append(charSet.get(tmp.charAt(j)));
        } else {
          result.append(tmp.charAt(j));
        }
      }
      sentence.replaceToken(i, result.toString());
    }

  }

  private void invalidChar(Sentence sentence) {
    for (int i = 0; i < sentence.size(); i++) {
      StringBuilder result = new StringBuilder();
      String tmp = sentence.get(i).toString();
      for (int j = 0; j < tmp.length(); j++) {
        if (validCharSet.contains(tmp.charAt(j))) {
          result.append(tmp.charAt(j));
        } else {
          //Do nothing!
        }
      }
      sentence.replaceToken(i, result.toString());
    }
  }

  private void removeEmptyWords(Sentence sentence) {
    for (int i = 0; i < sentence.size(); i++) {
      if (sentence.get(i).toString().trim().equals("")) {
        //TODO: Check this
        sentence.remove(i);
        i--;
      }
    }
  }

  public void process(Sentence sentence) {
    replaceCharSet(sentence);
    invalidChar(sentence);
    //pullRegex(sentence);
    //splitPunctuation(sentence);

    //Add semi-space here!
    //new SemiSpace().process(sentence);

    removeEmptyWords(sentence);
    //pushRegex(sentence);
  }

  public String process(String sentence) {
    Sentence tmpSentence = new Sentence(sentence);
    process(tmpSentence);
    return tmpSentence.toString();
  }
}
