package de.hsb.kss.mc_schnitzeljagd.logic;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;

import de.hsb.kss.mc_schnitzeljagd.persistence.model.Player;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;
import de.hsb.kss.mc_schnitzeljagd.task.LoadQuest;

class AbstractGameLogic{
    protected Player player;
    protected Quest quest;
    protected Point currentPoint;
    protected List<Point>points=new ArrayList<Point>();
    protected List<Hint> hints = new ArrayList<Hint>();
    private Riddle currentMandatoryRiddle;
    


    /**
     * Creates or loads an existing quest. For new Quest creates a point.
     * @param code
     * @param creationMode
     * @return success
     */
    protected boolean loadQuest(String code, boolean creationMode)
    {
        boolean found = false;

        if( code.isEmpty() && creationMode )
        {
            quest = new Quest();
            
            currentPoint = new Point();
            quest.setPointList(new ArrayList<Point>());
    		quest.getPointList().add(currentPoint);
    		
            currentPoint.setRiddleList(new ArrayList<Riddle>());            
            currentPoint.setHintList(new ArrayList<Hint>());
            
            found = true;
        }
        else
        {
        	//TODO: load from DataStore
            //TODO: Remove Dummy Code
        	try {
				Quest loadedQuest=new LoadQuest().execute(Long.valueOf(code)).get();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	if(code.equals("42")){
        		return true;
        	}
         
            if(code.equals("3000"))
            {
                quest = new Quest();
                quest.setName("Futurama");
                quest.setAuthor("Nibbler");
                quest.setAccessCode("3000");
                Riddle r = new Riddle(); 
                Riddle r2 = new Riddle(); 
                Riddle r3 = new Riddle(); 
                
                r.setRiddleText("first question first answer");
                r.setAnswer1("one");
                r.setAnswer2("two");
                r.setAnswer3("three");
                r.setAnswer4("four");
                r.setSolution(1);
                r.setMandatory(true);
                
                
                
                r2.setRiddleText("second question first answer");
                r2.setAnswer1("one");
                r2.setAnswer2("two");
                r2.setAnswer3("three");
                r2.setAnswer4("four");
                r2.setSolution(1);
                r2.setMandatory(false);
                
                
                
                r3.setRiddleText("third question first answer");
                r3.setAnswer1("one");
                r3.setAnswer2("two");
                r3.setAnswer3("three");
                r3.setAnswer4("four");
                r3.setSolution(1);
                r3.setMandatory(true);
                
                

                
                currentPoint = new Point();
                quest.setPointList(new ArrayList<Point>());
        		quest.getPointList().add(currentPoint);
                
                currentPoint.setRiddleList(new ArrayList<Riddle>());
                currentPoint.getRiddleList().add(r);
                currentPoint.getRiddleList().add(r2);
                currentPoint.getRiddleList().add(r3);    
                
                currentPoint.setHintList(new ArrayList<Hint>());

                Hint pic = new Hint();
                currentPoint.getHintList().add(pic);         	
                pic.setDescription(ptest());
                pic.setHintType("IMAGE");
                pic.setFree(true);
                
                for(int i=2; i<=35; i++){
                	Hint h = new Hint();
                	currentPoint.getHintList().add(h);
                	h.setDescription("Description for hint: " + i);
                	h.setHintType("TEXT");
                	h.setFree(true);
                }
    
                currentMandatoryRiddle = r;
                found = true;
            }
            else
            {
                found = false;
            }
        }
        return found; // If found else false;
    }


    
    public String ptest() {

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		    final int w = 800;
		    final int h = 800;
	    Bitmap compare = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        
        int color1, color2, a, r, g, b;

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
             
                a = (int)(Math.random() * 255); 

                r = (int) (Math.random() * 255); 
                g = (int) (Math.random() * 255); 
                b =(int) (Math.random() * 255); 
                compare.setPixel(x, y, Color.argb(a, r, g, b));
            }
        }
        
	    compare.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object   
		byte[] bllll = baos.toByteArray(); 		
		String encodedImage = Base64.encodeToString(bllll, Base64.DEFAULT);
		
		return encodedImage;
    }
    public String buildCurrentQuestDescription() {
    	if(quest != null){
    		return "Author= " + quest.getAuthor() + "\n No. waypoints=" + quest.getPointList().size() + " Gamename: " + quest.getName();    
    	} else {
    		return "no quest selected";
    	}
    }

}
