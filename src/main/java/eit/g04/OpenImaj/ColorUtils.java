package eit.g04.OpenImaj;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Java Code to get a color name from rgb/hex value/awt color
 * 
 * The part of looking up a color name from the rgb values is edited from
 * https://gist.github.com/nightlark/6482130#file-gistfile1-java (that has some errors) by Ryan Mast (nightlark)
 * 
 * @author Xiaoxiao Li
 * 
 */
public class ColorUtils {

	public ColorUtils(){
		
	}
    /**
     * Initialize the color list that we have.
     */
    /**
     * @return
     */
    private ArrayList<ColorName> initColorList() {
        ArrayList<ColorName> colorList = new ArrayList<ColorName>();
        colorList.add(new ColorName("Black", 0x00, 0x00, 0x00));
        colorList.add(new ColorName("Blue", 0x00, 0x00, 0xFF));
        //colorList.add(new ColorName("BlueViolet", 0x8A, 0x2B, 0xE2));
        //colorList.add(new ColorName("Cyan", 0x00, 0xFF, 0xFF));
        colorList.add(new ColorName("Blue", 0x00, 0x00, 0x8B));//DETTE ER DARKBLUE




        //colorList.add(new ColorName("DarkGreen", 0x00, 0x64, 0x00));
        //colorList.add(new ColorName("DarkOrange", 0xFF, 0x8C, 0x00));
        //colorList.add(new ColorName("DarkRed", 0x8B, 0x00, 0x00));


        colorList.add(new ColorName("DarkRed", 0x52, 0x00, 0x00));
        colorList.add(new ColorName("DarkRed", 0x5C, 0x3E, 0x3E));

        //colorList.add(new ColorName("DarkViolet", 0x94, 0x00, 0xD3));
        //colorList.add(new ColorName("DeepPink", 0xFF, 0x14, 0x93));

        //colorList.add(new ColorName("Gold", 0xFF, 0xD7, 0x00));

        //colorList.add(new ColorName("Gray", 0x80, 0x80, 0x80));
        colorList.add(new ColorName("Green", 0x00, 0x80, 0x00));
        colorList.add(new ColorName("Green", 0xAD, 0xFF, 0x2F)); //This is actually greenYellow
        colorList.add(new ColorName("Green", 0xBB, 0xBD, 0x29)); //This is actually greenYellow
        colorList.add(new ColorName("Green", 0xBB, 0xBD, 0x29));

        colorList.add(new ColorName("GreenSick", 0x3E, 0x5C, 0x3F));

        colorList.add(new ColorName("LightBlue", 0xAD, 0xD8, 0xE6));
        colorList.add(new ColorName("DarkBlue", 0x40, 0x3E, 0x5C));


        //colorList.add(new ColorName("LightGreen", 0x90, 0xEE, 0x90));
       // colorList.add(new ColorName("LightPink", 0xFF, 0xB6, 0xC1));
  
        colorList.add(new ColorName("LightYellow", 0xFF, 0xFF, 0xE0));//DETTE ER LIGHTYELLO
        
        colorList.add(new ColorName("Orange", 0xFF, 0xA5, 0x00));
        colorList.add(new ColorName("Orange", 0xFF, 0x84, 0x00));//homemade Orange . ganske close to red.



        //colorList.add(new ColorName("OrangeRed", 0xFF, 0x45, 0x00));
        //colorList.add(new ColorName("Pink", 0xFF, 0xC0, 0xCB));
        //colorList.add(new ColorName("Purple", 0x80, 0x00, 0x80));
        colorList.add(new ColorName("Red", 0xFF, 0x00, 0x00));
        //colorList.add(new ColorName("Snow", 0xFF, 0xFA, 0xFA));
        //colorList.add(new ColorName("Violet", 0xEE, 0x82, 0xEE));
        colorList.add(new ColorName("White", 0xFF, 0xFF, 0xFF));
        colorList.add(new ColorName("Yellow", 0xFF, 0xFF, 0x00));
        colorList.add(new ColorName("Yellow", 0xE2, 0xB2, 0x40));//custom E2B240 nesten orange egentlig
        colorList.add(new ColorName("YellowGreen", 0x9A, 0xCD, 0x32));

        return colorList;
    }



    /**
     * Get the closest color name from our list
     * 
     * @param r
     * @param g
     * @param b
     * @return
     */
    public String getColorNameFromRgb(int r, int g, int b) {
        ArrayList<ColorName> colorList = initColorList();
        ColorName closestMatch = null;
        int minMSE = Integer.MAX_VALUE;
        int mse;
        for (ColorName c : colorList) {
            mse = c.computeMSE(r, g, b);
            if (mse < minMSE) {
                minMSE = mse;
                closestMatch = c;
            }
        }

        if (closestMatch != null) {
            return closestMatch.getName();
        } else {
            return "No matched color name.";
        }
    }

    /**
     * Convert hexColor to rgb, then call getColorNameFromRgb(r, g, b)
     * 
     * @param hexColor
     * @return
     */
    public String getColorNameFromHex(int hexColor) {
        int r = (hexColor & 0xFF0000) >> 16;
        int g = (hexColor & 0xFF00) >> 8;
        int b = (hexColor & 0xFF);
        return getColorNameFromRgb(r, g, b);
    }

    public int colorToHex(Color c) {
        return Integer.decode("0x"
                + Integer.toHexString(c.getRGB()).substring(2));
    }

    public String getColorNameFromColor(Color color) {
        return getColorNameFromRgb(color.getRed(), color.getGreen(),
                color.getBlue());
    }





    /*public String getMoodFromColor(Color color){
    	String mood = "";
    
    	String colorName = getColorNameFromColor(color);
    	System.out.println(colorName);
    	if (colorName.toLowerCase().contains("blue")){
    		mood += "sadness";
    	}if(colorName.toLowerCase().contains("red")) {
            mood += "faith";
        }if(colorName.toLowerCase().contains("white")){
                mood += "white";
    	}if(colorName.toLowerCase().contains("yellow")){
    		mood += "joy";
    	}if(colorName.toLowerCase().contains("orangered")){
    		mood += "hapiness";
    	}if(colorName.toLowerCase().contains("purple")){
    		mood += "discomfort";
    	}if(colorName.toLowerCase().contains("green")){
    		mood += "calm";
    	}if(colorName.toLowerCase().contains("pink")){
    		mood += "CalmingPink";
    	}
    	
    	return mood;
    }*/

    /**
     * SubClass of ColorUtils. In order to lookup color name
     * 
     * @author Xiaoxiao Li
     * 
     */
    public class ColorName {
        public int r, g, b;
        public String name;

        public ColorName(String name, int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.name = name;
        }

        public int computeMSE(int pixR, int pixG, int pixB) {
            return (int) (((pixR - r) * (pixR - r) + (pixG - g) * (pixG - g) + (pixB - b)
                    * (pixB - b)) / 3);
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }

        public int getB() {
            return b;
        }

        public String getName() {
            return name;
        }
    }
}