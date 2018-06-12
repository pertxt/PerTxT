package com.pertxt.datatype;

import java.util.LinkedList;

public class Document extends LinkedList<Sentence>
{
    /*private LinkedList<Sentence> sentences=new LinkedList<>();*/
    //Copy constructor
    public Document(Document document) {
        for (Sentence sentence : document) {
            Sentence sent = new Sentence(sentence);
            this.add(sent);
        }
    }
    public Document()
    {

    }
    public Document(Sentence[] sentences) {
        for (Sentence sentence : sentences) {
            if (sentence != null) {
                this.add(sentence);
            }
        }
    }


    public void add(String sentence)
    {
        this.add(new Sentence(sentence));
    }

    /*public void add(Sentence sentence){sentences.add(sentence);}*/

    /*public Sentence get(int i)
    {
        if(i>this.size() || i<0)return null;
        return this.get(i);
    }*/
    public void replaceSentence(int i,String sentence)
    {
        if(i>this.size() || i<0)return;
        this.set(i,new Sentence(sentence));
    }
    public void replaceSentence(int i,Sentence sentence)
    {
        if(i>this.size() || i<0)return;
        this.set(i,sentence);
    }


    /*public void remove(int i)
    {
        if(i>sentences.size() || i<0)return;
        sentences.remove(i);
    }*/
    /*public int length()
    {
        return this.size();
    }*/

/*    //I think this function dose not work correctly!
    public boolean existSentence(String sentence)
    {
        if(sentences.contains(new Sentence(sentence)))return true;
        return false;
    }
    //I think this function dose not work correctly!
    public boolean existSentence(Sentence sentence)
    {
        if(sentences.contains(sentence))return true;
        return false;
    }*/

    //Todo: fix this. It should return just posTags not words!
    public String posTagsToString()
    {
        String result="";
        for (Sentence tokens : this) {
            result += tokens.toStringWordPOS(" ") + " ";
        }
        result=result.trim();
        return result;
    }
    public String[] posTagsToArray()
    {
        String result="";
        for (Sentence tokens : this) {
            result += tokens.toStringPOS() + " ";
        }
        result=result.trim();
        return result.split(" ");
    }

    public String toString()
    {
        String result="";
        for (Sentence tokens : this) {
            result += tokens.toString() + " ";
        }
        result=result.trim();
        return result;
    }
    public String[] toArrayString() {
        String result = "";
        for (Sentence tokens : this) {
            result += tokens.toString() + "\n";
        }
        result = result.trim();
        return result.split("\n");

    }

    public void setDocument(Sentence[] sentences)
    {
        this.clear();
        for (Sentence sentence : sentences) {
            if (sentence != null) {
                this.add(sentence);
            }
        }
    }


}
