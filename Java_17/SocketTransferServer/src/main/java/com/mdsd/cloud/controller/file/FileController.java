package com.mdsd.cloud.controller.file;

import com.mdsd.cloud.controller.transfer.TransferService;
import com.mdsd.cloud.response.ResponseDto;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2024-08-11]
 */
@Slf4j
@Tag(name = "文件")
@RequestMapping(name = "文件", path = "/file")
@RestController
public class FileController {

    @Value("${minio.bucket-name}")
    String bucketName;

    MinioClient minioClient;

    TransferService transferService;

    public FileController(MinioClient minioClient, TransferService transferService) {

        this.minioClient = minioClient;
        this.transferService = transferService;
    }

    @Parameter(name = "file", required = true, schema = @Schema(type = "string", format = "binary"))
    @Operation(summary = "文件上传", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "multipart/form-data",
                    schema = @Schema(
                            implementation = MultipartFile.class
                    )

            )
    ))
    @PostMapping(name = "文件上传", path = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<ObjectWriteResponse> fileUpload(@RequestParam(value = "file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                PutObjectArgs build = PutObjectArgs.builder().bucket(bucketName).object(file.getName()).stream(file.getInputStream(), -1, 10485760).build();
                ObjectWriteResponse objectWriteResponse = minioClient.putObject(build);
                return ResponseDto.wrapSuccess(objectWriteResponse);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("上传文件为空!");
        }
        return ResponseDto.wrapSuccess();
    }

    @Operation(summary = "获取预签名 URL", description = "使用该 URL 直接上传文件到文件服务器 Minio,该 URL 包含了上传文件所需的权限和过期时间, 默认过期时间: 10min")
    @GetMapping(name = "获取预签名 URL", path = "/getUploadUrl/{fileName}")
    public ResponseDto<String> getUploadUrl(@PathVariable String fileName) {

        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder().bucket(bucketName).method(Method.PUT).expiry(10, TimeUnit.MINUTES).object(fileName).build();
        try {
            return ResponseDto.wrapSuccess(minioClient.getPresignedObjectUrl(build));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "获取文件下载链接", description = "该接口会返回用于下载文件的链接, 默认过期时间: 1hours")
    @GetMapping(name = "获取文件下载链接", path = "/getDownloadLink/{fileName}")
    public ResponseDto<String> getDownloadLink(@PathVariable String fileName) {

        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder().bucket(bucketName).method(Method.GET).expiry(1, TimeUnit.HOURS).object(fileName).build();
        try {
            return ResponseDto.wrapSuccess(minioClient.getPresignedObjectUrl(build));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
