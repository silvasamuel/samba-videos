package com.amazon;

import java.io.File;
import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadObjectSingleOperation {
	private static String bucketName = "videosamba";
	private static String keyName = "AKIAIZVFBD7E63TFX2EA";
	private static String uploadFileName = "";
	
	public void upload() throws IOException {
		AWSCredentials credentials = new BasicAWSCredentials("AKIAIYWBURTWL2WSLNAQ", "/fiCK7x8j0aFGdx0Q0QlOEWPb3eJQvmxN+VNFXIw");
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        
        try {
            File file = new File(uploadFileName);
            s3Client.putObject(new PutObjectRequest(bucketName, keyName, file));

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