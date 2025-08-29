package com.app.restaurante.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class UploadServicio {

    private final String baseDir = "src/main/resources/static/upload/";

    public String saveUpload(MultipartFile file, String subFolder) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String encode = URLEncoder.encode(Objects.requireNonNull(file.getOriginalFilename()), StandardCharsets.UTF_8);
            Path path = Paths.get(baseDir + subFolder + "/" + encode);
            Files.createDirectories(path.getParent()); // Crea las carpetas si no existen
            Files.write(path, bytes);
            return encode; // Retorna el nombre del archivo
        }
        return null;
    }

    public void deleteUpload(String nombre, String subFolder) {
        File file = new File(baseDir + subFolder + "/" + nombre);
        if (file.exists()) {
            file.delete();
        }
    }
}

