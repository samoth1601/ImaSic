package Jfugue;

import java.util.Random;

/**
 * Created by Thomas on 16.03.2016.
 */
public class PossibleChordProgressions {
    static String[] possibleProgressionsMAJOR = {
            "I V vi IV", "I V vi iii", "vi V IV V", "I vi IV V", "I IV vi V", "I V IV V"
    };
    static String[] possibleProgressionsMINOR = {
            "i iv i i", "i v i v", "i iv v i ", "VI VII i i ", "I VII VI v", "i iv i iv", "v iv i i ", "ii v i i ", "i VII VI VII"
    };          //SKAL NUMMER 5 VÆRE SLIK TOLLEF?

    private static String[][] majorClasses = new String[][]{{"I","iii","vi"},{"IV","ii"}, {"V", "viio"}};
    private static String[][] minorClasses = new String[][]{{"i","III"},{"iv", "iio","VI"},{"v","VII"}};

    
    public static String getChordProgression(Mood mood){
        Random rnd = new Random();
        String toReturn= "";
        //velger en progression basert på mood
        if (mood == Mood.HAPPY){
            toReturn =  possibleProgressionsMAJOR[rnd.nextInt(possibleProgressionsMAJOR.length-1)];
            System.out.println("PossibleChordProgression returnerer major" + toReturn);
        }else if (mood == Mood.SAD){
            toReturn =  possibleProgressionsMINOR[rnd.nextInt(possibleProgressionsMINOR.length-1)];
            System.out.println("PossibleChordProgression returnerer minor: " + toReturn);
        }else {
            return ("I DONT HAVE A MOOD");
        }
        return toReturn;
    }


    public static String getChordProgression2(Mood mood, String part, int[] partSetup) {



        Random rnd = new Random();
        Double rndNr;

        String[][] chordClasses;


        String toReturn = "";

        int chordClass; //0 = TONIC, 1 = PREDOMINANT, 2 = DOMINANT
        //int[] partSetup = {0,1,2,2};



        // CONSIDER REVISE THIS, BASED ON HOW TO STRUCTURE CODE AND HOW MOODS COME IN
        if (mood == Mood.HAPPY){
            chordClasses = majorClasses;


            for (int chordNr = 0; chordNr < partSetup.length; chordNr++){
                rndNr = rnd.nextDouble();

                System.out.println(rndNr);

                //MAJOR TONIC
                if (partSetup[chordNr] == 0){
                    if(rndNr < 0.33){
                        toReturn = toReturn + chordClasses[partSetup[chordNr]][0] + " ";
                    }
                    else if(rndNr < 0.67){
                        toReturn = toReturn + chordClasses[partSetup[chordNr]][1] + " ";
                    }
                    else {
                        toReturn = toReturn + chordClasses[partSetup[chordNr]][2] + " ";
                    }
                }

                // MAJOR PREDOMINANT
                if (partSetup[chordNr] == 1){
                    if(rndNr < 0.5){
                        toReturn = toReturn + chordClasses[partSetup[chordNr]][0] + " ";
                    }

                    else{
                        toReturn = toReturn + chordClasses[partSetup[chordNr]][1] + " ";
                    }
                }

                // MAJOR DOMINANT
                if (partSetup[chordNr] == 2){
                    if(rndNr < 0.7){
                        toReturn = toReturn + chordClasses[partSetup[chordNr]][0] + " ";
                    }
                    else {
                        toReturn = toReturn + chordClasses[partSetup[chordNr]][1] + " ";
                    }
                }
            }
        }

        else if(mood == Mood.SAD){
            chordClasses = minorClasses;

            for (int chordNr = 0; chordNr < partSetup.length; chordNr++){
                rndNr = rnd.nextDouble();

                //MINOR TONIC
                if (partSetup[chordNr] == 0){
                    if(rndNr < 0.5){
                        toReturn = toReturn + chordClasses[0][0] + " ";
                    }

                    else{
                        toReturn = toReturn + chordClasses[0][1] + " ";
                    }
                }


                // MAJOR PREDOMINANT

                if (partSetup[chordNr] == 1){
                    if(rndNr < 0.45){
                        toReturn = toReturn + chordClasses[1][0] + " ";
                    }
                    else if(rndNr < 0.55){
                        toReturn = toReturn + chordClasses[1][1] + " ";
                    }
                    else {
                        toReturn = toReturn + chordClasses[1][2] + " ";
                    }
                }

                // MINOR DOMINANT
                if (partSetup[chordNr] == 2){
                    if(rndNr < 0.9){
                        toReturn = toReturn + chordClasses[2][0] + " ";
                    }
                    else {
                        toReturn = toReturn + chordClasses[2][1] + " ";
                    }
                }
            }

        }
        else {
            System.out.println("We have no mood");
            return toReturn;
        }


        return toReturn;


    }
}





