/**
 * 
 */
package com.tapiver.notification.service;

import com.tapiver.notification.service.exception.NotificationException;
import com.tapiver.notification.vo.EmailNotificationParameter;
import com.tapiver.notification.vo.SmsNotificationParameter;

/**
 * @author raj
 *
 */
public interface NotificationService {
	
	/**
	 * @param parameter TODO
	 * @return
	 * @throws NotificationException
	 */
	public boolean sendSmsNotification(SmsNotificationParameter parameter) throws NotificationException;
	
	/**
	 * @param parameter TODO
	 * @return
	 * @throws NotificationException
	 */
	public boolean sendEmailNotification(EmailNotificationParameter parameter) throws NotificationException;
}
