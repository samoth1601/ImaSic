package Jfugue;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import org.jfugue.rhythm.Rhythm;

import java.util.*;

/**
 * Created by tordolsen on 25/04/16.
 */
public class RhythmGenerator {

    //private String[] rhythmLayers = new String[]();
    private List<StringArray> rhythmLayers = new ArrayList<StringArray>();
    private Rhythm generatedRhythm = new Rhythm();
    private EuclideanRhythm euclidanRhyhm = new EuclideanRhythm();

    //private List<Map<Integer, Character>> rhythmMap = new ArrayList<Map<Integer, Character>>();






    public RhythmGenerator(Mood mood/*, int numberOfLayers, int divisors, int bars*/){
        Random rnd = new Random();
        double rndNr;
        int rndInt;
        setRhythmMap();

        int hits;       //NUMBER OF HITS TO A LAYER
        int pulses;     //NUMBER OF PULSES IN PART




        if(mood == Mood.HAPPY){
            /*StringArray bassDrums = new StringArray();
            bassDrums.add("O..oO...O..oO...");
            bassDrums.add("O...OO..O..oOO..");
            bassDrums.add("O..oOO..O..oOO..");

            StringArray snareDrums = new StringArray();
            snareDrums.add("..S...S...S...S.");
            snareDrums.add("..S_s_s.S...S_s_s.S.");

            StringArray hihats = new StringArray();
            hihats.add("+```````+```````");
            hihats.add("```````````````*");
*/
            StringArray bassDrums = new StringArray();
            bassDrums.add("O..oO...");
            bassDrums.add("O...OO..");
            bassDrums.add("O..oOO..");

            StringArray snareDrums = new StringArray();
            snareDrums.add("..S...S.");
            snareDrums.add("..S_s_s.S.");

            StringArray hihats = new StringArray();
            hihats.add("+```````");
            hihats.add("*```````");

            this.rhythmLayers.add(bassDrums);
            this.rhythmLayers.add(snareDrums);
            this.rhythmLayers.add(hihats);


            rndInt = rnd.nextInt(3);
            this.generatedRhythm.addLayer(rhythmLayers.get(0).get(rndInt));
            this.generatedRhythm.addLayer(rhythmLayers.get(1).get(rnd.nextInt(2)));
            this.generatedRhythm.addLayer(rhythmLayers.get(2).get(rnd.nextInt(2)));


            /*
            for(int i = 0; i < numberOfLayers; i++) {
                rndNr = rnd.nextDouble();


                if(i == 0 && rndNr < 0.5){
                    rndInt = rnd.nextInt(5) + 2;
                    euclidanRhyhm.bjorklund(8,rndInt,'O','.');


                    //rhythmLayers[0] = euclidanRhyhm.getStringPattern();
                    rhythmLayers.add(euclidanRhyhm.getStringPattern());
                    for(int j = 0; j < rhythmLayers.get(0).length();j++){
                        ///if(rhythmLayers[0].charAt(j) == 'O'){
                        if (rhythmLayers.get(0).charAt(j) == 'O'){

                        }
                    }

                }
                //rhythmLayers[i] = euclidanRhyhm.getStringPattern();

            }*/
        }
        else if(mood == Mood.SAD){
            /*StringArray bassDrums = new StringArray();
            bassDrums.add("O...O...O...O...");
            bassDrums.add("O...OO..O..oOO..");

            StringArray snareDrums = new StringArray();
            snareDrums.add("..S...S...S...S.");
            snareDrums.add("..Sss..S...Sss..S.");

            StringArray hihats = new StringArray();
            hihats.add("+.`.`.`.+.`.`.`.");
            hihats.add("```````````````*");
*/
            StringArray bassDrums = new StringArray();
            bassDrums.add("O...O...");
            bassDrums.add("O...OO..");

            StringArray snareDrums = new StringArray();
            snareDrums.add("..S...S.");
            snareDrums.add("..Sss..S.");

            StringArray hihats = new StringArray();
            hihats.add("+.`.`.`.");
            hihats.add("*```````");


            this.rhythmLayers.add(bassDrums);
            this.rhythmLayers.add(snareDrums);
            this.rhythmLayers.add(hihats);


            rndInt = rnd.nextInt(2);
            this.generatedRhythm.addLayer(rhythmLayers.get(0).get(rndInt));
            this.generatedRhythm.addLayer(rhythmLayers.get(1).get(rnd.nextInt(2)));
            this.generatedRhythm.addLayer(rhythmLayers.get(2).get(rnd.nextInt(2)));
        }
        else {

        }



        setKit(mood);



    }

    private void setKit(Mood mood){
        Map<Character, String> rhythmKit = new HashMap<Character, String>();
        rhythmKit.put('.', "Ri");
        rhythmKit.put('_', "Rs");
        rhythmKit.put('O', "[BASS_DRUM]i");
        rhythmKit.put('o', "Rs [BASS_DRUM]s");
        rhythmKit.put('S', "[ACOUSTIC_SNARE]i");
        rhythmKit.put('s', "[ACOUSTIC_SNARE]s");
        rhythmKit.put('`', "[CLOSED_HI_HAT]i");
        rhythmKit.put('^', "[PEDAL_HI_HAT]s Rs");
        rhythmKit.put('*', "[CRASH_CYMBAL_1]i");
        rhythmKit.put('+', "[OPEN_HI_HAT]i");
        //rhythmKit.put(Character.valueOf('+'), "[CRASH_CYMBAL_1]s Rs");
        rhythmKit.put('X', "[HAND_CLAP]i");
        rhythmKit.put('x', "[CHINESE_CYMBAL]s");
        rhythmKit.put('t', "[LOW_TOM]i");
        rhythmKit.put('y',"Rs");


        generatedRhythm.setRhythmKit(rhythmKit);
    }

    public Rhythm getRhythm (){

        return this.generatedRhythm;
    }


    private void setRhythmMap() {
        /*rhythmMap.add()
        rhythmMap.add(put(0,'x'));
        rhythmMap[0].put(1,'X');
*/
    }

}



