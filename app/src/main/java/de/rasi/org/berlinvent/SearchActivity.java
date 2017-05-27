package de.rasi.org.berlinvent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Justin on 21.03.2017.
 * Noch nicht eingepflegt!
 * Ausblick für 3. Aktivität
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    public String apikey = "QNgn6PCmNLN9HF9J";
    public TextView t1;
    Button btn1, btn2, btn3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        t1 = (TextView) findViewById(R.id.textmain);
        t1.setOnClickListener(this);

        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(SearchActivity.this);

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(SearchActivity.this);

        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(SearchActivity.this);
    }


    @Override
    public void onClick(View v) {

    }
}
