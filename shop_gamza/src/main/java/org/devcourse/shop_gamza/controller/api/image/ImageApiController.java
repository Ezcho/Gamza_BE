package org.devcourse.shop_gamza.controller.api.image;

import lombok.RequiredArgsConstructor;
import org.devcourse.shop_gamza.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageApiController {

    private final FileService fileService;

    @GetMapping("/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }

    @PostMapping
    public void uploadImage(@ModelAttribute MultipartFile image) {
        fileService.storeFile(image);
    }
}
