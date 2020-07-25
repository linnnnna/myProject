package parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

// 遍历文档目录, 读取所有的 html 文档内容, 把结果解析成行文本文件.
// 每一行都对应一个文档. 每一行中都包含 文档标题, 文档的 URL, 文档的正文(去掉 html 标签的内容)
public class Parser {
    // 下载好的 Java API 文档在哪
    private static final String INPUT_PATH = "D:\\javaSE8 Doc\\docs\\api";
    // 预处理模块输出文件存放的目录
    private static final String OUTPUT_PATH = "D:/raw_data.txt";

    public static void main(String[] args) throws IOException {
        FileWriter resultFileWriter = new FileWriter(new File(OUTPUT_PATH));
        // 通过 main 完成整个预处理的过程
        // 枚举出 INPUT_PATH 下所有的 html 文件(递归)
        ArrayList<File> fileList = new ArrayList<>();
        enumFile(INPUT_PATH, fileList);
        // 针对枚举出来的html文件路径进行遍历, 依次打开每个文件, 并读取内容.
        //    把内容转换成需要的结构化的数据(DocInfo对象)
        for (File f : fileList) {
            System.out.println("converting " + f.getAbsolutePath() + " ...");
            // 最终输出的 raw_data 文件是一个行文本文件. 每一行对应一个 html 文件.
            // line 这个对象就对应到一个文件.
            String line = convertLine(f);
            // 把得到的结果写入到最终的输出文件中(OUTPUT_PATH). 写成行文本的形式
            resultFileWriter.write(line);
        }
        resultFileWriter.close();
    }

    public static String convertLine(File f) throws IOException {
        String title = convertTitle(f);
        String url = convertUrl(f);
        String content = convertContent(f);
        return title + "\3" + url + "\3" + content + "\n";
    }

    public static String convertContent(File f) throws IOException {
        // 1. 把 html 中的标签去掉
        // 2. 把 \n 也去掉
        // 一个一个字符读取并判定
        FileReader fileReader = new FileReader(f);
        boolean isContent = true;
        StringBuilder output = new StringBuilder();
        while (true) {
            int ret = fileReader.read();
            if (ret == -1) {
                // 读取完毕了
                break;
            }
            char c = (char)ret;
            if (isContent) {
                // 当前这部分内容是正文
                if (c == '<') {
                    isContent = false;
                    continue;
                }
                if (c == '\n' || c == '\r') {
                    c = ' ';
                }
                output.append(c);
            } else {
                // 当前这个部分内容是标签
                // 不去写 output
                if (c == '>') {
                    isContent = true;
                }
            }
        }
        fileReader.close();
        return output.toString();
    }

    public static String convertUrl(File f) {
        // URL 线上文档对应的 URL
        String part1 = "https://docs.oracle.com/javase/8/docs/api";
        String part2 = f.getAbsolutePath().substring(INPUT_PATH.length());
        return part1 + part2;
    }

    public static String convertTitle(File f) {
        // 把文件名(不是全路径, 去掉.html后缀)作为标题
        String name = f.getName();
        return name.substring(0, name.length() - ".html".length());
    }

    // 当这个方法递归完毕后, 当前 inputPath 目录下所有子目录中的 html 的路径就都被收集到fileList 这个 List 中了
    public static void enumFile(String inputPath, ArrayList<File> fileList) {
        // 递归的把 inputPath 对应的全部目录和文件都遍历一遍
        File root = new File(inputPath);
        File[] files = root.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                // 如果当前这个 f 是一个目录. 递归调用 enumFile
                enumFile(f.getAbsolutePath(), fileList);
            } else if (f.getAbsolutePath().endsWith(".html")) {
                // 如果当前 f 不是一个目录, 看文件后缀是不是 .html. 如果是就把这个文件的对象
                // 加入到 fileList 这个 List 中
                fileList.add(f);
            }
        }
    }
}
