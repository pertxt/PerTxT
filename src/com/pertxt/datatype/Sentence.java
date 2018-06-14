package com.pertxt.datatype;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sentence extends LinkedList<Token> {

    //Copy constructor
    Sentence(Sentence sentence) {
        for (Token token : sentence) {
            Token tkn = new Token(token);
            this.add(tkn);
        }
    }

    public Sentence(String sent) {
        for (String word : sent.split(" ")) {
            if (!word.trim().equals("")) {
                this.add(new Token(word));
            }
        }
    }

    public Sentence(Token[] tokens) {
        for (Token token : tokens) {
            if (token != null) {
                this.add(token);
            }
        }
    }

    public boolean add(String token) {
        return this.add(new Token(token));
    }

    public void replaceToken(int i, String token) {
        if (i > this.size()) {
            return;
        }
        this.set(i, new Token(token));
    }

    public void replaceToken(int i, Token token) {
        if (i > this.size()) {
            return;
        }
        this.set(i, token);
    }

    public boolean existWord(String token) {
        for (Token otoken : this) {
            if (otoken.toString().equals(token)) {
                return true;
            }
        }
        return false;
    }


    public boolean existWord(Token token) {

        for (Token otoken : this) {
            if (otoken.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String result = "";
        for (Token token : this) {
            result += token.toString() + " ";
        }
        result = result.trim();
        return result;
    }

    public String toStringWordPOS(String separator) {
        String result = "";
        for (Token token : this) {
            result += token.toString() + separator + token.getPosTag() + " ";
        }
        result = result.trim();
        return result;
    }

    public String toStringPOS() {
        String result = "";
        for (Token token : this) {
            result += token.getPosTag() + " ";
        }
        result = result.trim();
        return result;
    }


    public List<String> toListPOS() {
        List<String> pos = new ArrayList<>(this.size());
        for (int i = 0; i < this.size(); i++) {
            pos.add(this.get(i).getPosTag());
        }
        return pos;
    }

    public void clearSentence() {
        this.clear();
    }

    public void setSentence(String sent) {
        this.clear();
        for (String word : sent.split(" ")) {
            this.add(new Token(word));
        }
    }

    public void setSentence(Token[] sent) {
        this.clear();
    }

    public void setPOSTags(String[] posTags) {
        if (posTags.length != this.size()) {
            System.out.println("Unmatched length!");
            System.out.println(
                    "The sentence length is: " + this.size() + "\nThe input length is: " + posTags.length);
            return;
        }
        for (int i = 0; i < posTags.length; i++) {
            this.get(i).setPosTag(posTags[i]);
        }
    }

    public void setPOSTags(List<String> posTags) {
        if (posTags.size() != this.size()) {
            System.out.println("Unmatched length!");
            System.out.println(
                    "The sentence length is: " + this.size() + "\nThe input length is: " + posTags.size());
            return;
        }
        for (int i = 0; i < posTags.size(); i++) {
            this.get(i).setPosTag(posTags.get(i));
        }
    }

    public LinkedList<String> ToLinkedListString() {
        return null;
    }

    public String[] ToArrayString() {
        return null;
    }


}
