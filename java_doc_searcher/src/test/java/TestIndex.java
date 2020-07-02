import Common.DocInfo;
import index.Index;

import java.io.IOException;
import java.util.List;

public class TestIndex {
    public static void main(String[] args) throws IOException {
        Index index = new Index();
        index.build("D:\\data.txt");
        List<Index.Weight> invertedList = index.getInverted("arraylist");//term 应该是全小写的
        for (Index.Weight weight : invertedList) {
            System.out.println(weight.docId);
            System.out.println(weight.word);
            System.out.println(weight.weight);

            DocInfo docInfo = index.getDocInfo(weight.docId);
            System.out.println(docInfo.getTitle());
            System.out.println("=====================================================");
        }
    }
}
