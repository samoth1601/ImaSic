package eit.g04.OpenImaj;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.connectedcomponent.GreyscaleConnectedComponentLabeler;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.processing.resize.ResizeProcessor;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * OpenIMAJ Hello world!
 *
 */
public class AppIMAGE {
    static int numBins = 2;


    public static void main( String[] args ) throws IOException {
        final ColorUtils colorUtils = new ColorUtils();





        MBFImage input = null;;
        try {
            input = ImageUtilities.readMBF(new File("./resources/Star.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        input = ResizeProcessor.halfSize(input);
        //input = ResizeProcessor.halfSize(input);









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
            System.out.println(Arrays.toString(fs));
        }//Now is a good time to test the code. Running it should print the (L, a, b) coordinates of each of the classes.



        //takes a vector (the L, a, b value of a single pixel) and returns the index of the class that it belongs to.
        //We’ll start by creating an image that visualises the pixels and their respective classes
        //by replacing each pixel in the input image with the centroid of its respective class:
        HardAssigner<float[],?,?> assigner = result.defaultHardAssigner();
        for (int y=0; y<input.getHeight(); y++) {
            for (int x=0; x<input.getWidth(); x++) {
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
        Color[] colors = new Color[5];
        int i = 0;

        for (ConnectedComponent comp : components) {

            //System.out.println("CenterSTART: " + comp.calculateCentroidPixel().x + ", " + comp.calculateCentroidPixel().y);

            if (comp.calculateArea() <input.getWidth()/2) {
                continue;
            }
		    
		   /* List<Pixel> boundries = comp.getInnerBoundary(ConnectMode.CONNECT_4);
		   
		    for (int j = 0; j < boundries.size(); j++) {
				float x = boundries.get(j).getX()*32;
				float y = boundries.get(j).getY()*32;
				boundries.get(j).setX(x);
				boundries.get(j).setY(y);
			}
		    
			frame.drawConnectedPoints(boundries, RGBColour.WHITE);*/

            //comp.pixels.iterator().next().binaryHeader();
            String color888 = comp.extractPixels1d(input).getBand(0).toString().split(" ")[0].substring(1);
            System.out.println(color888);
            double color666 =  (Double.parseDouble(comp.extractPixels1d(input).getBand(0).toString().split(" ")[0].substring(1).replace(',','.'))*250);

            int color1 = (int) (Double.parseDouble(comp.extractPixels1d(input).getBand(0).toString().split(" ")[0].substring(1).replace(',','.'))*250);
            int color2 = (int) (Double.parseDouble(comp.extractPixels1d(input).getBand(1).toString().split(" ")[0].substring(1).replace(',','.'))*250);
            int color3 = (int) (Double.parseDouble(comp.extractPixels1d(input).getBand(2).toString().split(" ")[0].substring(1).replace(',','.'))*250);

            int R = color1;
            int G = color2;
            int B = color3;

            //System.out.println(color1 + "," + color2 +"," + color3);
            Color tempColor = new Color(color1, color2, color3);
            //System.out.println(tempColor);
            float[] hsbColor = new float[3];

            //System.out.println(getNameReflection(tempColor));
            //System.out.print(colorUtils.getColorNameFromColor(tempColor));


            float Y = (R+R+B+G+G+G)/6;
            System.out.println("y: " + Y);

            //System.out.println(tempColor.RGBtoHSB(color1, color2, color3, hsbColor));

            for (float f : hsbColor) {
                //System.out.println("hihi: " + f);
            }
            input.drawText("Point:" + colorUtils.getColorNameFromColor(tempColor), comp.calculateCentroidPixel(), HersheyFont.TIMES_MEDIUM, 20);
            //frame.drawText("Y:" + Y, comp.calculateCentroidPixel().x*32,comp.calculateCentroidPixel().y*32, HersheyFont.TIMES_MEDIUM, 20);


            //input.drawText(colorUtils.getMoodFromColor(tempColor), comp.calculateCentroidPixel().x,comp.calculateCentroidPixel().y, HersheyFont.TIMES_MEDIUM, 25);

            //input.drawText(colorUtils.getMoodFromColor(tempColor), comp.calculateCentroidPixel().x,comp.calculateCentroidPixel().y+10, HersheyFont.TIMES_MEDIUM, 12);
            //System.out.println("position of Jacob: " + comp.calculateCentroidPixel().x +"-"+ comp.calculateCentroidPixel().y);


            /******* CALCULATE MOOD ************/
















            //frame.drawText(colorUtils.getColorNameFromColor(tempColor), comp.calculateCentroidPixel().x*32,comp.calculateCentroidPixel().y*32, HersheyFont.TIMES_MEDIUM, 20);


            colors[i]=tempColor;
            if (i==4){
                break;
            }
            System.out.println();
            i++;
            //System.out.println("colourspace: "+comp.extractPixels1d(input).getBand(0).split("+") + "," + comp.extractPixels1d(input).getBand(1) + "," + comp.extractPixels1d(input).getBand(2));
            //System.out.println("yolo : " + comp.extractPixels1d(input).

            //System.out.println(comp.pixels.iterator().next().binaryHeader());
            //input.drawText("color: " + comp.pixels.iterator().next().binaryHeader(),comp.calculateCentroidPixel(), HersheyFont.TIMES_MEDIUM, 20);

        }
        //Finally, we can display the image with the labels:

        DisplayUtilities.display(input);





    }




    public static String getNameReflection(Color colorParam) {
        try {
            //first read all fields in array
            Field[] field = Class.forName("java.awt.Color").getDeclaredFields();
            for (Field f : field) {
                String colorName = f.getName();
                Class<?> t = f.getType();
                if (t == java.awt.Color.class) {
                    Color defined = (Color) f.get(null);
                    if (defined.equals(colorParam)) {
                        System.out.println(colorName);
                        return colorName.toUpperCase();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error... " + e.toString());
        }
        return "NO_MATCH";
    }

}
