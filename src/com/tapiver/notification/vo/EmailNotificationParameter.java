package com.tapiver.notification.vo;

public class EmailNotificationParameter {
	private String subject;
	private String message;
	private String emailAddress;

	public EmailNotificationParameter(String subject, String message, String emailAddress) {
		this.subject = subject;
		this.message = message;
		this.emailAddress = emailAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}