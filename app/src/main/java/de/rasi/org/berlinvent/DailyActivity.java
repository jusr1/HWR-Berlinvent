package de.rasi.org.berlinvent;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Justin on 21.03.2017.
 */

public class DailyActivity extends AppCompatActivity implements View.OnClickListener{

    /** globale Objekte zur Nutzung in dieser Aktivität*/
    public String apikey = "QNgn6PCmNLN9HF9J";
    public TextView t1;
    Button btn1, btn2, btn3;
    public ProgressBar pb;
    public int MAXPROGRESS;
    public Eventlist eventcontent=null;

    /** der HttpLoggingInterceptor diente uns zum Auslesen der http-Abfragen und der Antworten
     * Dieser Schritt war notwendig, um unser Problem mit dem Erhalt der korrekten XML-Datei zu lösen
     * Auskommentiert, da es ein Wartungstool ist*/
    //public HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    /** der OkHTTPClient dient als Tool zur Nutzung von Retrofit, on-board HttpRequests haben nicht die
     * korrekten Antworten geliefert*/
    public OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            //.addInterceptor(logging)
            .build();
    /**Retrofit dient uns als Tool zum individuellen Mappen von Http-Responses, XMLConverter auf Java-Objekt (Event)*/
    public  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.eventful.com/rest/")
            //.addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
    /**Dazugehöriger service von retrofit, selbsterstellt um die Query zu bauen und ein Httprequest zu senden*/
    public EvtFulService service = retrofit.create(EvtFulService.class);

    /**"Magic Numbers", da wir nur die Abfrage auf Berlin haben wollen und genau 50 Objekte erhalten möchten*/
    public Call<de.rasi.org.berlinvent.Eventlist> data=service.listLocation("Berlin",apikey,"50");

    /**OnCreate Methode gleich dem Mainmenu, abgesehen vom Ausführen der Background-Methode getListLocation*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.daily_menu);

        pb = (ProgressBar) findViewById(R.id.mainProgressBar);

        t1 = (TextView) findViewById(R.id.textmain);
        t1.setOnClickListener(this);

        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(DailyActivity.this);

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(DailyActivity.this);

        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(DailyActivity.this);

        pb.setIndeterminate(true);
        new getListLocation().execute();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    /**Buttons aus dem Hauptmenü bleiben in der Funktion erhalten*/
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            //t1.setText(R.string.loading);
            Intent intent = new Intent(this, DailyActivity.class);
            startActivity(intent);
            finish();
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


    /**Backgroundmethode zum Erhalten von den oben definierten 50 Events aus Berlin
     * Informationen werden in einem String gesichert, sehr simpler Ansatz*/
    private class getListLocation extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            String content = "";
            try {
                Response<de.rasi.org.berlinvent.Eventlist> test = null;
                /**Interessante Gegebenheit: es darf kein http-request zwei oder mehr mal geöffnet werden,
                 * deshalb muss man die selbe Abfrage kopieren, um diesselbe  Antwort zu erhalten
                 * durch einen "cancel" soll gewährleistet werden, dass es nicht geklont werden muss*/
                if(data.isExecuted()==false) {
                    test = data.execute();
                }else{
                    test = data.clone().execute();
                }
                Eventlist all = test.body();
                try {
                    eventcontent = new Eventlist();
                    eventcontent = all;
                }catch(Exception e){
                    System.out.println("Contentfehler");
                }

                for(de.rasi.org.berlinvent.Event evt : all.getMatches()) {
                    content=content+evt.toString();

                }

                return content;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }
        protected void onProgressUpdate(Integer... progress) {

        }
        /** Nach Abschluss der Backgroundmethode werden die Resultate verarbeitet und angezeigt*/
        protected void onPostExecute(String result) {
            pb.setIndeterminate(false);
            t1.setText(result);
            t1.setGravity(Gravity.TOP);
            data.cancel();
        }
    }
}
