package com.amazon;

import java.io.File;
import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

public class UploadObjectSingleOperation {
	private static String bucketInputName = "videosamba/input";
	//private static String bucketOutputName = "videosamba/output";
	//private static String keyName = "video";
	
	public void upload(File file) throws IOException {
		AWSCredentials credentials = new BasicAWSCredentials("AKIAI6P2QTS6BRVAUZPQ", "x5fWrkFrOd5zIMhwuWMkGwI5ZcSed7ok9WkAHxyW");
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("us-east-2").withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        
        try {
			s3Client.putObject(new PutObjectRequest(bucketInputName, file.getName(), file));
        	
        	//S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketOutputName, keyName));
        	System.out.println("teste");

         } catch (AmazonServiceException ase) {
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}