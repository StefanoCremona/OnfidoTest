package uk.co.stefanocremona.mycomiclibrary.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;

import uk.co.stefanocremona.mycomiclibrary.R;

/**
 * Created by stefanocremona on 22/05/16.
 */
public class GUIUtils {
    private static String LOG_TAG = GUIUtils.class.getSimpleName();

    public static void showDialog(Activity activity, String title, String message){
        Log.d(LOG_TAG, "Input: "+title+"|"+message);
        LayoutInflater inflater = activity.getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
