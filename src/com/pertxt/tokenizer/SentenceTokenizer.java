package com.pertxt.tokenizer;

import com.pertxt.dataType.Document;
import com.pertxt.io.Reader;
import com.pertxt.dataType.Sentence;

import java.util.List;

public class SentenceTokenizer {


  private final List<Character> sentenceBoundary;

  public SentenceTokenizer() {
    sentenceBoundary = new Reader()
        .readChars("resources/tokenizer/sentenceBoundary.txt");
  }

  public SentenceTokenizer(String sentenceBoundaryFile) {
    sentenceBoundary = new Reader().readChars(sentenceBoundaryFile);
  }


  private Document splitPunctuations(String document) {
    Document outDocs = new Document();

    StringBuilder out = new StringBuilder();
    for (String word : document.split(" ")) {
      if (word.length() > 0) {  //Ignore this! (It is useless!)
        for (int i = 0; i < word.length(); i++) {
          if (sentenceBoundary.contains(word.charAt(i))) {
            if (i == 0)//If it is the first character
            {
              out.append(word.charAt(i));
              //out.append(' ');
              outDocs.add(out.toString());
              out = new StringBuilder();
            } else if (i == word.length() - 1)//If it is the last character
            {
              out.append(' ');
              out.append(word.charAt(i));
              outDocs.add(out.toString());
              out = new StringBuilder();
            } else {
              if (word.substring(i - 1, i).replaceAll("[0-9a-zA-Z_/-]", " ")
                  .equals(" "))//Previous char is English char or digit char, dash, underline, etc.
              {
                out.append(word.charAt(i));
              } else {
                out.append(' ');
                out.append(word.charAt(i));
                outDocs.add(out.toString());
                out = new StringBuilder();
              }
            }
          } else {
            out.append(word.charAt(i));
            //out.append(word.substring(i, i + 1));
          }
        }
      } else//It is always false!
      {
        out.append(word);
      }
      out.append(' ');
    }
    if (out.toString().trim().length() > 0) {
      outDocs.add(out.toString());
    }

    return outDocs;
  }


  public Document process(Sentence sentence) {
    return process(sentence.toString());
  }

  public Document process(String sentence) {
    return splitPunctuations(sentence);
  }
}
