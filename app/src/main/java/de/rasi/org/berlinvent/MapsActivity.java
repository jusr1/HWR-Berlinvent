package de.rasi.org.berlinvent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public String apikey = "QNgn6PCmNLN9HF9J";
    public TextView t1;
    Button btn1, btn2, btn3;
    public ProgressBar pb;
    public int MAXPROGRESS;
    public Eventlist eventcontent=null;

    //public HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    public OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            //.addInterceptor(logging)
            .build();
    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.eventful.com/rest/")
            //.addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
    public EvtFulService service = retrofit.create(EvtFulService.class);

    public Call<Eventlist> data=service.listLocation("Berlin",apikey,"25");
    public int PAGENUM=0;
    public int MAXPAGES=0;
    public Call<Eventlist> xmldata=service.listLocationMap("Berlin",apikey,"25",PAGENUM);

    private Eventlist elist;
    private GoogleMap mMap;
    private int i;
    private SupportMapFragment mapFragment;

/** Beim Erstellen der Aktivität werden Grundmethoden ausgeführt, um alle notwendigen Informationen zur
 * Darstellung der Karte zu gewährleisten*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new getListLocation().execute();
        setContentView(R.layout.activity_maps);
        //Intent intent = getIntent();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        pb = (ProgressBar) findViewById(R.id.mainProgressBar);
        pb.setIndeterminate(true);
    }

    /** alle Methoden zum Erstellen der Karte, Methoden wie die Marker auf der Map aussehen
     * Kamerabewegung auf Berlin, Informationen der Events werden empfangen und im Design verarbeitet
     * */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng berlin = new LatLng(52.520645, 13.409779);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(berlin,12));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }
            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {
                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.windowlayout, null);
                // textviews
                //ImageView imgThumb = (ImageView) v.findViewById(R.id.img_thumb);
                TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
                TextView tvDesc = (TextView) v.findViewById(R.id.tv_desc);
                //set texts
                tvDesc.setText("Beschreibung: "+arg0.getSnippet());
                tvTitle.setText("Event: "+arg0.getTitle());
                // Returning the view containing InfoWindow contents
                return v;
            }
        });
        try{
        for(Event evt : elist.getMatches()){
            try{
            if(evt.getDescription().length()>=100){
                evt.setDescription(evt.getDescription().substring(0,100)+"...");
            }else if (evt.getDescription().length()==0){
                evt.setDescription(" keine Beschreibung vorhanden");}
            }catch(NullPointerException np){
                evt.setDescription(" keine Beschreibung vorhanden");
            }
            LatLng event = new LatLng(evt.getLat(), evt.getLon());

            mMap.addMarker(new MarkerOptions()
                    .position(event)
                    .title(evt.getTitle())
                    .snippet(evt.getDescription()+"\n\nOrt: "+evt.getVenuename())
                    //.icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromURL(evt.getImg().getUrl())))
                    .draggable(false));
        }}catch(NullPointerException np){
            System.out.println("Keine Daten vorhanden!");
        }
    }
    /** Backgroundprozess um Informationen zu Events zu erhalten*/
    private class getListLocation extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String content="";
            try {
                Response<Eventlist> test = null;
                if(data.isExecuted()==false) {
                    test = data.execute();
                }else{
                    test = data.clone().execute();
                }
                Eventlist all = test.body();
                elist=all;
                return content;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onProgressUpdate(Integer... progress) {}

        protected void onPostExecute(String result) {
            data.cancel();
            mapFragment.getMapAsync(MapsActivity.this);
            pb.setIndeterminate(false);

            /**siehe darunter**/
            //try {
            //    MAXPAGES = Integer.parseInt(result);
            //}catch(Exception e){
            //    System.out.println("INTEGERTOSTRINGFEHLER");
            //}
            //elist= new Eventlist();
            //new getResponseInformation().execute();

            //t1.setText(result);
            //t1.setGravity(Gravity.TOP);

        }
    }

    /** Teil des Versuches ALLE Events in Berlin am heutigen Tag anzuzeigen; Performanceeinbruch**/
    /*
    private class getResponseInformation extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String content = "";
            try {
                PAGENUM++;
                Response<Eventlist> test = null;
                if(xmldata.isExecuted()==false) {
                    test = xmldata.execute();
                }else{
                    test = xmldata.clone().execute();
                }
                Eventlist all = test.body();
                elist.addEvents(all);
                return content;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }
        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(String result) {

            xmldata.cancel();
            //mapFragment.getMapAsync(MapsActivity.this);
            if(PAGENUM!=MAXPAGES){
                new getResponseInformation().execute();
            }else{
                mapFragment.getMapAsync(MapsActivity.this);
                pb.setIndeterminate(false);
            }
            //pb.setIndeterminate(false);
            //t1.setText(result);
            //t1.setGravity(Gravity.TOP);

        }
    }*/
}
