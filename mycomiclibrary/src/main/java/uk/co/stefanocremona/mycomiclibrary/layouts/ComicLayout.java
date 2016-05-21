package uk.co.stefanocremona.mycomiclibrary.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import uk.co.stefanocremona.mycomiclibrary.R;

/**
 * Created by stefanocremona on 21/05/16.
 */
public class ComicLayout extends LinearLayout {

    public ComicLayout(Context context) {
        super(context);
        inflateLayout(context);
        //Log.d("Prova", "Prova");
        //inflate(context, R.layout.prova, new LinearLayout(context));
    }

    public ComicLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayout(context);
        //addView(ll);
        //View myView = inflate(context, R.layout.prova, this);
        //LinearLayout ll = (LinearLayout) inflate(context, R.layout.activity_main, this);
    }

    private void inflateLayout(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.home, this);
        //TextView myTextView = new TextView(context);
        //myTextView.setText("Ciao Mamma");
        //addView(myTextView);
    }

}
