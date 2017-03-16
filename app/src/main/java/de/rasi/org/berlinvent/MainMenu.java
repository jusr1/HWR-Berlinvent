package de.rasi.org.berlinvent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.evdb.javaapi.APIConfiguration;
import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import com.evdb.javaapi.data.SearchResult;
import com.evdb.javaapi.data.request.EventSearchRequest;
import com.evdb.javaapi.operations.EventOperations;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    public String apikey="QNgn6PCmNLN9HF9J";
    public TextView t1;
    Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        try {
            APIConfiguration apic = new APIConfiguration();
            apic.setApiKey(apikey);

        }catch(Exception e){
            System.out.println("key fehler");
        }

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
            t1.setText(getEventRequest().toString());
        if (v.getId() == R.id.button2)
            t1.setText("Button 2 gedrückt");
        if (v.getId() == R.id.button3)
            t1.setText("Button 3 gedrückt");
    }
    public ArrayList<String> getEventRequest(){
        EventOperations eo = new EventOperations();
        EventSearchRequest esr = new EventSearchRequest();

        esr.setLocation("Berlin");
        esr.setDateRange("2017031600-2017032400");
        esr.setPageSize(20);
        esr.setPageNumber(1);
        // These 2 lines will set the timeout to 60 seconds.Normally not needed
        // Unless you are using Google App Engine
        esr.setConnectionTimeout(60000);  // Used with Google App Engine only
        esr.setReadTimeout(60000);        // Used with Google App Engine only
        SearchResult sr = null;
        try {
            sr = eo.search(esr);
            System.out.println(sr.getEvents().get(1).getTitle());
            if (sr.getTotalItems() > 1) {

                System.out.println("Total items: " + sr.getTotalItems());
            }
        }catch(EVDBRuntimeException var){
            System.out.println("1Opps got runtime an error...");

        } catch( EVDBAPIException var){
            System.out.println("2Opps got runtime an error...");
        }

            ArrayList<String> test = new ArrayList<String>();
        try {
            for (Event e : sr.getEvents()) {
                test.add(e.getTitle());
                System.out.println("HALLO");
            }

            return test;
        }catch(Exception e){
            test.add("Fehler");
            return test;
        }
    }

}
