package de.rasi.org.berlinvent;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

/**
 * Created by Justin on 20.03.2017.
 */
@Root(name="search",strict=false)
public class Eventlist {


        @ElementList(name="events", required = false) //inline=true,
        //@Path("search/events")
        private List<Event> events;

        public Eventlist(){};

        public List<Event>  getMatches() {
            return events;
        }

}
