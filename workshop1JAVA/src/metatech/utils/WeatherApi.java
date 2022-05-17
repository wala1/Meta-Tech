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
import org.json.JSONObject;

/**
 *
 * @author Abn
 */
public class WeatherApi {
    
    public static int getWeather()
    {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            
            Request request = new Request.Builder()
                    .url("http://api.weatherstack.com/current?access_key="
                            + "0da009493602702296a546abb848947c" // api key
                            + "&query=Tunisie")
                    .method("POST" , body)
                    .build();
            Response response = client.newCall(request).execute();
              
            JSONObject json = new JSONObject(response.body().string()) ;
            JSONObject js = json.getJSONObject("current");
            System.out.println(js);
            return js.getInt("temperature");
           
        } catch (Exception ex) {
            Logger.getLogger(WeatherApi.class.getName()).log(Level.SEVERE, null, ex);
        }
         return 0;
    }
    
}
