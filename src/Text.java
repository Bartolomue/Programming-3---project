import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bartek on 11/6/16.
 */
public class Text {

    private static List<String> list = Arrays.asList("Warsaw", "Boston", "New_York", "London", "Kraków");
    private static final String sampleUrl = "https://en.wikipedia.org/wiki/";
    private static final String folderPath = "data" + File.separator + "Files";

    public static void main(String argv[]) throws IOException {
        System.out.println(folderPath);
        System.out.println(buildPath(folderPath, "Warsaw"));
        System.out.println(Paths.get(buildPath(folderPath, "Warsaw")));

        File f1 = new File(buildPath(folderPath, "Warsaw"));
        if (f1.exists() && !f1.isDirectory()) {
            System.out.println("File exists!");
        } else {
            System.out.println("File does not exist!");
        }

        System.out.println(new String(Files.readAllBytes(Paths.get(buildPath(folderPath, "Warsaw")))));

    }

    public static List<String> getLinksByLink(String url) {
        List<String> linksClean = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            // get the page title
            String title = doc.title();
            System.out.println("title: " + title);

            // get all links in page
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                System.out.println("\nlink: " + link.attr("href"));
                System.out.println("data: " + link.dataset());
                // get the value from the href attribute
                if (link.text().contains("//")) {
                    linksClean.add(link.text());
                }
            }
        } catch (Exception e) {
            System.out.println("Error occured.");
        }

        return linksClean;
    }

    public static void createFile(String fileName, String fileContent) {
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.print(fileContent);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error occured while file creation.");
        }
    }

    public static void makeSampleData() throws IOException {
        for (String s : list
                ) {
            createFile( buildPath(folderPath, s), getContentSite( buildSampleUrl(sampleUrl, s)));
        }
    }

    private static String getContentSite(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element contentDiv = doc.select("div[id=content]").first();
        return contentDiv.text();
    }

    public static String getContentFile(String pathToFile) throws IOException {
        return new String(Files.readAllBytes(Paths.get(pathToFile)));
    }

    public static String getContentFile(Path path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Wrong path.");
        }
        return new String(Files.readAllBytes(path));
    }

    public static String getFileNameByPath(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("Wrong path.");
        }
        return path.getFileName().toString();
    }



    private static String validateFileName(String fileName) {
        return fileName.replaceAll("\\W+", "");
    }

    private static String buildPath(String folderPath, String fileName) {
        return folderPath + File.separator + fileName;
    }

    private static String buildSampleUrl(String domain, String topic) {
        return String.join(domain + topic);
    }

    public static String getSampleWeb1() throws IOException {
        return getContentSite(buildSampleUrl(sampleUrl, list.get(0)));
    }

    public static String getSampleLocal() throws IOException {
        return getContentFile(buildPath(folderPath, validateFileName(list.get(0))));
    }

    public static Path getSamplePath1() throws IOException {
        return Paths.get(buildPath(folderPath, validateFileName(list.get(0))));
    }

    public static Path getSamplePath2() throws IOException {
        return Paths.get(buildPath(folderPath, validateFileName(list.get(1))));
    }

    public static Path getSamplePath3() throws IOException {
        return Paths.get(buildPath(folderPath, validateFileName(list.get(2))));
    }

    public static Path getSamplePath4() throws IOException {
        return Paths.get(buildPath(folderPath, validateFileName(list.get(3))));
    }

    public static Path getSamplePath5() throws IOException {
        return Paths.get(buildPath(folderPath, validateFileName(list.get(4))));
    }


}
