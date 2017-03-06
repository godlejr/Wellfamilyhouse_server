package com.demand.server.well_family_house.util;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demand.server.well_family_house.dto.FirebaseResponse;
import com.demand.server.well_family_house.dto.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class AndroidPushNotification {
	private static final String FIREBASE_SERVER_KEY = "AAAAF8eRpe0:APA91bE9onTVWmVx3HgQWr2Xqz9pBuw_13iRNbO1JgtaGF0z19bLWexNuOvG7ePtWoUb7lXjS-ncmFoEmQrx9JfAo_SnG6QAJT8qSgY7UGj2f1nWPLfpNilRrRDNngITm_jZR-Z6vLDR";

	@Async
	public CompletableFuture<FirebaseResponse> send(Message message){
		RestTemplate restTemplate = new RestTemplate();
				
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
		httpHeaders.setContentType(new MediaType("application","json"));
		
		
		HttpEntity<Message> requestEntity = new HttpEntity<Message>(message, httpHeaders);
	
		FirebaseResponse firebaseResponse = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", requestEntity,
				FirebaseResponse.class);
		
		return CompletableFuture.completedFuture(firebaseResponse);	
	}

	
}
