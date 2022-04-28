package com.teamyear.admin.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpload {

    public static void saveImage(MultipartFile file, String fileName, String dir) throws IOException {

        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Path filePath = path.resolve(fileName);
        InputStream inputStream = file.getInputStream();
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void deleteImage(String dir) throws IOException {
        String delDir = "." + dir;
        Path delPath = Paths.get(delDir);
        Files.deleteIfExists(delPath);
    }

}