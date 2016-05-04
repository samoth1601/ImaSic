package Jfugue;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.ChordProgression;

import java.util.Random;

/**
 * Created by Thomas on 31.03.2016.
 */
public class EksempelMain {

    public static void main (String[] args) {
        //make random
        String[] possiblePantoMajor = new String[10];
        possiblePantoMajor[0] = "Cs ";
        possiblePantoMajor[1] = "Ds ";
        possiblePantoMajor[2] = "Es ";
        possiblePantoMajor[3] = "Gs ";
        possiblePantoMajor[4] = "As ";
        possiblePantoMajor[5] = "Rs ";
        possiblePantoMajor[6] = "Rs ";
        possiblePantoMajor[7] = "Rs ";
        possiblePantoMajor[8] = "Rs ";
        possiblePantoMajor[9] = "Rs ";


        String[] possiblePantoMinor = new String[8];
        possiblePantoMinor[0] = "Cs ";
        possiblePantoMinor[1] = "Ds ";
        possiblePantoMinor[2] = "Ebs ";
        possiblePantoMinor[3] = "Gs ";
        possiblePantoMinor[4] = "Bbs ";
        possiblePantoMinor[5] = "Rs ";
        possiblePantoMinor[6] = "Rs ";
        possiblePantoMinor[7] = "Rs ";


        Random rnd = new Random();
        String patternString = "";
        for (int i = 0; i < 14; i++) {
            patternString += possiblePantoMajor[rnd.nextInt(10)];
        }
        patternString += patternString;
        patternString += patternString;
        patternString += patternString;

        System.out.println(patternString);


        ScaleGenerator possibleScales = new ScaleGenerator();

        Pattern test = new Pattern(possibleScales.getScale("C", ScaleType.Major));
        System.out.println(test);

        Pattern p1 = new Pattern(patternString).setVoice(2).setInstrument("PIANO");

        Pattern pattern = new ChordProgression("I V vi IV").setKey("C")
                //Pattern pattern = new ChordProgression("I vi IV V").setKey("C4min")
                //Pattern pattern = new ChordProgression("VI VII i i").setKey("C4min")
                //.distribute("7%6")
                //.allChordsAs("$0 $1 $2 $0 $1 $1 $0 $0 $2 $1 $0 $0")
                .allChordsAs("$0 $0 $1 $1 $2 $2 $3 $3 ")
                //.eachChordAs("$0ia100 $1ia100 $2ia100 $0ia100")
                .getPattern()
                .setInstrument(32)
                .setVoice(1);

        Pattern pattern2 = new ChordProgression("I vi IV V").setKey("C5")
                //.distribute("7%6")
                //.allChordsAs("$0 $1 $2 $0 $1 $1 $0 $0 $2 $1 $0 $0")
                .allChordsAs("$0 $0 $1 $1 $2 $2 $3 $3 ")
                //.eachChordAs("$0ia100 $0ia100")
                .getPattern()
                .setInstrument("Piano")
                .setVoice(1);
        pattern.add(pattern);
        pattern.add(pattern);

        Rhythm rhythm = new Rhythm()
                .addLayer("O..oO...O..oOO..O..oO...O..oOO..O..oO...O..oOO..O..oO...O..oOO..")
                .addLayer("..S...S...S...S...S...S...S...S...S...S...S...S...S...S...S...S.")
                .addLayer("````````````````````````````````````````````````````````````````")
                .addLayer(".......+.......+.......+.......+.......+.......+.......+.......+");
        Player player = new Player();

        //player.play(pattern.repeat(2), rhythm.getPattern().repeat(4));
        player.play(p1.repeat(2),rhythm.getPattern().repeat(6), pattern.repeat(4));

        //player.play(p1.setTempo(80).repeat(2),pattern,rhythm);
    }
}

