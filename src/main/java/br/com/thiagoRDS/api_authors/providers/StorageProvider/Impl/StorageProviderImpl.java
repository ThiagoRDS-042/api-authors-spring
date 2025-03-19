package br.com.thiagoRDS.api_authors.providers.StorageProvider.Impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.thiagoRDS.api_authors.exceptions.InternalServerErrorException;
import br.com.thiagoRDS.api_authors.providers.StorageProvider.StorageProvider;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

@Service
public class StorageProviderImpl extends StorageProvider {
  @Value("${minio.bucket-name}")
  private String bucketName;

  @Autowired
  private MinioClient minioClient;

  public void createBucket() {
    BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(this.bucketName).build();

    try {
      boolean exists = this.minioClient.bucketExists(bucketExistsArgs);

      if (!exists) {
        MakeBucketArgs bucketArgs = MakeBucketArgs.builder().bucket(this.bucketName).build();

        this.minioClient.makeBucket(bucketArgs);
      }
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }

  public InputStream getFile(String filename) {
    this.createBucket();

    GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(this.bucketName).object(filename).build();

    try {
      var file = this.minioClient.getObject(getObjectArgs);

      return file;
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }

  public void uploadFile(MultipartFile file, String filename) {
    this.createBucket();

    try {
      InputStream inputStream = file.getInputStream();

      PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(this.bucketName).object(filename)
          .contentType(file.getContentType()).stream(inputStream, inputStream.available(), -1).build();

      this.minioClient.putObject(putObjectArgs);
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }

  public void deleteFile(String filename) {
    this.createBucket();

    RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(this.bucketName).object(filename).build();

    try {
      this.minioClient.removeObject(removeObjectArgs);
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }
}
