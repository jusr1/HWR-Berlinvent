package de.rasi.org.berlinvent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.evdb.javaapi.APIConfiguration;
//import com.evdb.javaapi.EVDBAPIException;
//import com.evdb.javaapi.EVDBRuntimeException;
//import com.evdb.javaapi.data.Event;
//import com.evdb.javaapi.data.SearchResult;
//import com.evdb.javaapi.data.request.EventSearchRequest;
//import com.evdb.javaapi.operations.EventOperations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.Response;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
/** globale Objekte zur Nutzung in dieser Aktivität*/
    public String apikey = "QNgn6PCmNLN9HF9J";
    public TextView t1;
    Button btn1, btn2, btn3;
    public ProgressBar pb;
    public Eventlist eventcontent=null;


    /**Methode bei Aufruf dieser Aktivität, Initialisierung der anzusprechenden Layout-Objekte*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        pb = (ProgressBar) findViewById(R.id.mainProgressBar);

        t1 = (TextView) findViewById(R.id.textmain);
        t1.setOnClickListener(this);

        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(MainMenu.this);

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(MainMenu.this);

        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(MainMenu.this);
    }
/** Bei Klicken der "Return-Taste" eines Mobiltelefons wird die Aktivität geschlossen*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
/** Buttons werden mit Listenern versehen, neue Intents (Also neue Aktivitätsaufrufe) werden gestartet
 * putExtra ist essentiell für die Übergabe von Informationen zwischen zwei Aktivitäten
 * button3 ist ohne Funktion*/
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            //t1.setText(R.string.loading);
            Intent intent = new Intent(this, DailyActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.button2) {
            Intent intent = new Intent(this,MapsActivity.class);
            intent.putExtra("events",eventcontent);
            startActivity(intent);
        }
        if (v.getId() == R.id.button3) {
            t1.setText("Button 3 gedrückt");
            t1.setGravity(Gravity.CENTER);
        }
        if(v.getId() == R.id.textmain){
            System.out.println(t1.getText());
        }
    }

    public void updateTextViewMain(String toThis) {
        TextView textView = (TextView) findViewById(R.id.textmain);
        textView.setText(toThis);
    }





}
