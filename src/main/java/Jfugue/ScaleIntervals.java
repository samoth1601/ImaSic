package Jfugue;

/**
 * Created by Thomas on 02.03.2016.
 */
public class ScaleIntervals {
    static int[] Major = {2,2,1,2,2,2,1};
    static int[] Minor = {2,1,2,2,1,2,2};
    static int[] MajorPentatonic = {2, 2, 3, 2, 3};
    static int[] MinorPentatonic = {3,2,2,3,2};

    private ScaleIntervals() {
    }

    public static int[] getIntervals(ScaleType scaleType){
        switch(scaleType){
            case Major:
                return Major;
            case Minor:
               return Minor;
            case MajorPentatonic:
                return MajorPentatonic;
            case MinorPentatonic:
                return MinorPentatonic;
            default : return null;//optional
        }
    }

}
