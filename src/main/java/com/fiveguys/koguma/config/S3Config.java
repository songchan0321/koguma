package com.fiveguys.koguma.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Getter
public class S3Config {

    private final String endPoint;
    private final String regionName;
    private final String accessKey;
    private final String secretKey;
    private final String bucketName;
    private final AmazonS3 s3;

    public S3Config(
            @Value("${ncloud.endPoint}") String endPoint,
            @Value("${ncloud.regionName}") String regionName,
            @Value("${ncloud.accessKey}") String accessKey,
            @Value("${ncloud.secretKey}") String secretKey,
            @Value("${ncloud.bucketName}") String bucketName) {
        this.endPoint = endPoint;
        this.regionName = regionName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        this.s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }
}