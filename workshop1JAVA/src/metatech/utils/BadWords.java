/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metatech.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.parser.JSONParser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

/**
 *
 * @author Abn
 */
public class BadWords {
    
    public static String check(String Message)
    {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, Message);
            
            Request request = new Request.Builder()
                    .url("https://api.apilayer.com/bad_words?censor_character=*")
                    .addHeader("apikey", "TkHPxAmGODtg0Ado42uvuvsmRvWNDH4z")
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();
            
            JSONObject js = new JSONObject(response.body().string()) ;
            System.out.println(js.getString("censored_content"));
            
            return js.getString("censored_content");
        } catch (Exception ex) {
            Logger.getLogger(BadWords.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
