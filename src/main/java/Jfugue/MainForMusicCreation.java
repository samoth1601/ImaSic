package Jfugue;

import org.jfugue.player.Player;
import org.jfugue.player.SynthesizerManager;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

// DISSE HAR TORD LAGT TIL 13.04.2016


public class MainForMusicCreation {
    public static void main(String[] args) throws IOException, MidiUnavailableException, InvalidMidiDataException {

        //SYNTH & SAMPLE CHANGE FROM DEFAULT

        Soundbank soundbank;        //Declares new Soundbank instance
        SynthesizerManager newSynth;//Declares new SynthesizerManager (jFugue class);

        Synthesizer synth;          //Declares new Synthesizer instance

        synth = MidiSystem.getSynthesizer();
        synth.open();

        try {
            // Make sure gervill.jar is in your classpath
            //FILEPATH TO Compifont_01032016.sf2
            //soundbank = MidiSystem.getSoundbank(new File("/Users/tordolsen/IdeaProjects/GervillTest/src/Compifont_01032016.sf2"));//TORD
            soundbank = MidiSystem.getSoundbank(new File("/Users/Thomas/development/Compifont_01032016.sf2"));//THOMAS
            //soundbank = MidiSystem.getSoundbank(new File("FILEPATH TO Compifont_01032016.sf2"));//THOMAS
            System.out.println("Gervill found");

            synth.loadAllInstruments(soundbank);


            newSynth = SynthesizerManager.getInstance();

            newSynth.setSynthesizer(synth);

        }
        catch (MidiUnavailableException e) {
            System.out.println("Catched: MidiUnavailableExeption " + e);
        }
        catch (InvalidMidiDataException e) {
            System.out.println("Catched: InvalidMidiDataExeption " + e);
        }
        catch (IOException e) {
            System.out.println("Catched: IOExeption " + e);
        }






        //String theMood = "excited";
        String theMood = "calm";
        System.out.println(theMood);

        /******CREATE SONG******/
        Random rnd = new Random();
        Mood songMood = Mood.HAPPY;

        int tempoSet = 120;
        String instrumentSet = "PIANO";

        if (theMood.equals("sad")){
            tempoSet = 40;
            instrumentSet = "VIOLIN";
            songMood = Mood.SAD;

        }else if (theMood.equals("calm")){
            tempoSet = 40;
            instrumentSet = "STRING_ENSEMBLE_1";
            songMood = Mood.HAPPY;

        }else if (theMood.equals("happy")){
            tempoSet = 80;
            instrumentSet = "MUSIC_BOX";
            songMood = Mood.HAPPY;

        }else if (theMood.equals("sick")){
            tempoSet = 74;
            instrumentSet = "VIOLIN";
            songMood = Mood.SAD;

        }else if (theMood.equals("excited")){
            tempoSet = 90;
            songMood = Mood.HAPPY;
            instrumentSet = "CALLIOPE";

        }else if (theMood.equals("scared")){
            tempoSet = 90;
            instrumentSet = "VIOLIN";
            songMood = Mood.SAD;

        }else if (theMood.equals("angry")){
            tempoSet = 80;
            instrumentSet = "CALLIOPE";
            songMood = Mood.SAD;
        }


        Player player = new Player();
        Song yolo = new Song(songMood,tempoSet);

        //how long intro
        int repeatIntro = rnd.nextInt(2);
        System.out.println("introtimes:" + repeatIntro);

        //how many times repeat verse
        int repeatVerse = rnd.nextInt(2)+2;
        System.out.println("versetimes:" + repeatVerse);

        //how many times repeat verse
        int repeatRefrain = rnd.nextInt(2)+2
                ;
        System.out.println("refrainTimes:" + repeatRefrain);

        player.play(

                /***** SONGPROGRESSION *****/
                yolo.getSongProgression().repeat(repeatIntro + repeatVerse).setTempo(tempoSet),
                yolo.getSongProgressionRefrain().repeat(repeatRefrain).setTempo(tempoSet),
                yolo.getSongProgression().repeat(1).setTempo(tempoSet),
                yolo.getSongProgressionRefrain().repeat(repeatRefrain).setTempo(tempoSet),



                /***** DRUMS *****/
                yolo.getRhythmEmpty().getPattern().repeat(repeatIntro),
                yolo.getRhythm().getPattern().repeat(repeatVerse),
                yolo.getRhythm().getPattern().repeat(repeatRefrain),
                yolo.getRhythmEmpty().getPattern(),
                yolo.getRhythm().getPattern().repeat(repeatRefrain),


                /***** MELODY *****/
                yolo.getMelodyEmpty().repeat(repeatIntro),
                yolo.getMelodyBasis().repeat(repeatVerse).setInstrument(instrumentSet),
                yolo.getMelodyRefrain().repeat(repeatRefrain).setInstrument(instrumentSet),
                yolo.getMelodyBasis().repeat(1).setInstrument(instrumentSet),
                //yolo.getRhythm().getPattern().repeat(repeatRefrain).setInstrument(instrumentSet),
                yolo.getMelodyRefrain().repeat(repeatRefrain).setInstrument(instrumentSet),


                /***** BASS *****/
                yolo.getBassLine().repeat(repeatIntro+repeatVerse*1+repeatRefrain*2 + 1)

        );
        synth.close();  // CLEAN UP ON ISLE 4
    }
}