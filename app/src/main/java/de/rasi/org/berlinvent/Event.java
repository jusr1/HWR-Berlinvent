package de.rasi.org.berlinvent;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import retrofit2.http.Path;
import org.simpleframework.xml.*;

/**
 * Created by Justin on 20.03.2017.
 */


    /*private String id;
    private String title;
    private String url;
    private String descr;
    private String starttime;
    private String endtime;
    private String imgurl;*/

    @Root(name = "search",strict=false)
    public class Event {
        @Attribute(name = "id", required = false)
        @org.simpleframework.xml.Path("events/event")
        private String id;

        @Element(name = "title", required = false)
        @org.simpleframework.xml.Path("events/event")
        private String title;

        @Element(name = "description", required = false)
        @org.simpleframework.xml.Path("events/event")
        private String description;

        @Element(name = "url", required = false)
        @org.simpleframework.xml.Path("events/event")
        private String url;

        @Element(name = "start_time", required = false)
        @org.simpleframework.xml.Path("events/event")
        private String start_time;

        @Element(name = "stop_time", required = false)
        @org.simpleframework.xml.Path("events/event")
        private String stop_time;




        /*@Attribute(required = false)
        private String link;*/

        public Event() {
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
