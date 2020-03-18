package com.zy.wordnet;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class TestTrek {

    // JWI装载WordNet到内存的遍历性能优化
    // RAMDictionary
    @Test
    public void test() {
        String wnhome = System.getenv("WNHOME"); //获取环境变量WNHOM
        String path = wnhome + File.separator+ "dict";

        File wnDir = new File(path);
        try {
            testRAMDictionary(wnDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testRAMDictionary(File wnDir) throws IOException, InterruptedException {

        IRAMDictionary dict = new RAMDictionary(wnDir, ILoadPolicy.NO_LOAD);
        dict.open();

        //周游WordNet
        System.out.print("没装载前：\n");
        trek(dict);

        //now load into memor
        System.out.print("\nLoading Wordnet into memory...");
        long t = System.currentTimeMillis();
        dict.load(true);
        System.out.printf("装载时间：done(%1d msec)\n", System.currentTimeMillis() - t);

        //装载后在周游
        System.out.print("\n装载后：\n");
        trek(dict);
    }

    public static void trek(IDictionary dict) {

        int tickNext = 0;
        int tickSize = 20000;
        int seen = 0;

        System.out.print("Treking across Wordnet");
        long t = System.currentTimeMillis();

        for (POS pos : POS.values()) { //遍历所有词性
            for (Iterator<IIndexWord> i = dict.getIndexWordIterator(pos); i.hasNext(); ) {
                //遍历某一个词性的所有索引
                for (IWordID wid : i.next().getWordIDs()) {
                    //遍历每一个词的所有义项
                    seen += dict.getWord(wid).getSynset().getWords().size();//获取某一个synsets所具有的词
                    if (seen > tickNext) {
                        System.out.print(".");
                        tickNext = seen + tickSize;
                    }
                }
            }
        }

        System.out.printf("done (%1d msec)\n", System.currentTimeMillis() - t);
        System.out.println("In my trek I saw " + seen + " words");
    }
}
