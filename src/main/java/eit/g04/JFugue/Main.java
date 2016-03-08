package eit.g04.JFugue;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.ChordProgression;

import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.util.Random;


public class Main {
    public static void main(String[] args) throws IOException, MidiUnavailableException {

        //make random
        String[] possiblePantoMajor = new String[8];
        possiblePantoMajor[0]="Cs ";
        possiblePantoMajor[1]="Ds ";
        possiblePantoMajor[2]="Es ";
        possiblePantoMajor[3]="Gs ";
        possiblePantoMajor[4]="As ";
        possiblePantoMajor[5]="Rs ";
        possiblePantoMajor[6]="Rs ";

        String[] possiblePantoMinor = new String[8];
        possiblePantoMinor[0]="Cs ";
        possiblePantoMinor[1]="Ds ";
        possiblePantoMinor[2]="Ebs ";
        possiblePantoMinor[3]="Gs ";
        possiblePantoMinor[4]="Bbs ";
        possiblePantoMinor[5]="Rs ";
        possiblePantoMinor[6]="Rs ";


        Random rnd = new Random();
        String patternString = "";
        for (int i = 0; i < 24; i++) {
            patternString+=possiblePantoMajor[rnd.nextInt(7)];
        }
        patternString+=patternString;

        patternString+=patternString;

        patternString+=patternString;

        System.out.println(patternString);


                //cdega
        Pattern p1 = new Pattern(patternString).setVoice(2);

        Pattern pattern = new ChordProgression("I V vi IV").setKey("C")
        //Pattern pattern = new ChordProgression("I vi IV V").setKey("C4min")
        //Pattern pattern = new ChordProgression("VI VII i i").setKey("C4min")
                //.distribute("7%6")
                //.allChordsAs("$0 $1 $2 $0 $1 $1 $0 $0 $2 $1 $0 $0")
                .allChordsAs("$0 $0 $1 $1 $2 $2 $3 $3 ")
                //.eachChordAs("$0ia100 $0ia100")
                .getPattern()
                .setInstrument("XYLOPHONE")
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
        //player.play(p1.repeat(2),rhythm.getPattern().repeat(6), pattern.repeat(4));
        player.play(p1.setTempo(80).repeat(2),pattern,rhythm);

    }
}
