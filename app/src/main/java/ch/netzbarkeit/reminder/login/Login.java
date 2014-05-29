package ch.netzbarkeit.reminder.login;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djones on 25/05/14.
 */
public class Login {

    private static String APIURL = "http://192.168.1.144:8000/";

    private static String APIURL_LOGIN = APIURL+"login/login/";
    private static String APIURL_CHECKLOGIN = APIURL+"login/check_login/";
    private static String APIURL_LOGOUT = APIURL+"accounts/logout/";


    public Boolean getLoginState(){
        return this.checkLogin();
    }

    public Boolean doLogin(String u, String p) {
        return doLoginHttp(u, p);
    }

    public void doLogout(){
        this.doLogoutHttp();
    }

    private Boolean doLoginHttp(String u, String p) {
        // set strict mode for http request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(this.APIURL_LOGIN);

            // Add data array to request
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", u));
            nameValuePairs.add(new BasicNameValuePair("password", p));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute Http
            ResponseHandler<String> responseHandler=new BasicResponseHandler();
            String response = httpclient.execute(httppost, responseHandler);
            if(response.equals("True")){
                return true;
            }
        } catch (Exception e) {
            Log.e("[POST REQUEST]", "Network exception", e);
        }
        return false;
    }

    private Boolean checkLogin() {
        // set strict mode for http request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(this.APIURL_CHECKLOGIN);

            // Execute Http
            ResponseHandler<String> responseHandler=new BasicResponseHandler();
            String response = httpclient.execute(httppost, responseHandler);
            if(response.equals("True")){
                return true;
            }

        } catch (Exception e) {
            Log.e("[GET REQUEST]", "Network exception", e);
        }
        return false;
    }

    private void doLogoutHttp() {
        // set strict mode for http request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(this.APIURL_LOGOUT);

            // Execute Http
            ResponseHandler<String> responseHandler=new BasicResponseHandler();
            String response = httpclient.execute(httppost, responseHandler);
        } catch (Exception e) {
            Log.e("[GET REQUEST]", "Network exception", e);
        }
    }
}
