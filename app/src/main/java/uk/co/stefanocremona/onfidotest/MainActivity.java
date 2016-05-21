package uk.co.stefanocremona.onfidotest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.co.stefanocremona.mycomiclibrary.tasks.ComicTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity myActivity = this;
        setContentView(R.layout.activity_main);

        ComicTask myTask = new ComicTask(this);
        myTask.execute(new String[]{"http://xkcd.com/info.0.json"});
    }
}
