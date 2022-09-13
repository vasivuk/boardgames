package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.ImageStorageException;
import com.vasivuk.boardgames.exception.MyFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String storeImage(MultipartFile image) throws ImageStorageException;

    Resource loadFileAsResource(String filename) throws MyFileNotFoundException;
}
