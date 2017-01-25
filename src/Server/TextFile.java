package Server;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TextFile {
    public TextFile() {
    }

    public static void createFile(String path, String fileContent) {
        try {
            PrintWriter e = new PrintWriter(path, "UTF-8");
            e.print(fileContent);
            e.close();
        } catch (Exception var3) {
            System.out.println("Error occured while file creation.");
        }

    }

    public static String getContentSite(String url) throws IOException {
        Element contentDiv = null;

        try {
            Document e = Jsoup.connect(url).get();
            contentDiv = e.select("div[id=content]").first();
        } catch (Exception var3) {
            System.out.println("Error occured while getting content from website: " + url);
        }

        return contentDiv.text();
    }

    public static String getContentFile(String pathToFile) throws IOException {
        return new String(Files.readAllBytes(Paths.get(pathToFile, new String[0])));
    }

    public static String getContentFile(Path path) throws IOException {
        if(path == null) {
            throw new IllegalArgumentException("Wrong path.");
        } else {
            return new String(Files.readAllBytes(path));
        }
    }

    public static String getFileNameByPath(Path path) {
        if(path == null) {
            throw new IllegalArgumentException("Wrong path.");
        } else {
            return path.getFileName().toString();
        }
    }

    public static String buildPath(String folderPath, String fileName) {
        return folderPath + File.separator + validateFileName(fileName);
    }

    private static String validateFileName(String fileName) {
        String fileNameValid = fileName.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9.-_]", "-");
        if(!fileName.equals(fileNameValid)) {
            System.out.println("File name was not valid, therefore was changed.");
        }

        return fileNameValid;
    }
}
