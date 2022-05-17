/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metatech.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Abn
 */
public class TextToEmotion {
    public static String Check(String text)
    {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, text);
            
            Request request = new Request.Builder()
                    .url("https://api.apilayer.com/text_to_emotion")
                    .addHeader("apikey", "nzUzj1fBostUQxs5TBSkB507MvPy0Q2K")
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();
              
            JSONObject js = new JSONObject(response.body().string()) ;
            System.out.println(js);
            
            return "Happy :" + String.valueOf( js.getDouble("Happy")) + "Sad :" + String.valueOf(js.getDouble("Sad")) ;
       
        } catch (IOException | JSONException ex) {
            Logger.getLogger(TextToEmotion.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
