package de.rasi.org.berlinvent;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import retrofit2.http.Path;
import org.simpleframework.xml.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Justin on 20.03.2017.
 * Mappen der Infos aus der XML-Antwort
 * Der Path lautet /search/events/event (Mit ID)
 * Attribut ist hier ein eindeutiger Beschreiber, sodass mehrere "Event"-Objekte aus der
 * XML erhalten werden können, mit x Elementen
 * Eventimage wurde nochmal aufgegliedert, siehe auch EventImage
 */

    @Root(name = "search/events/event",strict=false)
    public class Event implements Serializable{
        @Attribute(name = "id", required = false)
        //@org.simpleframework.xml.Path("events")
        private String id;

        @Element(name = "title", required = false)
        //@org.simpleframework.xml.Path("events")
        private String title;

        @Element(name = "description", required = false)
        //@org.simpleframework.xml.Path("event")
        private String description;

        @Element(name = "url", required = false)
        //@org.simpleframework.xml.Path("event")
        private String url;

        @Element(name = "start_time", required = false)
        //@org.simpleframework.xml.Path("event")
        private String start_time;

        @Element(name = "stop_time", required = false)
        //@org.simpleframework.xml.Path("event")
        private String stop_time;

        @Element(name = "latitude",required = false)
        private float lat;

        @Element(name = "longitude", required = false)
        private float lon;

        @Element(name = "venue_name", required = false)
        private String venuename;

        @Element(name="image", required = false) //inline=true,
        private EventImage img;



    public Event() {
    }

    @Override
    public String toString() {
        return "Event:"+this.getTitle()
                +"\n\nBeschreibung:"+this.getDescription()
                +"\nID:"+this.getId()
                +"\nAnfang:"+this.getStart_time()
                +"\nEnde:"+this.getStop_time()
                +"\nOrt:"+this.getVenuename()
                +"\nLatitude:"+this.getLat()
                +"\nLongitue:"+this.getLon()
                +"\n-----------------\n";
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getVenuename() {
        return venuename;
    }

    public void setVenuename(String venuename) {
        this.venuename = venuename;
    }

    public EventImage getImg() {
        return img;
    }

    public void setImg(EventImage img) {
        this.img = img;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getTitle() {

        return title;
    }

    public String getUrl() {
        return url;
    }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {

            return description;
        }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getId() {

        return id;
    }
}
