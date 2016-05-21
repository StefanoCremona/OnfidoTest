package uk.co.stefanocremona.mycomiclibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by stefanocremona on 21/05/16.
 */
public class HTTPUtils {

    public final static String LOG_TAG = HTTPUtils.class.getSimpleName();

    //Check the connectivity Status of the app
    public static boolean myInternetConnectionChecker(Activity myActivity){
        ConnectivityManager connMgr = (ConnectivityManager)
                myActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    public static HTTPResponce downloadUrl(String myurl, Map<String, String> mapRequestProperties, String jsonPostParams) throws IOException {
        InputStream is = null;
        HttpURLConnection conn;
        String retValString;
        int     readTimeout = 10000; /* milliseconds */
        int     connTimeout = 15000; /* milliseconds */

        URL url = new URL(myurl);
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(readTimeout );
        conn.setConnectTimeout(connTimeout);
        conn.setDoInput(true);

        //Write the Headers
        if(mapRequestProperties!=null)
            for (Map.Entry<String, String> e : mapRequestProperties.entrySet()) {
                //System.out.println(e.getKey() + ": " + e.getValue());
                //conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty(e.getKey(), e.getValue());
            }

        if (jsonPostParams == null) {
            Log.d(LOG_TAG, "Trying to GET: "+myurl);
            conn.setRequestMethod("GET");
        }else {
            Log.d(LOG_TAG, "Trying to POST: "+myurl);
            conn.setRequestMethod("POST");

            // For POST only - START
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            //os.write(builder.build().getEncodedQuery().getBytes());
            Log.d(LOG_TAG, "Sending json: "+jsonPostParams);
            os.write(jsonPostParams.getBytes("UTF-8"));
            os.flush();
            os.close();
        }
        // Starts the query
        conn.connect();
        int response = conn.getResponseCode();
        Log.d(LOG_TAG, "The response is: " + response);

        if (response == HttpURLConnection.HTTP_OK || response == HttpURLConnection.HTTP_CREATED ){
            is = conn.getInputStream();
            retValString = readIt(is); // Convert the InputStream into a string
        } else {
            Log.e(LOG_TAG, "Error in retriving the response: "+response);
            retValString =  null;
        }

        if (conn != null) {
            conn.disconnect();
        }
        if (is != null) {
            is.close();
        }

        return  new HTTPResponce(response, retValString);
    }

    // Reads an InputStream and converts it to a String.
    public static String readIt(InputStream inputStream){
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            // Nothing to do.
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
        }  catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return buffer.toString();
    }
}

