package de.rasi.org.berlinvent;

import android.util.Xml;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.*;

/**
 * Created by Justin on 20.03.2017.
 */

public interface EvtFulService {
    @GET("events/search")
    Call<Eventlist> listLocation(@Query("location") String loc, @Query("app_key") String appkey);
}

