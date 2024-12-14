package com.projects.ticketsystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String savePoster(MultipartFile posterFile) throws IOException {
        if (posterFile.isEmpty()) return null;

        String extension = posterFile.getOriginalFilename()
                .substring(posterFile.getOriginalFilename().lastIndexOf(".") + 1)
                .toLowerCase();

        if (!extension.equals("png") && !extension.equals("jpg")) {
            throw new IllegalArgumentException("Неверный формат файла. Допустимы только PNG и JPG.");
        }

        String randomNumbers = String.valueOf(new Random().nextInt(10000));
        String resultFilename = "poster_" + randomNumbers + "." + extension;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        posterFile.transferTo(new File(uploadPath + "/" + resultFilename));

        return resultFilename;
    }

    public void deletePoster(String filename) {
        if (filename == null || filename.isEmpty()) return;

        File file = new File(uploadPath + "/" + filename);
        if (file.exists() && !file.delete()) {
            throw new RuntimeException("Не удалось удалить файл: " + filename);
        }
    }

}
