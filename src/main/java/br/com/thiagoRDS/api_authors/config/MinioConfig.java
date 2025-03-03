package br.com.thiagoRDS.api_authors.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
  private String url;
  private String accessKey;
  private String secretKey;
  private String bucketName;

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getAccessKey() {
    return this.accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return this.secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getBucketName() {
    return this.bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  @Bean
  MinioClient minioClient() {
    MinioClient client = MinioClient.builder()
        .endpoint(this.url)
        .credentials(this.accessKey, this.secretKey)
        .build();

    return client;
  }
}
