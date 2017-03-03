package com.demand.server.well_family_house.util;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demand.server.well_family_house.dto.FirebaseResponse;


@Service
public class AndroidPushNotification {
	private static final String FIREBASE_SERVER_KEY = "AAAAF8eRpe0:APA91bE9onTVWmVx3HgQWr2Xqz9pBuw_13iRNbO1JgtaGF0z19bLWexNuOvG7ePtWoUb7lXjS-ncmFoEmQrx9JfAo_SnG6QAJT8qSgY7UGj2f1nWPLfpNilRrRDNngITm_jZR-Z6vLDR";

	@Async
	public CompletableFuture<FirebaseResponse> send(HttpEntity<String> entity){
		RestTemplate restTemplate = new RestTemplate();
		
		ArrayList<ClientHttpRequestInterceptor> interceptors= new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		
		restTemplate.setInterceptors(interceptors);

		FirebaseResponse firebaseResponse = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", entity,
				FirebaseResponse.class);
		
		return CompletableFuture.completedFuture(firebaseResponse);		
	}

	
}
