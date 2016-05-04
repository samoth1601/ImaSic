package Jfugue;

import org.jfugue.pattern.Pattern;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.ChordProgression;

import java.util.Random;

/**
 * Created by Thomas on 16.03.2016.
 */
public class Song {
    Mood mood;
    String key;
    String keyRaw;

    ChordProgression songProgression;
    Melody melodyBasis;
    Pattern melodyEmpty;
    Melody melodyRefrain;
    Melody melodyRefrain2;
    Rhythm rhythm;
    Rhythm rhythmEmpty;
    String progressionRoman;
    Pattern BassLine;

    Pattern BassLineRefrain;
    String progressionRomanRefrain;
    ChordProgression refrainProgression;

    public Song (Mood mood, int tempo){
        this.mood=mood;
        Random rnd = new Random();

        //Ny sang
        //***********************
        //******* NEW SONG ******
        //***********************
        //********* KEY *********
        //***********************

        //Selects key randomly.
        String[] possibleKeys={"C","D","E","F","G","A","B"};

        key = possibleKeys[rnd.nextInt(possibleKeys.length)];
        keyRaw =key;

        String majmin = "";
        //choose Maj/Min
        //If sad: min, if happy:major.

        if (mood == Mood.HAPPY){
            majmin = "maj";
        }else if (mood == Mood.SAD){
            majmin = "min";
        }else {
            majmin = "I DONT HAVE A MOOD";
        }

        //making key in major or minor.
        key = key+majmin;
        System.out.println("Making new song in key: " + key + ". The mood of the song is: " +  mood);

        //************************************
        //******** CHORDPROGRESSION **********
        //************************************

        //Creating new chordprogression
        int[] progression = {0,1,2,2};
        progressionRoman = PossibleChordProgressions.getChordProgression2(mood,"",progression);

        System.out.println("Chordprogression is: " + progressionRoman);


        progressionRomanRefrain = PossibleChordProgressions.getChordProgression2(mood, "", progression);
        refrainProgression = new ChordProgression(progressionRomanRefrain).setKey(key);

        refrainProgression.allChordsAs("$0 $1 $2 $3");

        songProgression = new ChordProgression(progressionRoman).setKey(key);

        System.out.println("Chords in progression: " + songProgression.getPattern().toString());

        //play all chords like this.
        songProgression.allChordsAs("$0 $1 $2 $3 ");





        //**************************
        //******** MELODY **********
        //**************************
        //create new melodies for chorus and refrain.
        System.out.println("new melody in key: "+keyRaw);
        melodyBasis = new Melody(mood,keyRaw,songProgression,progressionRoman,"basis");
        System.out.println("melody: " + melodyBasis.getMelodyString());
        melodyRefrain = new Melody(mood,keyRaw,songProgression,progressionRoman,"refrain");
        melodyRefrain2 = new Melody(mood,keyRaw,songProgression,progressionRoman,"refrain");

        //create empty melody for empty sections
        String emptyPattern ="";
        for (int i = 0; i < 15; i++) {
            emptyPattern += "rs ";
        }
        melodyEmpty = new Pattern(emptyPattern);


        //**************************
        //******** BASS **********
        //**************************
        BassLine = new ChordProgression(progressionRoman).setKey(keyRaw+"2")
                .allChordsAs("$0 $1 $2 $3")
                .eachChordAs("$0")
                .getPattern()
                .setInstrument("SAWTOOTH")
                .setVoice(7);


        //**************************
        //******** RHYTHM **********
        //**************************
        RhythmGenerator generateRhythm = new RhythmGenerator(mood);
        rhythm = generateRhythm.getRhythm();
        rhythmEmpty = new Rhythm()
                .addLayer("........")
                .addLayer("........")
                .addLayer("........")
                .addLayer("........");
    }

    //**************************
    //******** GETTERS *********
    //**************************
    //using MusicBox as fallback.

    public Rhythm getRhythm() {
        return rhythm.setLength(1);
    }

    public Rhythm getRhythmEmpty() {
        return rhythmEmpty.setLength(1);
    }

    public Pattern getMelodyEmpty() {
        return ((melodyEmpty.setVoice(2).setInstrument("MUSIC_BOX")));
    }

    public Pattern getMelodyBasis() {
        return (new Pattern(melodyBasis.getMelodyString()).setVoice(2).setInstrument("MUSIC_BOX"));

    }
    public Pattern getMelodyRefrain() {
        return (new Pattern(melodyRefrain.getMelodyString()).setVoice(2).setInstrument("MUSIC_BOX"));
    }

    public Pattern getBassLine() {
        System.out.println(BassLine);
        return BassLine;
    }

    public Pattern getSongProgression() {
        Pattern pattern = songProgression
                .getPattern()
                .setInstrument("SYNTH_BASS_2")
                .setVoice(1);

        return pattern.setTempo(80);
    }

    public Pattern getSongProgressionRefrain() {
        Pattern pattern = refrainProgression
                .getPattern()
                .setInstrument("SYNTH_BASS_2")
                .setVoice(1);

        return pattern.setTempo(80);
    }

}

