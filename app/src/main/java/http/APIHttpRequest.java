package http;


import android.os.StrictMode;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class APIHttpRequest {

    public String requestHttp(String url) {
        String resonseBody = this.GsonHttpRequest(url);
        return resonseBody;
    }


    private String GsonHttpRequest(String url) {
        // set strict mode for http request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient httpclient = new DefaultHttpClient();

        try {
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            // Body contains your json string
            String responseBody = httpclient.execute(httpget, responseHandler);
            // .execute(httpget, responseHandler)
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
            return responseBody;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
            return "";
        }
    }

}
