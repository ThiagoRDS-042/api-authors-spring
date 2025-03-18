package br.com.thiagoRDS.api_authors.providers;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.thiagoRDS.api_authors.exceptions.InternalServerErrorException;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

@Service
public class MinioProvider {
  @Autowired
  private MinioClient minioClient;

  public void createBucket(String bucketName) {
    BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();

    try {
      boolean exists = this.minioClient.bucketExists(bucketExistsArgs);

      if (!exists) {
        MakeBucketArgs bucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();

        this.minioClient.makeBucket(bucketArgs);
      }
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }

  public InputStream getFile(String bucketName, String filename) {
    this.createBucket(bucketName);

    GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(bucketName).object(filename).build();

    try {
      var file = this.minioClient.getObject(getObjectArgs);

      return file;
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }

  public void uploadFile(String bucketName, MultipartFile file, String filename) {
    this.createBucket(bucketName);

    try {
      InputStream inputStream = file.getInputStream();

      PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName).object(filename)
          .contentType(file.getContentType()).stream(inputStream, inputStream.available(), -1).build();

      this.minioClient.putObject(putObjectArgs);
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }

  public void deleteFile(String bucketName, String filename) {
    this.createBucket(bucketName);

    RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object(filename).build();

    try {
      this.minioClient.removeObject(removeObjectArgs);
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }
}
