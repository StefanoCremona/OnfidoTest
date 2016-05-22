package uk.co.stefanocremona.onfidotest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import uk.co.stefanocremona.mycomiclibrary.ComicCallbackInterface;
import uk.co.stefanocremona.mycomiclibrary.ComicManager;

public class MainActivity extends AppCompatActivity {
    static TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView= (TextView) findViewById(R.id.text1);
    }

    //Method called by the push button
    public void callTheActivity(View view){
        final Activity myActivity = this;
        ComicManager.getComic(this, new ComicCallbackInterface() {
            @Override
            public void thumbsUpPressed() {
                Log.d("callTheActivity", "thumbsUpPressed");
                myTextView.setText(R.string.buttonUpPressed);
            }

            @Override
            public void thumbsDownPressed() {
                Log.d("callTheActivity", "thumbsDownPressed");
                myTextView.setText(R.string.buttonDownPressed);
            }
        });
    }
}
