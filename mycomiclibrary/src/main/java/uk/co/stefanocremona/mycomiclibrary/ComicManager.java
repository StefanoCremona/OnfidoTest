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
    //private ComicManager(){}

    public static void getComic(Activity callerActivity){
        if(myCallerActivity==null)
            myCallerActivity = callerActivity;
        if(myCallBackInterface==null)
            myCallBackInterface = (ComicCallbackInterface) callerActivity;

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(myCallerActivity);

        LayoutInflater inflater = myCallerActivity.getLayoutInflater();
        View myDialogView = inflater.inflate(R.layout.comic_layout, null);
        Button thumbsUp     = (Button) myDialogView.findViewById(R.id.thumbsUpButton);
        Button thumbsDown   = (Button) myDialogView.findViewById(R.id.thumbsDownButton);
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
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(myDialogView);
// 3. Get the AlertDialog from create()
        myAlertDialog = builder.create();
        myAlertDialog.show();
        ComicTask myTask = new ComicTask(myCallerActivity, myDialogView);
        myTask.execute(new String[]{"http://xkcd.com/info.0.json"});
        //return myComicManager;
    }

    public static ComicCallbackInterface getMyCallBackInterface(){
        return myCallBackInterface;
    }
    public static Activity getMyCallingClass(){
        return myCallerActivity;
    }
}
