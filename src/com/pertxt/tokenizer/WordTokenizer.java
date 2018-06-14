package com.pertxt.tokenizer;

import java.util.HashSet;
import com.pertxt.datatype.Document;
import com.pertxt.datatype.Token;
import com.pertxt.io.Reader;
import com.pertxt.datatype.Sentence;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordTokenizer {

  private String currentPath = System.getProperty("user.dir");
  private final List<String> topRegex;
  private List<String> tokenizeExceptionList;
  private final List<Character> puncs;
  private List<String> verbPrefixRegSplitting;
  private List<String> verbSuffixRegSplitting;
  private List<String> nonVerbPrefixRegSplitting;
  private List<String> nonVerbSuffixRegSplitting;
  private List<String> verbPrefixRegMerging;
  private List<String> verbSuffixRegMerging;
  private List<String> nonVerbPrefixRegMerging;
  private List<String> nonVerbSuffixRegMerging;
  private List<String> verbDictionary;
  private List<String> nonVerbDictionary;
  private final HashSet<String> nonVerbDictionaryHash = new HashSet<>();
  private final HashSet<String> verbDictionaryHash = new HashSet<>();
  private final Character semispace = '\u200C';

  public WordTokenizer() {
    topRegex = new Reader().readConfigFile(currentPath+"/resources/normalizer/topRegex.txt");
    tokenizeExceptionList = new Reader().readConfigFile(currentPath+"/resources/tokenizer/tokenizeExceptionList.txt");
    puncs = new Reader().readChars(currentPath+"/resources/tokenizer/punctuations.txt");
    verbPrefixRegSplitting = new Reader()
        .readConfigFile(currentPath+"/resources/tokenizer/verbPrefixRegSplitting.txt");
    verbSuffixRegSplitting = new Reader()
        .readConfigFile(currentPath+"/resources/tokenizer/verbSuffixRegSplitting.txt");
    nonVerbPrefixRegSplitting = new Reader()
        .readConfigFile(currentPath+"/resources/tokenizer/nonVerbPrefixRegSplitting.txt");
    nonVerbSuffixRegSplitting = new Reader()
        .readConfigFile(currentPath+"/resources/tokenizer/nonVerbSuffixRegSplitting.txt");
    verbPrefixRegMerging = new Reader()
        .readConfigFile(currentPath+"/resources/tokenizer/verbPrefixRegMerging.txt");
    verbSuffixRegMerging = new Reader()
        .readConfigFile(currentPath+"/resources/tokenizer/verbSuffixRegMerging.txt");
    nonVerbPrefixRegMerging = new Reader()
        .readConfigFile(currentPath+"/resources/tokenizer/nonVerbPrefixRegMerging.txt");
    nonVerbSuffixRegMerging = new Reader()
        .readConfigFile(currentPath+"/resources/tokenizer/nonVerbSuffixRegMerging.txt");
    verbDictionary = new Reader()
        .readConfigFile(currentPath+"/resources/tokenizer/verbDictionary.txt");
    nonVerbDictionary = new Reader()
        .readConfigFile(currentPath+"/resources/tokenizer/nonVerbDictionary.txt");
    verbDictionary.forEach(item -> {
      if (!verbDictionaryHash.contains(item)) {
        verbDictionaryHash.add(item);
      }
    });
    nonVerbDictionary.forEach(item -> {
      if (!nonVerbDictionaryHash.contains(item)) {
        nonVerbDictionaryHash.add(item);
      }
    });
  }

  public WordTokenizer(String punctuationsFile, String topRegexFile) {
    puncs = new Reader().readChars(punctuationsFile);
    topRegex = new Reader().readConfigFile(topRegexFile);
  }

  private void splitTokens(Sentence sentence) {
    String text = sentence.toString();
    StringBuilder out = new StringBuilder();
    for (String word : text.split(" ")) {
      if (word.length() > 1) {
        for (int i = 0; i < word.length(); i++) {
          if (puncs.contains(word.charAt(i))) {
            if (i == 0)//If it is the first character
            {
              out.append(word.charAt(i));
              out.append(' ');
            } else if (i == word.length() - 1)//If it is the last character
            {
              out.append(' ');
              out.append(word.charAt(i));
            } else {
              if (word.substring(i - 1, i).replaceAll("[0-9a-zA-Z_/-]", " ").equals(
                  " "))//Previous char is English char or digit char, dash, underline, etc.
              {
                out.append(word.charAt(i));
              } else {
                out.append(' ');
                out.append(word.charAt(i));
              }
              if (!word.substring(i + 1, i + 2).replaceAll("[0-9a-zA-Z_/-]", " ")
                  .equals(" ")) {
                out.append(' ');
              }
            }
          } else {
            out.append(word.charAt(i));
            //out.append(word.substring(i, i + 1));
          }
        }
      } else {
        out.append(word);
      }
      out.append(' ');
    }
    sentence.setSentence(out.toString().trim());

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

  private Matcher matchString(String inText, String inRegex) {
    if (Pattern.compile(inRegex).matcher(inText).matches()) {
      Pattern pattern = Pattern.compile(inRegex);
      Matcher matcher = pattern.matcher(inText);
      matcher.matches();
      return matcher;
    }
    return null;
  }

  private boolean tokenPrefixSuffixChecker(Sentence sentence
      , String w1, String w2
      , String mainWord
      , int i, List<String> regPreSuf, HashSet<String> dictionary) {
    String token = w1 + " " + w2;
    for (String reg : regPreSuf) {
      Matcher matcher = matchString(token, reg);
      if (matcher != null) {
        try {
          //Check if the mainWord exist in the dictionary
          if ((dictionary.contains(mainWord) || mainWord.contains("" + semispace))
              && mainWord.length() > 1) {
            sentence.get(i).setWord(w1 + semispace + w2);

            //TODO: Check this
            sentence.remove(i + 1);
            return true;
          }
        } catch (Exception e) {
          //Ignore
        }
      }
    }
    return false;
  }

  private void nonVerbPrefixSuffixMerging(Sentence sentence
      , List<String> nonVerbPrefixReg, List<String> nonVerbSuffixReg
      , HashSet<String> nonVerbDictionary) {
    tokenPrefixSuffixMerging(sentence, nonVerbPrefixReg, nonVerbSuffixReg, nonVerbDictionary);
  }

  private void verbPrefixSuffixMerging(Sentence sentence
      , List<String> verbPrefixReg, List<String> verbSuffixReg
      , HashSet<String> verbDictionary) {
    tokenPrefixSuffixMerging(sentence, verbPrefixReg, verbSuffixReg, verbDictionary);
  }

  private void tokenPrefixSuffixMerging(Sentence sentence
      , List<String> prefixReg, List<String> suffixReg
      , HashSet<String> dictionary) {
    for (int i = 0; i < sentence.size() - 1; i++) {
      String w1 = sentence.get(i).toString();
      String w2 = sentence.get(i + 1).toString();
      if (tokenPrefixSuffixChecker(sentence, w1, w2, w2, i, prefixReg, dictionary)) {
        i--;
        continue;
      }
      if (tokenPrefixSuffixChecker(sentence, w1, w2, w1, i, suffixReg, dictionary)) {
        i--;/*continue;*/
      }
    }
  }

  private void nonVerbPrefixSuffixSplitting(Sentence sentence
      , List<String> nonVerbPrefixReg, List<String> nonVerbSuffixReg
      , HashSet<String> nonVerbDictionary) {
    tokenPrefixSuffixSplitting(sentence, nonVerbPrefixReg, nonVerbSuffixReg, nonVerbDictionary);
  }

  private void verbPrefixSuffixSplitting(Sentence sentence
      , List<String> verbPrefixReg, List<String> verbSuffixReg
      , HashSet<String> verbDictionary) {
    tokenPrefixSuffixSplitting(sentence, verbPrefixReg, verbSuffixReg, verbDictionary);
  }

  private void tokenPrefixSuffixSplitting(Sentence sentence
      , List<String> prefixReg, List<String> suffixReg
      , HashSet<String> dictionary) {
    for (Token aSentence : sentence) {
      String token = aSentence.toString();
      for (String reg : prefixReg) {
        Matcher matcher = matchString(token, reg);
        if (matcher != null) {
          try {
            String mainWord = matcher.group(2);
            String prefixWord = matcher.group(1);
            String tmpMainWord = mainWord;
            if (tmpMainWord.contains("" + semispace)) {
              tmpMainWord = tmpMainWord.split("" + semispace)[0];
            }
            //Check if the mainWord exist in the dictionary
            if (dictionary.contains(tmpMainWord) && mainWord.length() > 1) {
              aSentence.setWord(prefixWord + semispace + mainWord);
            }
          } catch (Exception e) {
            //Ignore
          }
        }
      }
      for (String reg : suffixReg) {
        Matcher matcher = matchString(token, reg);
        if (matcher != null) {
          try {
            String mainWord = matcher.group(1);
            String suffixWord = matcher.group(2);
            String tmpMainWord = mainWord;
            if (tmpMainWord.contains("" + semispace)) {
              tmpMainWord = tmpMainWord.split("" + semispace)[1];
            }
            //Check if the mainWord exist in the dictionary
            if (dictionary.contains(tmpMainWord) && mainWord.length() > 1) {
              aSentence.setWord(mainWord + semispace + suffixWord);
            }
          } catch (Exception e) {
            //Ignore
          }
        }
      }
    }
  }

  public void process(Document document) {
    for (Sentence aDocument : document) {
      process(aDocument);
    }
  }

  public void process(Sentence sentence) {
    splitTokens(sentence);
    removeEmptyWords(sentence);
    nonVerbPrefixSuffixSplitting(sentence, nonVerbPrefixRegSplitting, nonVerbSuffixRegSplitting,
        nonVerbDictionaryHash);
    verbPrefixSuffixSplitting(sentence, verbPrefixRegSplitting, verbSuffixRegSplitting,
        verbDictionaryHash);
    nonVerbPrefixSuffixMerging(sentence, nonVerbPrefixRegMerging, nonVerbSuffixRegMerging,
        nonVerbDictionaryHash);
    verbPrefixSuffixMerging(sentence, verbPrefixRegMerging, verbSuffixRegMerging,
        verbDictionaryHash);
  }
}
