package de.rasi.org.berlinvent;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Justin on 20.03.2017.
 * Mapping der Infos aus der Antwort XML
 * Der Aufbau ist /search/...
 * Die Elemente sind eindeutige Attribute
 * Die Elementliste besteht aus weiteren Unterelementen
 * siehe auch Event.java
 */
@Root(name="search",strict=false)
public class Eventlist implements Serializable{
//implements Parcelable{
        @Element(name="total_items",required = false)
        private int totalitems;

        @Element(name="page_size", required = false)
        private int pagesize;

        @Element(name="page_count", required = false)
        private int pagecount;

        @Element(name = "page_number",required = false)
        private int pagenumber;

        @ElementList(name="events", required = false) //inline=true,
        //@Path("search/events")
        private List<Event> events;

        public Eventlist(){};

        public List<Event>  getMatches() {
            return events;
        }

        public void addEvents(Eventlist evt){
                try {
                        List<Event> add = this.getMatches();
                        add.addAll(evt.getMatches());
                        events=add;
                }catch (NullPointerException np){
                        events=evt.getMatches();
                }

        }

        public int getPagecount() {
                return pagecount;
        }

        public void setPagecount(int pagecount) {
                this.pagecount = pagecount;
        }

        public int getPagenumber() {
                return pagenumber;
        }

        public void setPagenumber(int pagenumber) {
                this.pagenumber = pagenumber;
        }

        public int getTotalitems() {
                return totalitems;
        }

        public void setTotalitems(int totalitems) {
                this.totalitems = totalitems;
        }

        public int getPagesize() {
                return pagesize;
        }

        public void setPagesize(int pagesize) {
                this.pagesize = pagesize;
        }

        /*
        public List<Event> parcelList;

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeList(this.getMatches());
        }
        public static final Parcelable.Creator<Eventlist> CREATOR = new Parcelable.Creator<Eventlist>(){

                @Override
                public Eventlist createFromParcel(Parcel in) {
                        return new Eventlist(in);
                }

                @Override
                public Eventlist[] newArray(int size) {
                        return new Eventlist[size];
                }
        };
        private Eventlist(Parcel in){
                parcelList=in.readTypedList(this.getMatches(),Event.CREATOR);
        }*/
}
