package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.configuration.FileStorageProperties;
import com.vasivuk.boardgames.exception.ImageStorageException;
import com.vasivuk.boardgames.exception.MyFileNotFoundException;
import com.vasivuk.boardgames.service.FileService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    private final Path imageStorageLocation;

    public FileServiceImpl(FileStorageProperties fileStorageProperties) {
        imageStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        System.out.println(imageStorageLocation);
    }


        @Override
    public String storeImage(MultipartFile image) throws ImageStorageException {
        String imageName = StringUtils.cleanPath(image.getOriginalFilename());
        // Ne dozvoljavamo ".." u imenu da se neko ne bi kretao po sistemu
        try {
            if (imageName.contains("..")) {
                throw new ImageStorageException("Sorry! Filename contains invalid path sequence: " + imageName);
            }

            Path targetLocation = this.imageStorageLocation.resolve(imageName).normalize();
            System.out.println(targetLocation);
            Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return imageName;
        } catch (IOException exception) {
            throw new ImageStorageException("Could not store file " + imageName + ". Please try again!", exception);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) throws MyFileNotFoundException {
        try {
            Path filePath = this.imageStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
