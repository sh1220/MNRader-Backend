package com.example.mnraderbackend.common.service;


import com.example.mnraderbackend.common.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.*;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class S3ImageService {

    private final S3Client s3Client; // AWS SDK v2용 클라이언트

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    private static final String ANIMAL_IMG_DIR = "animal/";

    public String saveAnimalImg(MultipartFile uploadFile) {
        validateUploadFile(uploadFile);
        return saveImg(uploadFile, ANIMAL_IMG_DIR);
    }

    private String saveImg(MultipartFile uploadFile, String dir) {
        if (uploadFile.isEmpty()) {
            log.debug("Upload file is empty");
            throw new UserException(INVALID_IMG);
        }

        String fileName = dir + UUID.randomUUID() + uploadFile.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(uploadFile.getContentType())
//                    .acl(ObjectCannedACL.PUBLIC_READ) // v2에서는 ObjectCannedACL 사용 => 현재 bucktet policy로 공개 설정
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(uploadFile.getInputStream(), uploadFile.getSize()));
        } catch (IOException e) {
            log.error("Error uploading to S3", e);
            throw new UserException(UPLOAD_FAIL);
        }

        // URL 반환 (S3Client는 getUrl 없음 → 직접 URL 구성 필요)
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, fileName);
    }

    public void deleteImage(String imgUrl) {
        try {
            String fileName = extractFileNameFromUrl(imgUrl);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            log.info("Image deleted successfully: {}", fileName);
        } catch (Exception e) {
            log.error("Error occurred while deleting image: {}", imgUrl, e);
            throw new UserException(UPLOAD_FAIL);
        }
    }

    private String extractFileNameFromUrl(String fileNameOrUrl) {
        if (fileNameOrUrl.startsWith("https://")) {
            try {
                URI uri = new URI(fileNameOrUrl);
                return uri.getPath().substring(1);
            } catch (URISyntaxException e) {
                throw new UserException(UPLOAD_FAIL);
            }
        } else {
            throw new UserException(INVALID_USER_DB_VALUE);
        }
    }

    private void validateUploadFile(MultipartFile file) {
        if (file == null) {
            throw new UserException(NULL_USER_VALUE);
        }
        if (file.isEmpty()) {
            throw new UserException(EMPTY_USER_VALUE);
        }
    }
}
