package com.igitan.springboothome.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

  private final Path fileStorageLocation;

  public FileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
    this.fileStorageLocation = Paths.get(uploadDir)
        .toAbsolutePath().normalize();

    try {
      Files.createDirectories(this.fileStorageLocation);
    } catch (Exception ex) {
      throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
    }
  }

  public String storeFile(MultipartFile file) {
    // Normalize file name
    String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
    String fileName = "";

    try {
      // Check if the file's name contains invalid characters
      if (originalFileName.contains("..")) {
        throw new RuntimeException("Sorry! Filename contains invalid path sequence " + originalFileName);
      }

      // Generate a unique file name to avoid collisions
      String extension = "";
      int i = originalFileName.lastIndexOf('.');
      if (i > 0) {
        extension = originalFileName.substring(i);
      }
      fileName = UUID.randomUUID().toString() + extension;

      // Copy file to the target location (Replacing existing file with the same name)
      Path targetLocation = this.fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      return fileName;
    } catch (IOException ex) {
      throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
    }
  }

  public Path loadFileAsResource(String fileName) {
    return this.fileStorageLocation.resolve(fileName).normalize();
  }
}
