package uk.co.stefanocremona.onfidotest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity myActivity = this;
        setContentView(uk.co.stefanocremona.mycomiclibrary.R.layout.activity_main);

        new uk.co.stefanocremona.mycomiclibrary.tasks.ComicTask(this).execute(new String[]{"http://xkcd.com/info.0.json"});
    }
}
