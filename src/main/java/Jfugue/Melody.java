package Jfugue;

import org.jfugue.theory.ChordProgression;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Thomas on 16.03.2016.
 */
public class Melody {
    ScaleType scaleType;
    ScaleGenerator possibleIntervalScales = new ScaleGenerator();
    ArrayList<String> scale;
    String patternString;

    public Melody (Mood mood, String key, ChordProgression blockProgression,String progressionRoman,String section){
        patternString = "";
        ArrayList<String> possibleLandingNotes = new ArrayList<String>();
        //velger skala ut fra mood
        if (mood == Mood.HAPPY){
            scaleType = ScaleType.MajorPentatonic;
            possibleLandingNotes = possibleIntervalScales.getScaleAsArray(key,ScaleType.Major);
        }else if (mood == Mood.SAD){
            scaleType = ScaleType.MinorPentatonic;
            possibleLandingNotes = possibleIntervalScales.getScaleAsArray(key,ScaleType.Minor);
        }
        scale = possibleIntervalScales.getScaleAsArray(key,scaleType);



        Random rnd = new Random();

        //henter chordprogression for blokken og konverterer denne fra romerske tall til integers.
        int[] progressionInts = getNumbersFromRoman(progressionRoman);

        //lager en sekvens av 16 noter, fordelt på 4 bolker.

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                //*********** RULE # 1 ***************
                //første note av 16 skal være lik grunnote i første chord.
                //Prosent sjanse for at denne regelen intreffer ved dette punktet i koden:
                int rule1chance = 95;
                if (i==0 && j==0 && chancePercent(rule1chance)) {
                    System.out.println("rule1 triggered");
                    patternString += possibleLandingNotes.get(0)+"s ";
                    continue;
                }

                //*********** RULE # 3 ***************
                //legg til én notes pause
                //Prosent sjanse for at denne regelen intreffer ved dette punktet i koden:
                int rule3chance = 10;
                if (section.equals("basis")){
                    rule3chance=10;
                }else if (section.equals("refrain")){
                    rule3chance=10;
                }if (chancePercent(rule3chance)) {
                    System.out.println("rule3 triggered");
                    patternString+= "rs ";
                    continue;
                }

                //*********** RULE # 33 ***************
                //legg til én 4/4 note
                //Prosent sjanse for at denne regelen intreffer ved dette punktet i koden:

                int rule33chance = 10;
                if (section.equals("basis")){
                    rule33chance=5;
                }else if (section.equals("refrain")){
                    rule33chance=30;
                }if (chancePercent(rule33chance)) {
                    System.out.println("rule33 triggered");
                    patternString += scale.get(rnd.nextInt(scale.size()))+"q ";
                    i++;
                    j-=1;
                    break;
                }



                //*********** RULE # 4 ***************
                //(Etter første 4-er blokk) -Første note av 4er-blokken skal være lik en av tonene som er i akkorden som spilles.
                //Prosent sjanse for at denne regelen intreffer ved dette punktet i koden:
                int rule4chance = 80;
                if (i>0 && j == 0 && chancePercent(rule4chance)) {

                    //finne hvilken akkord som spilles.
                    int activeChordInt = progressionInts[i];

                    //henter ut mulige noter som skal spilles basert på hvilken chord som spilles og i keyen.
                    ArrayList<String> possibleFirstNotesFromChord = new ArrayList<String>();
                    int progressionIntInt = progressionInts[i];

                    //Legger til en note som passer men skal unngå 4. og 7. note i skalaen.
                    int k = 0;
                    if (progressionIntInt != 3 || progressionIntInt != 6){
                        possibleFirstNotesFromChord.add(possibleLandingNotes.get(progressionIntInt));
                        k++;
                    }
                    if (progressionIntInt+2 != 3 || progressionIntInt+2 != 6){
                        possibleFirstNotesFromChord.add(possibleLandingNotes.get((progressionIntInt+2)%7));
                        k++;
                    }
                    if (progressionIntInt+4 != 3 || progressionIntInt+4 != 6){
                        possibleFirstNotesFromChord.add(possibleLandingNotes.get((progressionIntInt+4)%7));
                    }
                    patternString+=possibleFirstNotesFromChord.get(rnd.nextInt(possibleFirstNotesFromChord.size()))+"s ";
                    System.out.println("rule4 triggered");
                    continue;
                }

                //*********** RULE # 5 ***************
                //Hvis på 2. note i en bolk, mulighet for at 3 neste noter er pauser,
                //Prosent sjanse for at denne regelen intreffer ved dette punktet i koden:
                int rule5chance = 50;
                if (section.equals("basis")){
                    rule5chance=25;
                }else if (section.equals("refrain")){
                    rule5chance=60;
                }if (i>0 && j==1 && chancePercent(rule5chance)) {
                    patternString+= "rs rs rs ";
                    //ikke plass til flere noter i denne bolken, så øk 'j' for å gå til neste bolk.
                    j=9;
                    System.out.println("rule5 triggered");
                    continue;
                }

                //*********** RULE # 2 ***************
                //legg til en random note.
                //Prosent sjanse for at denne regelen intreffer ved dette punktet i koden:
                int rule2chance = 100;
                if (chancePercent(rule2chance)) {
                    patternString += scale.get(rnd.nextInt(scale.size()))+"s ";
                    System.out.println("rule2 triggered");
                    continue;
                }
                //System.out.println("j=" +j + ": " +  patternString);
            }
            //System.out.println("i=" + i + ": " +  patternString);
        }
        System.out.println("Det som spilles for "+section + ": " + patternString);

    }



    public String getMelodyString(){
        return patternString;
    }

    public int[] getNumbersFromRoman(String romanString){
        String[] romanArray = romanString.split(" ");
        int[] romanInts = new int[romanArray.length];
        for (int i = 0; i < romanArray.length; i++) {
            String R = romanArray[i];
            System.out.print("R: " + R);
            int factor = 1;
            if (R.equals("I") || R.equals("i")) {
                romanInts[i] = 1-factor;
            } else if (R.equals("ii") || R.equals("II") || R.equals("iio")) {       // lagt til for iio i minor-proression
                romanInts[i] = 2-factor;
            } else if (R.equals("iii") || R.equals("III")) {
                romanInts[i] = 3-factor;
            } else if (R.equals("IV") || R.equals("iv")) {
                romanInts[i] = 4-factor;
            } else if (R.equals("V") || R.equals("v")) {
                romanInts[i] = 5-factor;
            } else if (R.equals("vi") || R.equals("VI")) {
                romanInts[i] = 6-factor;
            } else if (R.equals("VII") || R.equals("vii")) {
                romanInts[i] = 7-factor;
            }
            // LEGGER TIL FOR DIM7-akkord
            else if (R.equals("viio")){
                romanInts[i] = 7-factor;
            }
            else{
                romanInts[i] = 1999;
            }
        }
        System.out.println();
        for (int testyolo: romanInts
                ) {
            System.out.print("$: " + testyolo);
        }
        System.out.println();
        return romanInts;
    }


    public boolean chancePercent(int percentChance){
        Random rnd = new Random();
        if (rnd.nextInt(100)<percentChance){
            return true;
        }else {
            return false;
        }

    }








}






