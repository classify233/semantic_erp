package com.zy.wordnet;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 在获取一个IndexWord时，很容易抛出一个运行异常，即NullPointerException。这是因为在WordNet里找不着你想要的词。但是这个词在实际英语环境中是存在的。可能的原因如下：
 * 1.所输入的词形式不对，比如：go写成了gone，或者dog写成dogs等非源词形式了。
 * 2.可能是你在构造词时，在如getIndexWord("go", POS.VERB)函数中词性参数输入错误，比如上例中输入的词性是POS.ADVERB。由于go没有副词，所以汇报NullPointerException异常。
 * 3.一些新的单词根本还没录入WordNet。
 */
public class TestGetSynonyms {

    //JWI获取同义词以及抛出NullPointerException原因解析
    @Test
    public void test() throws IOException {
        String wnhome = System.getenv("WNHOME"); //获取WordNet根目录环境变量WNHOME
        String path = wnhome + File.separator + "dict";
        File wnDir = new File(path);

        URL url = new URL("file", null, path);
        IDictionary dict = new Dictionary(url);
        dict.open();//打开词典

        getSynonyms(dict, "go", POS.VERB); //testing
    }

    public static void getSynonyms(IDictionary dict, String in, POS pos) {

        // look up first sense of the word "go"
        IIndexWord idxWord = dict.getIndexWord(in, pos);
        IWordID wordID = idxWord.getWordIDs().get(0); // 1st meaning

        IWord word = dict.getWord(wordID);
        ISynset synset = word.getSynset(); //ISynset是一个词的同义词集的接口

        // iterate over words associated with the synset
        for (IWord w : synset.getWords())
            System.out.println(w.getLemma());//打印同义词集中的每个同义词
    }
}
