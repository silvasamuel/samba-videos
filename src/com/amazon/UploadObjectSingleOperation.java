package com.amazon;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadObjectSingleOperation {
	private String videoUrl;
	private static String bucketInputName = "videosamba/input";
	private static String bucketOutputName = "videosamba/output";
	
	public void upload(String fileName, byte [] file) throws IOException, InterruptedException {
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJXKGQCGCC3UFNRDA", "dfDgk7yEFPHrUEgssWw/CwYuwiCMy1mXKFOjCUeH");
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("us-east-2").withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        
        try {
        	Long contentLength = Long.valueOf(file.length);
        	InputStream inputStream = new ByteArrayInputStream(file);
        	ObjectMetadata metadata = new ObjectMetadata();
        	metadata.setContentLength(contentLength);
        	
			s3Client.putObject(new PutObjectRequest(bucketInputName, fileName, inputStream, metadata));
			
			String outputFileName = fileName.substring(0, fileName.lastIndexOf('.')).concat(".mp4");
			
			java.util.Date expiration = new java.util.Date();
			long milliSeconds = expiration.getTime();
			milliSeconds += 1000 * 60 * 60; // Add 1 hour.
			expiration.setTime(milliSeconds);

			GeneratePresignedUrlRequest generatePresignedUrlRequest = 
				    new GeneratePresignedUrlRequest(bucketOutputName, outputFileName);
			generatePresignedUrlRequest.setMethod(HttpMethod.GET); 
			generatePresignedUrlRequest.setExpiration(expiration);
			
			//boolean isSearching = true;
			videoUrl = "";
			
			//while(isSearching) {
			//  setTimeout(() -> {    
	            	URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest); 	                
	            	videoUrl = url.toString();
	            	//  }, 1000);
	            
	            //if(videoUrl != "") {
	            	//break;
            		//	}
			//}

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
	
    public void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
}