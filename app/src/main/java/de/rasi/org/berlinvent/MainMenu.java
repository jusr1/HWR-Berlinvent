package de.rasi.org.berlinvent;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
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

    public String apikey = "QNgn6PCmNLN9HF9J";
    public String base_url = APIConfiguration.getBaseURL();
    public TextView t1;
    Button btn1, btn2, btn3;


    public HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    public OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build();
    public  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.eventful.com/rest/")
            //.addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
    public EvtFulService service = retrofit.create(EvtFulService.class);
    public Call<de.rasi.org.berlinvent.Eventlist> data =service.listLocation("Berlin","QNgn6PCmNLN9HF9J");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


       // System.out.println(service.listLocation("QNgn6PCmNLN9HF9J","Berlin"));



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
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            System.out.println("HALLO2");
            new getListLocation().execute();

            //new GetUrlContentTask().execute("http://api.eventful.com/rest/events/search?app_key=QNgn6PCmNLN9HF9J&location=Berlin&callback");
        }
        if (v.getId() == R.id.button2)
            t1.setText("Button 2 gedrückt");
        if (v.getId() == R.id.button3)
            t1.setText("Button 3 gedrückt");
    }

    public void updateTextViewMain(String toThis) {
        TextView textView = (TextView) findViewById(R.id.textmain);
        textView.setText(toThis);
    }

    private class getListLocation extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            String content = "";
            try {
                System.out.println("TEST100");
                Response<de.rasi.org.berlinvent.Eventlist> test = data.execute();
/*
                if(data.isExecuted()==false) {
                    test = data.execute();
                }else{
                    test = data.clone().execute();
                }
                */
                Eventlist all = test.body();


                for(de.rasi.org.berlinvent.Event evt : all.getMatches()) {
                    System.out.println("Titel: " + evt.getTitle());
                    System.out.println("Beschreibung: " + evt.getDescription());
                    System.out.println("Event-ID: " + evt.getId());
                    System.out.println("Startzeit: " + evt.getStart_time());
                    System.out.println("Ende: " + evt.getStop_time());
                    System.out.println("URL: " + evt.getUrl());
                    System.out.println("----------");
                    content=content+"\n"+evt.getTitle()+evt.getDescription()+evt.getId()+evt.getStart_time()+evt.getStop_time()+evt.getUrl();
                }




                System.out.println("TEST101");
            } catch (IOException e) {
                System.out.println("REsponseFehler");
                e.printStackTrace();
                return null;
            }
            return content;

        }
        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            t1.setText("Result:"+result);
            //data.cancel();

        }
    }



}
