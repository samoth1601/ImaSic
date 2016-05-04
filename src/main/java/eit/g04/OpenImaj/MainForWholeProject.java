package eit.g04.OpenImaj;

import Jfugue.Mood;
import Jfugue.Song;
import org.jfugue.player.Player;
import org.jfugue.player.SynthesizerManager;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.connectedcomponent.GreyscaleConnectedComponentLabeler;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.processing.resize.ResizeProcessor;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;

import javax.sound.midi.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * OpenIMAJ Hello world!
 *
 */
public class MainForWholeProject {


    /*****
     * Moods
     *****/
    static float sad = 0;
    static float calm = 0;
    static float happy = 0;
    static float sick = 0;
    static float excited = 0;
    static float scared = 0;
    static float angry = 0;
    static float light = 1;

    public static void main(String[] args) throws IOException, MidiUnavailableException, InvalidMidiDataException {
        final ColorUtils colorUtils = new ColorUtils();


        MBFImage input = null;
        ;
        try {
            input = ImageUtilities.readMBF(new File("./resources/Rural.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //If Image is very big, half image to reduce computation time.
        //input = ResizeProcessor.halfSize(input);
        //input = ResizeProcessor.halfSize(input);
        //input = ResizeProcessor.halfSize(input);
        //input = ResizeProcessor.halfSize(input);
        //input = ResizeProcessor.doubleSize(input);


        //*******************************************
        //To start our implementation, we’ll first apply a colour-space transform to the image:
        input = ColourSpace.convert(input, ColourSpace.CIE_Lab);
        //We can then construct the K-Means algorithm:
        FloatKMeans cluster = FloatKMeans.createExact(15);
        //The parameter (2) is the number of clusters or classes we wish the algorithm to generate.
        //We can optionally provide a second integer argument that controls the maximum number of iterations of the
        //algorithm (the default is 30 iterations if we don't specify otherwise).


        //The FloatKMeans algorithm takes its input as an array of floating point vectors (float[][]).
        //We can flatten the pixels of an image into the required form using the getPixelVectorNative() method:
        float[][] imageData = input.getPixelVectorNative(new float[input.getWidth() * input.getHeight()][3]);

        //The K-Means algorithm can then be run to group all the pixels into the requested number of classes:
        FloatCentroidsResult result = cluster.cluster(imageData);

        //Each class or cluster produced by the K-Means algorithm has an index, starting from 0.
        //Each class is represented by its centroid (the average location of all the points belonging to the class).
        //We can print the coordinates of each centroid:
        float[][] centroids = result.centroids;
        for (float[] fs : centroids) {
            //System.out.println(Arrays.toString(fs));
        }//Now is a good time to test the code. Running it should print the (L, a, b) coordinates of each of the classes.


        //takes a vector (the L, a, b value of a single pixel) and returns the index of the class that it belongs to.
        //We’ll start by creating an image that visualises the pixels and their respective classes
        //by replacing each pixel in the input image with the centroid of its respective class:
        HardAssigner<float[], ?, ?> assigner = result.defaultHardAssigner();
        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                float[] pixel = input.getPixelNative(x, y);
                int centroid = assigner.assign(pixel);
                input.setPixelNative(x, y, centroids[centroid]);
            }
        }

        //We can then display the resultant image. Note that we need to convert the image back to RGB colour space for it to display properly:
        input = ColourSpace.convert(input, ColourSpace.RGB);
        //DisplayUtilities.display(input);


        //The GreyscaleConnectedComponentLabeler class can be used to find the connected components:
        //Note that the GreyscaleConnectedComponentLabeler only processes greyscale images
        GreyscaleConnectedComponentLabeler labeler = new GreyscaleConnectedComponentLabeler();
        List<ConnectedComponent> components = labeler.findComponents(input.flatten());


        //The ConnectedComponent class has many useful methods for extracting information about the shape of the region.
        //Lets draw an image with the components numbered on it. We’ll use the centre of mass of each region to
        //position the number and only render numbers for regions that are over a certain size (50 pixels in this case):
        ArrayList<Color> colors = new ArrayList<Color>();
        int i = 0;
        for (ConnectedComponent comp : components) {

            //System.out.println(comp.calculateArea());
            if (comp.calculateArea() < input.getWidth() / 10)
                continue;


            int color1 = (int) (Double.parseDouble(comp.extractPixels1d(input).getBand(0).toString().split(" ")[0].substring(1).replace(",", ".")) * 250);
            int color2 = (int) (Double.parseDouble(comp.extractPixels1d(input).getBand(1).toString().split(" ")[0].substring(1).replace(",", ".")) * 250);
            int color3 = (int) (Double.parseDouble(comp.extractPixels1d(input).getBand(2).toString().split(" ")[0].substring(1).replace(",", ".")) * 250);

            int R = color1;
            int G = color2;
            int B = color3;

            //System.out.println(color1 + "," + color2 +"," + color3);
            Color tempColor = new Color(color1, color2, color3);
            //System.out.println(tempColor);
            float[] hsbColor = new float[3];

            float Y = (R + R + B + G + G + G) / 6;

            for (float f : hsbColor) {
                //System.out.println("hihi: " + f);
            }


            /** HERHERHER*/
            input.drawText(colorUtils.getColorNameFromColor(tempColor), comp.calculateCentroidPixel(), HersheyFont.TIMES_MEDIUM, 14, RGBColour.WHITE);
            //System.out.println(colorUtils.getColorNameFromColor(tempColor));


            colors.add(tempColor);


            String currentColor = colorUtils.getColorNameFromColor(tempColor);
            float compSize = comp.calculateArea();
            double totalSize = input.getContentArea().calculateArea();

            /**********DET VI MÅ GJØRE ER Å......*******/
            //per område
            if (currentColor.equals("Blue")) {
                calm += compSize * 0.8;
                sad += compSize * 0.9;
            } else if (currentColor.equals("LightBlue")) {
                calm += compSize * 0.1;
                sad += compSize * 0.1;
            } else if (currentColor.equals("Yellow")) {
                happy += compSize * 1;
                excited += compSize * 0.4;
                sick += compSize * 0.05;
            } else if (currentColor.equals("LightYellow")) {
                happy += compSize * 0.5;
                excited += compSize * 0.2;
                sick += compSize * 0.1;
            } else if (currentColor.equals("Orange")) {
                happy += compSize * 0.5;
                excited += compSize * 0.7;
                angry += compSize * 0.4;
            } else if (currentColor.equals("Red")) {
                angry += compSize * 0.9;
                excited += compSize * 0.8;
            } else if (currentColor.equals("DarkRed")) {
                sad += compSize * 0.1;
                angry += compSize * 0.2;
                excited += compSize * 0.1;
                sick += 0.1;
            } else if (currentColor.equals("Green")) {
                calm += compSize * 0.6;
                sick += compSize * 0.05;
                happy += compSize * 0.1;
            } else if (currentColor.equals("YellowGreen")) {
                sick += compSize * 0.8;
            } else if (currentColor.equals("BrownSick")) {
                sick += compSize * 0.6;
            } else if (currentColor.equals("DarkBlue")) {
                sick += compSize * 0.05;
                sad += compSize * 0.5;
                calm += compSize * 0.3;
            } else if (currentColor.equals("GreenSick")) {
                sick += compSize * 0.6;
            } else if (currentColor.equals("Black")) {
                sad += compSize * 0.5;
                angry += compSize * 0.6;
            }

            i++;
            light += Y;


        }
        //Finally, we can display the image with the labels:
        System.out.println();


        DisplayUtilities.display(input);

        System.out.println("sad: " + sad);
        System.out.println("calm: " + calm);
        System.out.println("happy: " + happy);
        System.out.println("sick: " + sick);
        System.out.println("excited: " + excited);
        System.out.println("scared: " + scared);
        System.out.println("angry: " + angry);
        System.out.println("lightness: " + light / i);


        /********** FIND maxMOOD **********/
        String[] theMoodsString = new String[]{"sad", "calm", "happy", "sick", "excited", "scared", "angry"};
        int[] theMoodsInt = new int[]{(int) sad, (int) calm, (int) happy, (int) sick, (int) excited, (int) scared, (int) angry};

        double max = Double.NEGATIVE_INFINITY;
        int maxIndex = 0;
        for (int j = 0; j < theMoodsInt.length; j++) {
            if (theMoodsInt[j] > max) {
                max = theMoodsInt[j];
                maxIndex = j;
            }
        }
        String theMood = theMoodsString[maxIndex];
        System.out.println(theMood);

//SYNTH & SAMPLE CHANGE FROM DEFAULT

        Soundbank soundbank;        //Declares new Soundbank instance
        SynthesizerManager newSynth;//Declares new SynthesizerManager (jFugue class);

        Synthesizer synth;          //Declares new Synthesizer instance

        synth = MidiSystem.getSynthesizer();
        synth.open();

        try {
            // Make sure gervill.jar is in your classpath
            //path to compifont. I.e.: Compifont_01032016.sf2
            soundbank = MidiSystem.getSoundbank(new File("PATH TO COMPIFONT"));

            synth.loadAllInstruments(soundbank);


            newSynth = SynthesizerManager.getInstance();

            newSynth.setSynthesizer(synth);

        } catch (MidiUnavailableException e) {
            System.out.println("Catched: MidiUnavailableExeption " + e);
        } catch (InvalidMidiDataException e) {
            System.out.println("Catched: InvalidMidiDataExeption " + e);
        } catch (IOException e) {
            System.out.println("Catched: IOExeption " + e);
        }


        //String theMood = "excited";
        System.out.println(theMood);

        /******CREATE SONG******/
        Random rnd = new Random();
        Mood songMood = Mood.HAPPY;

        int tempoSet = 120;
        String instrumentSet = "PIANO";

        if (theMood.equals("sad")) {
            tempoSet = 40;
            instrumentSet = "VIOLIN";
            songMood = Mood.SAD;

        } else if (theMood.equals("calm")) {
            tempoSet = 40;
            instrumentSet = "STRING_ENSEMBLE_1";
            songMood = Mood.HAPPY;

        } else if (theMood.equals("happy")) {
            tempoSet = 80;
            instrumentSet = "MUSIC_BOX";
            songMood = Mood.HAPPY;

        } else if (theMood.equals("sick")) {
            tempoSet = 74;
            instrumentSet = "VIOLIN";
            songMood = Mood.SAD;

        } else if (theMood.equals("excited")) {
            tempoSet = 90;
            songMood = Mood.HAPPY;
            instrumentSet = "CALLIOPE";

        } else if (theMood.equals("scared")) {
            tempoSet = 90;
            instrumentSet = "VIOLIN";
            songMood = Mood.SAD;

        } else if (theMood.equals("angry")) {
            tempoSet = 80;
            instrumentSet = "CALLIOPE";
            songMood = Mood.SAD;
        }


        Player player = new Player();
        Song yolo = new Song(songMood, tempoSet);

        //how long intro
        int repeatIntro = rnd.nextInt(2);
        System.out.println("introtimes:" + repeatIntro);

        //how many times repeat verse
        int repeatVerse = rnd.nextInt(2) + 2;
        System.out.println("versetimes:" + repeatVerse);

        //how many times repeat verse
        int repeatRefrain = rnd.nextInt(2) + 2;
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
                yolo.getMelodyRefrain().repeat(repeatRefrain).setInstrument(instrumentSet),

                /***** BASS *****/
                yolo.getBassLine().repeat(repeatIntro + repeatVerse * 1 + repeatRefrain * 2 + 1)

        );
        synth.close();  // CLEAN UP ON ISLE 4
    }

}
