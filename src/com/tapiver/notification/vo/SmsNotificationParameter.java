package com.tapiver.notification.vo;

public class SmsNotificationParameter {
	private String message;
	private String phoneNumber;
	private boolean isHighPriority;

	public SmsNotificationParameter(String message, String phoneNumber, boolean isHighPriority) {
		this.message = message;
		this.phoneNumber = phoneNumber;
		this.isHighPriority = isHighPriority;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isHighPriority() {
		return isHighPriority;
	}

	public void setHighPriority(boolean isHighPriority) {
		this.isHighPriority = isHighPriority;
	}
}