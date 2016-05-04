package Jfugue;
import java.util.ArrayList;
import java.util.List;


public class EuclideanRhythm{
    private List<Integer> pattern = new ArrayList<Integer>();
    private List<Integer> counts = new ArrayList<Integer>();
    private List<Integer> remainders = new ArrayList<Integer>();

    private int level;
    private int divisor;

    private String stringPattern;

    private char hit;
    private char pause;

    //TRY DIFFERENT RETURNTYPES. STRINGS, ARRAYS, WHATEVVAH YOU WANT
    public void bjorklund(int steps, int pulses, char hit, char pause) {

        this.hit = hit;
        this.pause = pause;

        if (pulses > steps) {
            System.out.println("ValueError");
            return;
        }

        divisor = steps - pulses;
        remainders.add(pulses);
        level = 0;

        while (true) {
            counts.add(divisor/remainders.get(level));
            remainders.add(divisor % remainders.get(level));
            divisor = remainders.get(level);

            level++;
            if(remainders.get(level) <= 1){
                break;
            }
        }
        counts.add(divisor);

        System.out.println("Counts:     " + counts);
        System.out.println("Remainders: " + remainders);
        System.out.println("Level:      " + level);


        build(level);


        int i = pattern.indexOf(1);
        System.out.println("i:          " + i);


        pattern.addAll(pattern.subList(i,pattern.size()));
        pattern.addAll(pattern.subList(0,i));


        for(int u = 0; u < steps; u++){
            pattern.remove(0);
        }


        System.out.println("Pattern:  " + pattern);





    }

    private void build (int level){
        if(level == -1){
            pattern.add(0);
        }
        else if(level == -2){
            pattern.add(1);
        }
        else {
            for(int i=0; i < counts.get(level);i++){
                build(level-1);
            }
            if(remainders.get(level) != 0){
                build(level-2);
            }
        }
    }

    public void rotateRhythm(int rotationSteps){
        List<Integer> rotated = pattern;

        rotated.addAll(pattern.subList(0,rotationSteps));
        for(int i = 0; i < rotationSteps; i++){
            rotated.remove(0);
        }

        pattern = rotated;
    }

    public String getStringPattern() {
        List<Character> charPattern = new ArrayList<Character>();

        for(int j = 0; j < pattern.size();j++){
            if(pattern.get(j) == 1) {
                charPattern.add(hit);
            }
            else {
                charPattern.add(pause);
            }
        }
        stringPattern = "";
        for(int k = 0; k < pattern.size(); k++){
            stringPattern = stringPattern + charPattern.get(k);
        }

        //System.out.println(charPattern);
        //System.out.println(stringPattern);


        return stringPattern;
    }
}
