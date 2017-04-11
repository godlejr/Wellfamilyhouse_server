package com.demand.server.well_family_house.common.fcmDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DataMessage {
	private String to;
	private String priority;
	private Data data;

	public DataMessage() {
		super();
		this.priority = "high";
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public DataMessage(String to, String priority, Data data) {
		super();
		this.to = to;
		this.priority = priority;
		this.data = data;
		this.priority = "high";

	}

}
