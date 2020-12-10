package com.overrideeg.apps.opass.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {
    public static String downloadReport(String url){
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String outputPath = "C:\\Reports\\"+fileName;
        try {
            InputStream inputStream = new URL(url).openStream();
            Files.copy(inputStream, Paths.get(outputPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputPath;
    }
}
