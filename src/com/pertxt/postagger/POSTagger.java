package com.pertxt.postagger;


//import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import com.pertxt.datatype.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import com.pertxt.datatype.Token;

public class POSTagger
{

    private MaxentTagger tagger;
    private String separator="\\^";
    private String currentPath=System.getProperty("user.dir");

    public POSTagger(String modelFile) {
        loadModel(modelFile);
    }
    public POSTagger() {
        loadModel(currentPath+"/resources/posTagger/model.tagger");
    }
    private void loadModel(String modelFile) {
        if (new java.io.File(modelFile).exists()) {
            tagger = new MaxentTagger(modelFile);
        } else {
            System.out.println("POSTagger model not found!");
        }
    }



    private void tagSentence(Sentence sentence) {
        String text = sentence.toString();

        text = tagger.tagTokenizedString(text);
        sentence.clearSentence();
        for (String item : text.split(" ")) {
            Token token=new Token(item.split(separator)[0]);
            token.setPosTag(item.split(separator)[1]);

            //TODO: Check this
            sentence.add(token);
        }
        //sentence.setSentence(text);
    }

    public void process(Sentence sentence) {
        tagSentence(sentence);
    }
}
