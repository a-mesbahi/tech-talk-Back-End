package com.techtalk.usersservice.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FileService {
    //final static String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/tech-talk-b9bba.appspot.com/o/%s?alt=media";
    final static String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/techtalk-1ff78.appspot.com/o/%s?alt=media";
    private static String TEMP_URL ="";

    private String uploadFile(File file, String fileName) throws IOException {
        //BlobId blobId = BlobId.of("tech-talk-b9bba.appspot.com", fileName);
        BlobId blobId = BlobId.of("techtalk-1ff78.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = getClass().getResourceAsStream("/serviceAccountKey2.json");
        //InputStream inputStream = getClass().getResourceAsStream("/serviceAccountKey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public Object upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File file = this.convertToFile(multipartFile, fileName);
            TEMP_URL = this.uploadFile(file, fileName);
            file.delete();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



}
