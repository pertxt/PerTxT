package com.pertxt.datatype;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by dehghan on 8/24/2017.
 */
public class Token {
  //Feature works!
  private String lemma;
  private boolean stopWord = false;
  private String chunkTag;
  private String neTag;
  private String posTag;
  private int cntChars = 0;

  private ArrayList<Character> chars = new ArrayList<>();


  /**
   * @param inWord the string to be stored
   */
  public Token(String inWord) {
    for (int i = 0; i < inWord.length(); i++) {
      chars.add(inWord.charAt(i));
    }
  }

  //Copy constructor
  Token(Token token) {
    this.lemma = token.lemma;
    this.stopWord = token.stopWord;
    this.chunkTag = token.chunkTag;
    this.neTag = token.neTag;
    this.posTag = token.posTag;
    this.cntChars = token.cntChars;
    this.chars = (ArrayList<Character>) token.chars.clone();
  }

  /**
   * Appends the specified character to the end of this token.
   *
   * @param inChar character to be appended to this token.
   */
  public void add(Character inChar) {
    chars.add(inChar);
  }

  /**
   * @param i index of the element
   * @return character in position <code>i</code>
   */
  public Character get(int i) {
    if (i > chars.size()) {
      return null;
    }
    return chars.get(i);
  }

  /**
   * Replaces the character at the specified position in this token with the specified character.
   *
   * @param i index of the element
   * @param inChar the new character
   */
  public void replaceChar(int i, Character inChar) {
    if (i > chars.size()) {
      return;
    }

    //words.remove(i);
    chars.set(i, inChar);
  }

  /**
   * Removes the character at the specified position in this token. Shifts any subsequent characters
   * to the left (subtracts one from their indices). Returns the character that was removed from the
   * list.
   *
   * @param i index of the element
   * @return the character previously at the specified position
   */
  public Character remove(int i) {
    if (i > chars.size() || i < 0) {
      return null;
    }

    return chars.remove(i);
  }

  /**
   * Returns the number of characters in this token.
   *
   * @return the number of characters in this token
   */
  public int length() {
    return chars.size();
  }

  /**
   * Returns <code>true</code> if this token contains the specified character. More formally,
   * returns <code>true</code> if and only if this token contains at least one character
   * <code>ch</code> such that<br> <code>(inChar==null ? ch==null : inChar.equals(ch))</code>.
   *
   * @param inChar element whose presence in token is to be tested
   * @return <code>true</code> if this token contains the specified character
   */
  public boolean containsChar(Character inChar) {
    return chars.contains(inChar);
  }

  /**
   * @return a string representation of this token.
   */
  public String toString() {
    String result = "";
    for (Character aChar : chars) {
      result += aChar.toString();
    }
    //result=result.trim();
    return result;
  }

  public void setWord(String inWord) {
    chars.clear();
    for (int i = 0; i < inWord.length(); i++) {
      chars.add(inWord.charAt(i));
    }
  }

  public String getPosTag() {
    return posTag;
  }

  public void setPosTag(String posTag) {
    this.posTag = posTag;
  }

  public LinkedList<String> ToLinkedListString() {
    return null;
  }

  public String[] ToArrayString() {
    return null;
  }

  /**
   * Set the stop word's indicator of this token to true
   */
  public void setStopWord() {
    stopWord = true;
  }

  /**
   * Set the stop word's indicator of this token to false
   */
  public void clearStopWord() {
    stopWord = false;
  }

  /**
   * Returns <code>true</code> if this token is a stop word
   *
   * @return <code>true</code> if this token is a stop word
   */
  public boolean isStopWord() {
    return stopWord;
  }

}
