/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

//import static com.edu.esprit.entities.SmsApi.ACCOUNT_SID;

/**
 *
 * @author ihebx
 */
public class SmsApi {
          public static final String ACCOUNT_SID = "AC2aec24735743905f9787c482c8ab8ad3";
  public static final String AUTH_TOKEN = "ad2834aada2b0863970aa5f4016a663f";
  public static void sendSMS(String num,String text){
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(new PhoneNumber(num),
        new PhoneNumber("+18623622028"), 
        text).create();

    System.out.println(message.getSid());
  }
    
}
