package uk.co.stefanocremona.mycomiclibrary;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import uk.co.stefanocremona.mycomiclibrary.utils.HTTPResponce;
import uk.co.stefanocremona.mycomiclibrary.utils.HTTPUtils;

/**
 * Created by stefanocremona on 21/05/16.
 */
public class ComicTask extends AsyncTask<String, Void, HTTPResponce> {
    private static final String LOG_TAG = ComicTask.class.getSimpleName();
    private final Activity myCallerActivity;
    private final View myCustomView;

    public ComicTask(Activity callerActivity, View customView) {
        myCallerActivity = callerActivity;
        myCustomView = customView;
    }

    @Override
    protected HTTPResponce doInBackground(String... params) {
        // Usage
        // String[0]=Url
        // String[1]=jason post parameter

        Map myMap = new HashMap();
        myMap.put("Content-Type", "application/json; charset=UTF-8");
        try {
            if(params.length==1) {
                return HTTPUtils.downloadUrl(params[0], myMap, null);
            }else {
                //It makes a POST call
                Log.d(LOG_TAG, "Sending: "+params[1]);
                return HTTPUtils.downloadUrl(params[0], myMap, params[1]);
            }
        } catch(SocketTimeoutException e){
            return new HTTPResponce(522, "{\"error\":\"Connection Timed Out. Retry later.\"}");
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to retrieve web page. URL may be invalid.");
            e.printStackTrace();
            return null;
        }
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(HTTPResponce result) {
        String errMessage = "Unable to retrieve web page.  URL may be invalid.";
        String errTitle = "Error";
        boolean succeded = false;
        if (result==null) {
            Log.d(LOG_TAG, "onPostExecute|Output String null!");
            //GUIUtils.showDialog(myCallerActivity, errTitle, errMessage);
        }else {
            Log.d(LOG_TAG, "onPostExecute|Output length: "+result.getResponceBody().toString().length());
            //populateTheView(result, outPutField);
            if(result.getResponceCode()==200 || result.getResponceCode()==201) {
                succeded=true;
                try {
                    String title   = result.getResponceBody().getString("title");
                    String imgPath = result.getResponceBody().getString("img");
                    final String alt     = result.getResponceBody().getString("alt");

                    TextView myTitleTextView = (TextView) myCustomView.findViewById(R.id.titleid);
                    myTitleTextView.setText(title);
                    ImageView myImage = (ImageView) myCustomView.findViewById(R.id.imageid);
                    if (myImage != null && imgPath!=null) {
                        Picasso.with(myCallerActivity)
                                .load(imgPath)
                                .into(myImage);
                        myImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast myToast = Toast.makeText(myCallerActivity, alt, Toast.LENGTH_SHORT);
                                myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                myToast.show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "onPostExecute|Error: "+e.getMessage());
                    e.printStackTrace();
                }
            } else {

                try {
                    errMessage  = result.getResponceBody().getString("error");
                    errTitle    = "Error: "+result.getResponceCode();
                } catch (JSONException e) {
                    errTitle="Error: "+result.getResponceCode();
                    e.printStackTrace();
                }

            }

        }
        //if(!succeded)
        //    GUIUtils.showDialog(myCallerActivity, errTitle, errMessage);
    }
}
