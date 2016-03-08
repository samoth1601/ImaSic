package eit.g04.JFugue;

import java.util.ArrayList;


/**
 * Created by Thomas on 02.03.2016.
 */
public class Interval{
    private ArrayList<String> interval;
    public Interval(){
        interval = new ArrayList<String>();
    }

    public ArrayList<String> getInterval() {
        return interval;
    }

    public void setInterval(ArrayList<String> interval) {
        this.interval = interval;
    }

    public void addToInterval(String toAdd) {
        interval.add(toAdd);
    }

    @Override
    public String toString() {
        return "Interval{" +
                "interval=" + interval +
                '}';
    }
}
