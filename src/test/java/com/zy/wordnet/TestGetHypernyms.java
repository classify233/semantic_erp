package com.zy.wordnet;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class TestGetHypernyms {

    @Test
    public void test() throws IOException {
        String wnhome = System.getenv("WNHOME"); //获取WordNet根目录环境变量WNHOME
        String path = wnhome + File.separator + "dict";
        File wnDir = new File(path);

        IDictionary dict = new Dictionary(wnDir);
        dict.open();//打开词典

        getHypernyms(dict);//testing
    }

    public static void getHypernyms(IDictionary dict) {

        //获取指定的synset
        IIndexWord idxWord = dict.getIndexWord("article", POS.NOUN);//获取dog的IndexWord
        IWordID wordID = idxWord.getWordIDs().get(0); //取出第一个词义的词的ID号
        IWord word = dict.getWord(wordID); //获取词
        ISynset synset = word.getSynset(); //获取该词所在的Synset

        // 获取hypernyms
        List<ISynsetID> hypernyms = synset.getRelatedSynsets(Pointer.HYPERNYM);//通过指针类型来获取相关的词集，其中Pointer类型为HYPERNYM

        // print out each hypernyms id and synonyms
        List<IWord> words;
        for (ISynsetID sid : hypernyms) {
            words = dict.getSynset(sid).getWords(); //从synset中获取一个Word的list
            System.out.print(sid + "{");
            for (Iterator<IWord> i = words.iterator(); i.hasNext(); ) {
                System.out.print(i.next().getLemma());
                if (i.hasNext()) {
                    System.out.print(", ");
                }
            }
            System.out.println("}");
        }
    }
}
