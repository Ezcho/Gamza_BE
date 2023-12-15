package org.devcourse.shop_gamza.service;

import lombok.extern.slf4j.Slf4j;
import org.devcourse.shop_gamza.domain.image.Image;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileService {
    private String fileDir;

    public FileService() {
        String root = System.getProperty("user.dir");
        fileDir = root + "/file/";
        log.info(fileDir);
    }

    public List<Image> storeFiles(List<MultipartFile> multipartFiles) {
        List<Image> storeFileResult = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            if (!file.isEmpty()) {
                storeFileResult.add(storeFile(file));
            }
        }
        return storeFileResult;
    }

    public Image storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        try {
            file.transferTo(new File(getFullPath(storeFileName)));
        } catch (IOException e) {
            return null;
        }

        return Image.builder()
                .uploadFileName(originalFilename)
                .storeFileName(storeFileName)
                .build();
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
