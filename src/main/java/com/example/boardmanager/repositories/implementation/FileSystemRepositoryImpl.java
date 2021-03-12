package com.example.boardmanager.repositories.implementation;

import com.example.boardmanager.repositories.FileSystemRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class FileSystemRepositoryImpl implements FileSystemRepository {
    public String save(String resourceDir, byte[] content, String imageName) throws Exception {
        Path newFile = Paths.get(resourceDir + new Date().getTime() + "-" + imageName);
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile.toAbsolutePath().toString();
    }

    public FileSystemResource findInFileSystem(String location) throws Exception {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            throw new Exception("Such file not found");
        }
    }
}
