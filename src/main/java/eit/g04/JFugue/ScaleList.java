package eit.g04.JFugue;

import java.util.HashMap;

/**
 * Created by Thomas on 02.03.2016.
 */
public class ScaleList extends HashMap<Double, String>{
    //private String[] NumberNote = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};
    public ScaleList() {
        this.put((double)1, "C");
        this.put(1.5, "Db");
        this.put((double)2, "D");
        this.put(2.5, "Eb");
        this.put((double)3, "E");
        this.put(3.5, "NONE");
        this.put((double)4, "F");
        this.put(4.5, "Gb");
        this.put((double)5, "G");
        this.put(5.5, "Ab");
        this.put((double)6, "A");
        this.put(6.5, "Bb");
        this.put((double)7, "A");
        this.put(7.5, "NONE");
        
    }

}
