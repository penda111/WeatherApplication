package hk.edu.ouhk.weatherapplication.APIHandler;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.FndAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.RhrreadAPIHandler;

public class JsonHandlerThread extends Thread {
    private static final String TAG = "JsonHandlerThread";
    // URL to get contacts JSON file
    private static String jsonUrl;
    private String api;

    public JsonHandlerThread(String url, String api){
        jsonUrl = url;
        this.api = api;
    }


    public static String makeRequest() {
        String response = null;
        try {
            URL url = new URL(jsonUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = inputStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private static String inputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            }
        }
        return sb.toString();
    }

    public void run() {
        String contactStr = makeRequest();
        Log.e(TAG, "Response from url: " + contactStr + jsonUrl);

        if (contactStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(contactStr);

                switch(api)
                {
                    case "Moon":
                        Log.d(TAG, "Check jsonObject from Thread: " + jsonObj);
                        /*MoonAPIHandler moon = (MoonAPIHandler) object;
                        moon.replaceJsonObj(jsonObj);*/
                        MoonAPIHandler.jsonObject = jsonObj;
                        break;
                    case "Sun":
                        SunAPIHandler.jsonObject = jsonObj;
                        break;
                    case "Flw":
                        FlwAPIHandler.jsonObject = jsonObj;
                        FlwAPIHandler.getJsonData();
                        break;
                    case "Fnd":
                        FndAPIHandler.jsonObject = jsonObj;
                        FndAPIHandler.getJsonData();
                        break;
                    case "Rhrread":
                        RhrreadAPIHandler.jsonObject = jsonObj;
                        RhrreadAPIHandler.getJsonData();
                        break;

                }

                /*// Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("contacts");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");

                    // Phonee node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");


                    // Add contact (name, email, address) to contact list
                    ContactInfo.addContact(name, email, address, mobile);
                }*/
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}
