package br.com.authors.api_authors.providers;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.authors.api_authors.exceptions.InternalServerErrorException;
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
    MakeBucketArgs bucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();

    try {
      this.minioClient.makeBucket(bucketArgs);
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }

  public boolean bucketExists(String bucketName) {
    BucketExistsArgs bucketArgs = BucketExistsArgs.builder().bucket(bucketName).build();

    try {
      boolean exists = this.minioClient.bucketExists(bucketArgs);

      return exists;
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }

  public InputStream getFile(String bucketName, String filename) {
    GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(bucketName).object(filename).build();

    try {
      InputStream file = this.minioClient.getObject(getObjectArgs);

      return file;
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }

  public void uploadFile(String bucketName, MultipartFile file, String filename) {
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
    RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object(filename).build();

    try {
      this.minioClient.removeObject(removeObjectArgs);
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }
}
