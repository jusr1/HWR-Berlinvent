package de.rasi.org.berlinvent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    public TextView t1;
    Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        t1 = (TextView) findViewById(R.id.textmain);
        t1.setOnClickListener(this);

        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(MainMenu.this);

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(MainMenu.this);

        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(MainMenu.this);
    }
    @Override
    public void onClick (View v) {
        if (v.getId() == R.id.button1)
            t1.setText("Button 1 gedrückt");
        if (v.getId() == R.id.button2)
            t1.setText("Button 2 gedrückt");
        if (v.getId() == R.id.button3)
            t1.setText("Button 3 gedrückt");
    }


}
