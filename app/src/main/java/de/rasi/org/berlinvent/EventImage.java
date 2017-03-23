package de.rasi.org.berlinvent;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Justin on 21.03.2017.
 */
@Root(name = "image",strict=false)
public class EventImage {
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
