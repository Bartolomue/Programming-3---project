import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by bartek on 11/6/16.
 */
public class TextFile {

    public static void createFile(String path, String fileContent) {
        try {
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            writer.print(fileContent);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error occured while file creation.");
        }
    }

    public static String getContentSite(String url) throws IOException {
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

    public static String buildPath(String folderPath, String fileName) {
        return folderPath + java.io.File.separator + validateFileName(fileName);
    }

    private static String validateFileName(String fileName) {
        String fileNameValid = fileName.replaceAll("\\W+", "");
        if (!fileName.equals(fileNameValid)) {
            System.out.println("File name was not valid, therefore was changed.");
        }
        return fileNameValid;
    }

}
