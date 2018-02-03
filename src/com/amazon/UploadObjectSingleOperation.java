package com.amazon;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class UploadObjectSingleOperation {
	private InputStream video;
	private static String bucketInputName = "videosamba/input";
	private static String bucketOutputName = "videosamba/output";
	
	public void upload(String fileName, byte [] file) throws IOException, InterruptedException {
		FacesContext context = FacesContext.getCurrentInstance();
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJXKGQCGCC3UFNRDA", "dfDgk7yEFPHrUEgssWw/CwYuwiCMy1mXKFOjCUeH");
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("us-east-2").withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        
        try {
        	Long contentLength = Long.valueOf(file.length);
        	InputStream inputStream = new ByteArrayInputStream(file);
        	ObjectMetadata metadata = new ObjectMetadata();
        	metadata.setContentLength(contentLength);
        	
			s3Client.putObject(new PutObjectRequest(bucketInputName, fileName, inputStream, metadata));
			
			String outputFileName = fileName.substring(0, fileName.lastIndexOf('.')).concat(".mp4");
			S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketOutputName, outputFileName));
			
			S3ObjectInputStream s3is = s3object.getObjectContent();
		    FileOutputStream fos = new FileOutputStream(new File("D:\\" + outputFileName));
		    byte[] read_buf = new byte[1024];
		    int read_len = 0;
		    while ((read_len = s3is.read(read_buf)) > 0) {
		        fos.write(read_buf, 0, read_len);
		    }
		    s3is.close();
		    fos.close();
		    context.responseComplete();
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

	public InputStream getVideo() {
		return video;
	}

	public void setVideo(InputStream video) {
		this.video = video;
	}
}