package com.sushchenko.mystictourismapp.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sushchenko.mystictourismapp.config.ObjectStorageConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObjectStorageService {
    private final ObjectStorageConfig objectStorageConfig;

    public Set<String> uploadAttachments(List<byte[]> attachments) {
        Set<String> urls = new HashSet<>();
        AmazonS3 s3Client = getS3Client();
        for(byte[] attachBytes : attachments) {
            String url = saveAndGetUrl(attachBytes, s3Client);
            urls.add(url);
        }
        return urls;
    }
    public String uploadAttachment(byte[] attachment) {
        AmazonS3 s3Client = getS3Client();
        return saveAndGetUrl(attachment, s3Client);
    }
    private String saveAndGetUrl(byte[] attachment, AmazonS3 s3Client) {
        final String bucketName = objectStorageConfig.getBucketName();

        String fileName = generateUniqueName();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(attachment.length);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(attachment);
        s3Client.putObject(bucketName, fileName, inputStream, metadata);
        return s3Client.getUrl(bucketName, fileName).toExternalForm();
    }
    private AmazonS3 getS3Client() {
        final String accessKeyId = objectStorageConfig.getAccessKeyId();
        final String secretAccessKey = objectStorageConfig.getSecretAccessKey();

        final AmazonS3 s3Client;
        try {
            s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(
                                    "https://storage.yandexcloud.net",
                                    "ru-central1"
                            )
                    )
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey)))
                    .build();
        } catch (SdkClientException e) {
            throw new SdkClientException(e.getMessage());
        }
        return s3Client;
    }
    private String generateUniqueName() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
