package de.rasi.org.berlinvent;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Justin on 21.03.2017.
 * Mappen von Infos aus der XML-Antwort
 * Der Path ist /search/events/event/image/...
 * Der Block Image hat verschiedene Image-URLs
 * Wir greifen nur auf die Basis-URL zu
 */
@Root(name = "image",strict=false)
public class EventImage implements Serializable{
    @Element(name = "url", required = false)
    //@org.simpleframework.xml.Path("events")
    private String url;

    public EventImage(){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
