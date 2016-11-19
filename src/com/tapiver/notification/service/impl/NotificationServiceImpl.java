/**
 * 
 */
package com.tapiver.notification.service.impl;

import java.util.HashMap;
import java.util.Map;

//import com.amazonaws.regions.Region;
//import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
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

		String subject = parameter.getSubject();
		String message = parameter.getMessage(); //body; Explore HTML option
        String destinationEmailAddress = parameter.getEmailAddress();//"+65XXXXXXXX";
		
        boolean emailSentSuccessfully = false;
        
        //Attempt sms delivery with Amazon SNS
        try{
        	sendEmailNotificationWithAWS(NotificationConfigurationConstants.EMAIL_FROM_ADDRESS,destinationEmailAddress,subject, message);
        	emailSentSuccessfully = true;
        }catch(Exception e){
        	System.out.println("SMS notification was not successful with AWS. Attempting with Twilio...");
        }
        
        if(!emailSentSuccessfully){
        	//TODO RAJ: Twilio implementation pending
        	//Attempt with Twilio
        }
		return emailSentSuccessfully;
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
	
	private void sendEmailNotificationWithAWS(String fromAddress, String toAddress, String subject, String body){
		Destination destination = new Destination().withToAddresses(new String[]{toAddress});
		
		// Create the subject and body of the message.
        Content subj = new Content().withData(subject);
        Content textBody = new Content().withData(body);
        Body bodyMessage = new Body().withText(textBody);
        
        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subj).withBody(bodyMessage);

        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(fromAddress).withDestination(destination).withMessage(message);

/*        try {
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

            BasicAWSCredentials credentials = null;
            try {
                credentials = new BasicAWSCredentials("access_key_id", "secret_key_id"); // TODO RAJ: Need to input the right credentials
            } catch (Exception e) {
                //TODO RAJ: Handle exception
            }

            // Instantiate an Amazon SES client, which will make the service call with the supplied AWS credentials.
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(credentials);

            Region REGION = Region.getRegion(Regions.US_EAST_1); //SG doesnt have email privileges
            client.setRegion(REGION);

            // Send the email.
            client.sendEmail(request);
            System.out.println("Email sent!");

        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }*/
        
	}
	
/*	public static void main(String[] args) {
        String message = "Your login PIN for Tapiver application is 123456";
        String phoneNumber = "+6592704773";
        new NotificationServiceImpl().sendSMSMessageWithAWS(message, phoneNumber);
	}*/
}
