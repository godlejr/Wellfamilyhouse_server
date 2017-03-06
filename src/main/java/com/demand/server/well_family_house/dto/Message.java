package com.demand.server.well_family_house.dto;

import java.util.ArrayList;

public class Message {
	private ArrayList<String> to;
	private Data data;

	public Message() {
		super();
	}

	public Message(ArrayList<String> to, Data data) {
		super();
		this.to = to;
		this.data = data;
	}

	public ArrayList<String> getTo() {
		return to;
	}

	public void setTo(ArrayList<String> to) {
		this.to = to;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
