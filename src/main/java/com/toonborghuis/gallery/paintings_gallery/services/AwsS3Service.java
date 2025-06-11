package com.toonborghuis.gallery.paintings_gallery.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@AllArgsConstructor
public class AwsS3Service {

    private final S3Client s3Client;
    private final String bucketName = "paintings-gallery-bucket";

    public String uploadFileToS3(MultipartFile file) throws IOException {
        String key = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // Return the public URL (if bucket is public or you generate pre-signed URL)
        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }

    public void deleteFileFromS3(String fileUrl) {
        // Extract key from URL, e.g. remove "https://bucket-name.s3.amazonaws.com/"
        String key = fileUrl.substring(fileUrl.indexOf(".com/") + 5);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }
    
}
