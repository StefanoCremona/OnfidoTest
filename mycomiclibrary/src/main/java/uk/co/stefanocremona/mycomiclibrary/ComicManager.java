package uk.co.stefanocremona.mycomiclibrary;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by stefanocremona on 22/05/16.
 */
public class ComicManager {
    private static Activity myCallerActivity = null;
    private static ComicCallbackInterface myCallBackInterface = null;
    private static AlertDialog myAlertDialog;

    private ComicManager(){}

    public static void getComic(Activity callerActivity, ComicCallbackInterface callBackInterface){
        if(myCallerActivity==null)
            myCallerActivity = callerActivity;
        if(myCallBackInterface==null)
            myCallBackInterface = callBackInterface;

        // Open a Dialog for showing the data from the server
        AlertDialog.Builder builder = new AlertDialog.Builder(myCallerActivity);

        LayoutInflater inflater = myCallerActivity.getLayoutInflater();
        View myDialogView = inflater.inflate(R.layout.comic_layout, null);
        Button thumbsUp     = (Button) myDialogView.findViewById(R.id.thumbsUpButton);
        Button thumbsDown   = (Button) myDialogView.findViewById(R.id.thumbsDownButton);

        //Set the listeners to perform the callback to the calling interface
        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.cancel();
                myCallBackInterface.thumbsUpPressed();
            }
        });
        thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.cancel();
                myCallBackInterface.thumbsDownPressed();
            }
        });

        builder.setView(myDialogView);

        myAlertDialog = builder.create();
        myAlertDialog.show();

        //Call the service
        ComicTask myTask = new ComicTask(myCallerActivity, myDialogView);
        String myUrl = callerActivity.getString(R.string.serviceUrl);
        myTask.execute(new String[]{myUrl});
    }

    /*public static ComicCallbackInterface getMyCallBackInterface(){
        return myCallBackInterface;
    }
    public static Activity getMyCallingClass(){
        return myCallerActivity;
    }*/
}
