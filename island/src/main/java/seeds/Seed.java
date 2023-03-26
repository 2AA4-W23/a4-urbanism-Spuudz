package seeds;

import java.util.Map;

import configuration.Configuration;
import IslandADT.*;

public class Seed {
    private String seed;
    private Boolean inputString;
    private static final int placeHolderLength=6; 

    public Seed(Configuration config){
        seed = (config.seed());
        if(seed.length()>0){
            inputString=true;
        }else{
            inputString=false;
        }
    }
    public Seed(){
        seed="";
        inputString=false;
    }

    public void addToSeed(String addition){
        int num = addition.length();
        String newString = "";
        for(int i=0;i<placeHolderLength-num;i++){
            newString+="0";
        }
        newString+=addition;
        seed=seed+newString;
    }

    public String getSeed(){
        return seed;
    }

    public Boolean input(){
        return inputString;
    }

    public void shaveString(){
        seed=seed.substring(6);
    }
    public int returnCurrent(){
        int idx=Integer.parseInt(seed.substring(0,6));
        System.out.println(idx);
        shaveString();
        return idx;
        
    }

}
