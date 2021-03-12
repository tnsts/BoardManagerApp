package com.example.boardmanager.repositories;

import org.springframework.core.io.FileSystemResource;

public interface FileSystemRepository {
    String save(String resourceDir, byte[] content, String imageName) throws Exception;

    FileSystemResource findInFileSystem(String location) throws Exception;
}
