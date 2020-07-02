package parser;

import Common.DocInfo;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void convertLine() {
        Parser parser = new Parser();
        DocInfo docInfo = new DocInfo();
        docInfo.setTitle("");
        docInfo.setUrl("");
        docInfo.setUrl("");
    }

    @Test
    public void convertContent() throws IOException {
        File file = new File("D:\\Test.txt");
        Parser parser = new Parser();
        String res = parser.convertContent(file);
        System.out.println(res);
    }

    @Test
    public void convertUrl() {
    }

    @Test
    public void convertTitle() {
    }

    @Test
    public void enumFile() {
    }
}