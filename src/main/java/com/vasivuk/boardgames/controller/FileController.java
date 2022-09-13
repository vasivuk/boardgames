package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.exception.ImageStorageException;
import com.vasivuk.boardgames.exception.MyFileNotFoundException;
import com.vasivuk.boardgames.service.FileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) throws IOException, MyFileNotFoundException {
        Resource resource = fileService.loadFileAsResource(filename);

        File file = resource.getFile();

        byte[] image = FileUtils.readFileToByteArray(file);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @PostMapping("api/images/save")
    public ResponseEntity<String> saveProductImage(@RequestBody MultipartFile image) throws ImageStorageException {
        String fileName = fileService.storeImage(image);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")
                .path(fileName)
                .toUriString();
        System.out.println(fileDownloadUri);
        return ResponseEntity.ok().body(fileDownloadUri);
    }
}
