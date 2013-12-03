package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;

@Entity
public class Quest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7620533703445783089L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    private int questId;
	private String name;
    private String author;
    private String accessCode;
    @OneToMany(cascade=CascadeType.ALL)
    private List<Point> pointList= new ArrayList<Point>();
    
    public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }
    
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//TODO: Meleanie sch�n machen mit Strings xml und so
    public String toString()
    {
    	return "Author= " + getAuthor() + "\n No. waypoints= 2";    
    }
}
