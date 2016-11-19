/**
 * 
 */
package com.tapiver.notification.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.tapiver.notification.constants.NotificationConfigurationConstants;
import com.tapiver.notification.service.NotificationService;
import com.tapiver.notification.service.exception.NotificationException;
import com.tapiver.notification.vo.EmailNotificationParameter;
import com.tapiver.notification.vo.SmsNotificationParameter;

/**
 * @author raj
 *
 */
public class NotificationServiceImpl implements NotificationService {

	/**
	 * 
	 */
	public NotificationServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tapiver.notification.service.NotificationService#sendSmsNotification(com.tapiver.notification.vo.SmsNotificationParameter)
	 */
	@Override
	public boolean sendSmsNotification(SmsNotificationParameter parameter) throws NotificationException {
		String message = parameter.getMessage();
        String phoneNumber = parameter.getPhoneNumber();//"+65XXXXXXXX";
		
        boolean smsSentSuccessfully = false;
        
        //Attempt sms delivery with Amazon SNS
        try{
        	sendSMSMessageWithAWS(message,phoneNumber);
        	smsSentSuccessfully = true;
        }catch(Exception e){
        	System.out.println("SMS notification was not successful with AWS. Attempting with Twilio...");
        }
        
        if(!smsSentSuccessfully){
        	//TODO RAJ: Twilio implementation pending
        	//Attempt with Twilio
        }
		return smsSentSuccessfully;
	}

	/* (non-Javadoc)
	 * @see com.tapiver.notification.service.NotificationService#sendEmailNotification(com.tapiver.notification.vo.EmailNotificationParameter)
	 */
	@Override
	public boolean sendEmailNotification(EmailNotificationParameter parameter) throws NotificationException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 * @return
	 */
	private Map<String, MessageAttributeValue> setupSmsAttributesForAWS(){
		Map<String, MessageAttributeValue> smsAttributes =
		        new HashMap<String, MessageAttributeValue>();
		smsAttributes.put(NotificationConfigurationConstants.AWS_SenderID, new MessageAttributeValue()
		        .withStringValue(NotificationConfigurationConstants.AWS_SenderID_Val) 
		        .withDataType("String"));
		smsAttributes.put(NotificationConfigurationConstants.AWS_MaxPrice, new MessageAttributeValue()
		        .withStringValue(NotificationConfigurationConstants.AWS_MaxPrice_Val) 
		        .withDataType("Number"));
		smsAttributes.put(NotificationConfigurationConstants.AWS_SMSType, new MessageAttributeValue()
		        .withStringValue(NotificationConfigurationConstants.AWS_SMSType_Val) 
		        .withDataType("String"));
		
		return smsAttributes;
	}
	
	/**
	 * 
	 * @param message
	 * @param phoneNumber
	 */
	private void sendSMSMessageWithAWS(String message, String phoneNumber) {
		Map<String, MessageAttributeValue> smsAttributes = setupSmsAttributesForAWS();
		AmazonSNSClient snsClient = new AmazonSNSClient();
		
	        PublishResult result = snsClient.publish(new PublishRequest()
	                        .withMessage(message)
	                        .withPhoneNumber(phoneNumber)
	                        .withMessageAttributes(smsAttributes));
	        
	        System.out.println(result.toString()); 
	}
	
	public static void main(String[] args) {
        String message = "Your login PIN for Tapiver application is 123456";
        String phoneNumber = "+6592704773";
        new NotificationServiceImpl().sendSMSMessageWithAWS(message, phoneNumber);
}
}
