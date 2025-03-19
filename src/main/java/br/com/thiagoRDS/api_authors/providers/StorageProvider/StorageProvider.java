package br.com.thiagoRDS.api_authors.providers.StorageProvider;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public abstract class StorageProvider {
  public abstract void createBucket();

  public abstract InputStream getFile(String filename);

  public abstract void uploadFile(MultipartFile file, String filename);

  public abstract void deleteFile(String filename);
}
