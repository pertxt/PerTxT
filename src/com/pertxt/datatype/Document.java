package com.pertxt.datatype;

import java.util.LinkedList;

public class Document extends LinkedList<Sentence>
{
    public Document()
    {

    }

    //Copy constructor
    public Document(Document document) {
        for (Sentence sentence : document) {
            Sentence sent = new Sentence(sentence);
            this.add(sent);
        }
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
        return result.toString().trim().split("\n");
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
