package de.hsb.kss.mc_schnitzeljagd.logic;

public class LogicHelper {
	
    public static int TranslateType(String hintType)
    {
    	if(hintType.equals("TEXT")) return 0;
    	if(hintType.equals("IMAGE")) return 1;
    	if(hintType.equals("AUDIO")) return 2;
    	if(hintType.equals("VIDEO")) return 3;
    	
    	return 0;
    }

    public static String TranslateType(int hintType)
    {
    	if(hintType == 0) return "TEXT";
    	if(hintType == 1) return "IMAGE";
    	if(hintType == 2) return "AUDIO";
    	if(hintType == 3) return "VIDEO";
    	
    	return "TEXT";
    }

}
