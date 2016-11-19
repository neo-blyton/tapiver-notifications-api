/**
 * 
 */
package com.tapiver.notification.constants;

/**
 * @author raj
 *
 */
public interface NotificationConfigurationConstants {
	
	//AWS SMS Notification
	static final String AWS_SenderID = "AWS.SNS.SMS.SenderID";
	static final String AWS_MaxPrice = "AWS.SNS.SMS.MaxPrice";
	static final String AWS_SMSType = "AWS.SNS.SMS.SMSType";
	
	static final String AWS_SenderID_Val = "TAPIVER"; 
	static final String AWS_MaxPrice_Val = "0.50"; //TODO RAJ: To check with Erwin
	static final String AWS_SMSType_Val = "Transactional";
	
	
	//AWS SES Email Notification
	static final String EMAIL_FROM_ADDRESS = "NOTIFICATIONS@TAPIVER.COM"; 
    
}
