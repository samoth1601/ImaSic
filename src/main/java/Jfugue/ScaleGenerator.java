package Jfugue;

import java.util.ArrayList;

/**
 * Created by Thomas on 02.03.2016.
 */
public class ScaleGenerator {

    String[] allNotes={"C","Db","D","Eb","E","F","Gb","G","Ab","A","Bb","B"};

    public String getScale(String key, ScaleType scaleType){
        int[] scaleProgression = ScaleIntervals.getIntervals(scaleType);

        String scaleToReturn ="";
        int currentKey = findIndexOfKey(key);
        for (int delta: scaleProgression) {
            System.out.println("CurrentKey "+ (currentKey));
            scaleToReturn += allNotes[currentKey%12]+"s ";
            currentKey=currentKey+delta;
        }
        return scaleToReturn;
    }

    public ArrayList<String> getScaleAsArray(String key, ScaleType scaleType){
        System.out.println("getScaleArraykey : " + key);
        int[] scaleProgression = ScaleIntervals.getIntervals(scaleType);

        ArrayList<String> scaleArray = new ArrayList<String>();
        int currentKey = findIndexOfKey(key);
        for (int delta: scaleProgression) {
            //System.out.println("CurrentKey "+ (currentKey));
            scaleArray.add(allNotes[currentKey%12]);
            currentKey=currentKey+delta;
        }
        return scaleArray;
    }


    private int findIndexOfKey(String key){

        for (int i = 0; i < allNotes.length; i++) {
            if (allNotes[i]==key){

                return i;

            }
        }
        return 0;
    }



}
