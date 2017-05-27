package de.rasi.org.berlinvent;

import android.util.Xml;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.*;

/**
 * Created by Justin on 20.03.2017.
 * Hier werden Query-Objekte definiert, diese können in den Funktionen benutzt werden und mit Objekt-Bausteinen versehen werden
 * Teil von "Retrofit"
 *
 * Die verschiedenen Querybegriffe werden auf die hier beschriebenen XML-Elemente der "Eventful"-XML-Datei gemappt
 */

public interface EvtFulService {
    @GET("events/search")
    Call<Eventlist> listLocation(@Query("location") String loc, @Query("app_key") String appkey,@Query("pagesize") String ps);
    @GET("events/search")
    Call<List<Event>> listLocation2(@Query("location") String loc, @Query("app_key") String appkey);
    @GET("events/search")
    Call<Eventlist> listLocationMap(@Query("location") String loc, @Query("app_key") String appkey,@Query("pagesize") String ps,@Query("pagenumber") int pn);
}

