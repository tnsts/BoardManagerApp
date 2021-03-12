package com.example.boardmanager.services;

import com.example.boardmanager.repositories.FileSystemRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class FileSystemService {

    private final FileSystemRepository fileSystemRepository;

    String RESOURCES_DIR = "/upload-dir/";

    public FileSystemService(FileSystemRepository fileSystemRepository) {
        this.fileSystemRepository = fileSystemRepository;
    }

    public String save(byte[] bytes, String imageName) throws Exception {

        return fileSystemRepository.save(RESOURCES_DIR, bytes, imageName);
    }

    public FileSystemResource find(String location) throws Exception {
        return fileSystemRepository.findInFileSystem(location);
    }
}