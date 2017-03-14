package com.demand.server.well_family_house.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.demand.server.well_family_house.flag.LogFlag;


@Service
public class AwsS3Connection {

	private final String ACCESS_KEY = "AKIAIUGMLWN3S757JDVA";
	private final String SECRET_KEY = "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";
	private final String END_POINT_URL = "http://s3.ap-northeast-2.amazonaws.com";
	private final String BUCKET = "demand.files";

	private static final Logger logger = LoggerFactory.getLogger(AwsS3Connection.class);

	public AwsS3Connection() {
		super();
	}

	public  String uploadFileToAWSS3(String base64Data, String location, String ext)
			throws IllegalStateException, IOException {
		AmazonS3 s3;
		String fileName = null;

		try {
			byte[] base64Image = org.apache.commons.codec.binary.Base64.decodeBase64(base64Data);
			InputStream imagefile = new ByteArrayInputStream(base64Image);
			AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
			s3 = new AmazonS3Client(credentials);
			s3.setEndpoint(END_POINT_URL);
			fileName = System.currentTimeMillis() + "";
			ObjectMetadata objectMetadata = new ObjectMetadata();

			PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, location + "/" + fileName + ext, imagefile,
					objectMetadata);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			s3.putObject(putObjectRequest);

		} catch (AmazonServiceException e) {
			log(e);
		}

		return fileName;
	}

	public  void deleteFileFromAWSS3(String location, String fileName, String ext) {
		AmazonS3 s3;

		AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		s3 = new AmazonS3Client(credentials);
		s3.setEndpoint(END_POINT_URL);

		try {
			s3.deleteObject(new DeleteObjectRequest(BUCKET, location + "/" + fileName + ext));
		} catch (AmazonServiceException ase) {
			log(ase);
		} catch (AmazonClientException ace) {
			log(ace);
		}
	}
	
	
	public static void log(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		String className = ste[0].getClassName();
		String methodName = ste[0].getMethodName();
		int lineNumber = ste[0].getLineNumber();
		String fileName = ste[0].getFileName();

		if (LogFlag.printFlag) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception: " + e.getMessage());
				logger.info(className + "." + methodName + " " + fileName + " " + lineNumber + " " + "line");
			}
		}
	}

}
