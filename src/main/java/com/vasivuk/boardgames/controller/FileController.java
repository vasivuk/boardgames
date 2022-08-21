package com.vasivuk.boardgames.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/images")
public class FileController {
    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) throws IOException {
        Resource resource = new ClassPathResource("images/"+filename);

        InputStream inputStream = resource.getInputStream();

        File file = resource.getFile();

        byte[] image = FileUtils.readFileToByteArray(file);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
