package com.demand.server.well_family_house.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class ImageS3 {
	private static final String ACCESS_KEY = "AKIAIUGMLWN3S757JDVA";
	private static final String SECRET_KEY = "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";
	
	private static final String END_POINT_URL = "http://s3.ap-northeast-2.amazonaws.com/demand.files";// e.g
	// http://s3.amazonaws.com
	private static final String BUCKET = "demand.files";
	//private static final String IMAGE_LOCATION = "xxx";
	//private static final String S3_CACHE = "xxx"; // e.g 60
	private static AmazonS3 s3;

	public static void uploadImageToAWSS3(String base64Data, String location) throws IllegalStateException, IOException {
		String fileName = null;
		try {
			
			 byte[] base64Image=Base64.decodeBase64(base64Data);

//	            String directory=ImageS3.class.getResource("").getPath()+"/sample.jpg";
//
//	            
//	            new FileOutputStream(directory).write(imageByte);

//			byte[] base64Image = org.apache.commons.codec.binary.Base64
//					.decodeBase64((base64Data).getBytes());
			InputStream imagefile = new ByteArrayInputStream(base64Image);

			AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
			//java.security.Security.setProperty("networkaddress.cache.ttl", S3_CACHE);
			s3 = new AmazonS3Client(credentials);
			s3.setEndpoint(END_POINT_URL);

			// InputStream stream = multipartFile.getInputStream();
			fileName = System.currentTimeMillis() + "_";
			ObjectMetadata objectMetadata = new ObjectMetadata();
			PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, location + "/" + fileName, imagefile,
					objectMetadata);
			// skip if do not want to access the image directly from S3
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			// skip if do not want to access the image directly from S3
			s3.putObject(putObjectRequest);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}
}
