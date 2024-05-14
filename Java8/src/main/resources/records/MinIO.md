# MinIO

---

### 什么是minio

```text
MinIO是根据 GNU Affero 通用公共许可证v3.0发布的高性能对象存储;
它与 Amazon S3云存储服务兼容;
使用 MinIO 构建用于机器学习,分析和应用程序数据工作负载的高性能基础架构;
```

### 官网地址(中文)

> https://www.minio.org.cn/

### 文档地址

> http://docs.minio.org.cn/docs/master/setup-nginx-proxy-with-minio

## 使用 Docker 搭建 minio 服务

- GNU / Linux和macOS

> docker run -p 9000:9000 \
> --name minio1 \
> -v /mnt/data:/data \
> -e "MINIO_ROOT_USER=AKIAIOSFODNN7EXAMPLE" \
> -e "MINIO_ROOT_PASSWORD=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY" \
> minio/minio server /data

- Windows

> docker run -p 9000:9000 \
> --name minio1 \
> -v D:\data:/data \
> -e "MINIO_ROOT_USER=AKIAIOSFODNN7EXAMPLE" \
> -e "MINIO_ROOT_PASSWORD=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY" \
> minio/minio server /data

- [注]

```text
MINIO_ROOT_USER: 为用户key
MINIO_ROOT_PASSWORD: 为用户密钥
```

### 当启动后就可以访问minio的图形化界面了

> 在浏览器访问 http://localhost:9000

## 搭建springboot 环境

### 添加 Maven 依赖

```text
<!-- 操作minio的java客户端-->
<dependency>
    <groupId>io.minio</groupId>
    <artifactId>minio</artifactId>
    <version>8.2.1</version>
</dependency>
```

### 添加配置

```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
  #minio配置
  minio:
    access-key: AKIAIOSFODNN7EXAMPLE      #key就是docker初始化是设置的，密钥相同
    secret-key: wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
    url: http://localhost:9000
    bucket-name: wdhcr # 文件桶名称
  thymeleaf:
```

### 创建配置类

```java

@Configuration
@ConfigurationProperties(prefix = "spring.minio")
@Data
public class MinioConfiguration {
    private String accessKey;

    private String secretKey;

    private String url;

    private String bucketName;

    @Bean
    public MinioClient minioClient() {

        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}
```