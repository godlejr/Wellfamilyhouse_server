package com.demand.server.well_family_house.common.fcmDto;

public class NotificationMessage {
	private String to;
	private String priority;
	private Notification notification;

	public NotificationMessage() {
		super();
	}

	public NotificationMessage(String to, String priority, Notification notification) {
		super();
		this.to = to;
		this.priority = priority;
		this.notification = notification;
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

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

}
