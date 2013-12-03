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
public class Point implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8096107369809459221L;
	private int pointId;
    private double longitude;
    private double latitude;
    private String name;
    private String description;
    
    @OneToMany(cascade=CascadeType.ALL)
    private List<Riddle> riddles=new ArrayList<Riddle>();
    
    @OneToMany(cascade=CascadeType.ALL)
    private List<Hint> hintList =new ArrayList<Hint>();    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    
    public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
    
    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Riddle> getRiddles() {
        return riddles;
    }

    public void setRiddles(List<Riddle> riddles) {
        this.riddles = riddles;
    }

    public List<Hint> getHintList() {
        return hintList;
    }

    public void setHintList(List<Hint> hintList) {
        this.hintList = hintList;
    }

}
