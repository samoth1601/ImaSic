package eit.g04.JFugue;

/**
 * Created by Thomas on 02.03.2016.
 */
public class PossibleIntervalScales {

    String[] numberNote={"C","Db","D","Eb","E","F","Gb","G","Ab","A","Bb","B"};
    String[] wholeNotes={"C","D","E","F","G","A","B"};

    //The natural major Scale (Ionian)
    //W,W,H,W,W,W,H
    //natural major scale in the key of C the fifth interval(note) is G. C,D,E,F,G,A,B,C
    public Interval getMajorScale(String key){
        String[] scaleProgression = {"W","W","H","W","W","W","H"};
        double[] scaleProgressionDouble = {1,1,0.5,1,1,1,0.5};
        Interval majorScale = new Interval();
        //int[] progression = {}
        ScaleList scaleList = new ScaleList();


        double currentKey = findIndexOfKey(key);
        for (Double delta: scaleProgressionDouble) {
            //majorScale.addToInterval();

        }


        return majorScale;
    }

    private int findIndexOfKey(String key){
        for (int i = 0; i < wholeNotes.length; i++) {
            if (wholeNotes[i]==key){
                return i;
            }
        }
        return 0;
    }



}
