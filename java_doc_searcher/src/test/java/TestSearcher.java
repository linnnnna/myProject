import searcher.Result;
import searcher.Searcher;

import java.io.IOException;
import java.util.List;

public class TestSearcher {
    public static void main(String[] args) throws IOException {
        Searcher searcher = new Searcher();
        List<Result> results = searcher.search("ArrayList");
        for (Result result : results) {
            System.out.println("==========================================================");
            System.out.println(result);
        }
    }
}
