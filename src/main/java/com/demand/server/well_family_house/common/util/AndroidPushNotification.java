package com.demand.server.well_family_house.common.util;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demand.server.well_family_house.common.dto.FirebaseResponse;
import com.demand.server.well_family_house.common.fcmDto.DataMessage;
import com.demand.server.well_family_house.common.fcmDto.NotificationMessage;


@Service
public class AndroidPushNotification {
	private static final String FIREBASE_SERVER_KEY = "AAAAF8eRpe0:APA91bE9onTVWmVx3HgQWr2Xqz9pBuw_13iRNbO1JgtaGF0z19bLWexNuOvG7ePtWoUb7lXjS-ncmFoEmQrx9JfAo_SnG6QAJT8qSgY7UGj2f1nWPLfpNilRrRDNngITm_jZR-Z6vLDR";
	private static final String FIREBASE_ENDPOINT ="https://fcm.googleapis.com/fcm/send";
	@Async
	public CompletableFuture<FirebaseResponse> insertDataMessage(DataMessage message){
		RestTemplate restTemplate = new RestTemplate();
				
		HttpHeaders httpHeaders = getHttpHeaders();
		
		HttpEntity<DataMessage> requestEntity = new HttpEntity<DataMessage>(message, httpHeaders);
	
		FirebaseResponse firebaseResponse = restTemplate.postForObject(FIREBASE_ENDPOINT, requestEntity,
				FirebaseResponse.class);
		
		return CompletableFuture.completedFuture(firebaseResponse);	
	}
	
	@Async
	public CompletableFuture<FirebaseResponse> insertNotificationMessage(NotificationMessage message){
		RestTemplate restTemplate = new RestTemplate();
				
		HttpHeaders httpHeaders = getHttpHeaders();
		
		HttpEntity<NotificationMessage> requestEntity = new HttpEntity<NotificationMessage>(message, httpHeaders);
	
		FirebaseResponse firebaseResponse = restTemplate.postForObject(FIREBASE_ENDPOINT, requestEntity,
				FirebaseResponse.class);
		
		return CompletableFuture.completedFuture(firebaseResponse);	
	}

	public HttpHeaders getHttpHeaders(){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
		httpHeaders.setContentType(new MediaType("application","json"));
		
		return httpHeaders;
	}
	
}
