package searcher;

import Common.DocInfo;
import index.Index;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Searcher {
    private Index index = new Index();

    public Searcher() throws IOException {
        index.build("D:/raw_data.txt");
    }


    public List<Result> search (String query) {
        List<Term> terms = ToAnalysis.parse(query).getTerms();//针对用户输入内容进行分词
        ArrayList<Index.Weight> allTockenResult = new ArrayList<>();//保存每个分词的结果
        for (Term term : terms) {
            String word = term.getName();
            List<Index.Weight> invertedList = index.getInverted(word);
            if (invertedList == null) {
                continue;
            }
            allTockenResult.addAll(invertedList);
        }
        allTockenResult.sort(new Comparator<Index.Weight>() {
            @Override
            public int compare(Index.Weight o1, Index.Weight o2) {
                return o2.weight - o1.weight;//降序排列
            }
        });
        ArrayList<Result> results = new ArrayList<>();
        for (Index.Weight weight : allTockenResult) {
            DocInfo docInfo = index.getDocInfo(weight.docId);//根据weight中包含的docId找到DocInfo对象
            Result result = new Result();
            result.setTitle(docInfo.getTitle());
            result.setShowUrl(docInfo.getUrl());
            result.setClickUrl(docInfo.getUrl());
            result.setDesc(GenDesc(docInfo.getContent(), weight.word));
            results.add(result);
        }
        return results;
    }

    private String GenDesc(String content, String word) {
        int firstPos = content.toLowerCase().indexOf(word);//找到word第一次出现的位置
        if (firstPos == -1) {//没找到word的情况
            return "";
        }
        int descBeg = firstPos < 50 ? 0 : firstPos - 50;//描述的内容从这个词出现的前50个字符开始
        if (descBeg + 150 > content.length()) {
            return content.substring(descBeg);//从描述开始往后的150个字符如果超过正文的长度，整个描述就从descBeg开始到结束
        }
        return content.substring(descBeg,(descBeg + 150)) + "...";
    }
}
