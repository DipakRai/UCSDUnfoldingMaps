package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
//Parsing library
import parsing.ParseFeed;
//Processing library
import processing.core.PApplet;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(900, 700);
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    /*if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	System.out.println("@#@ magObj ="+magObj);
	    	float mag = Float.parseFloat(magObj.toString());
	    	System.out.println("@#@ mag ="+mag);
	    	// PointFeatures also have a getLocation method
	    	SimplePointMarker marker = createMarker(f);
	    	markers.add(marker);	    	
	    }*/
	    
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
	    int blue=color(0,0,255);
	    int red=color(255,0,0);
	    //TODO: Add code here as appropriate
	    for (PointFeature pointFeature : earthquakes) {
			System.out.println("!$!$ pointFeature =" + pointFeature.getLocation() + " @#$#@ magnitude ="
					+ pointFeature.getProperty("magnitude"));
			markers.add(createMarker(pointFeature));
		}

	    SimplePointMarker smp = null;
	    float magnitude=0;
	    List<Marker> updatedMarkers = new ArrayList<Marker>();
	    for (Marker mk :markers) {
	    	smp=(SimplePointMarker)mk;
	    	magnitude =(float) smp.getProperty("magnitude"); 
	    	if (magnitude>=5.0) {
	    		smp.setColor(red);
	    		smp.setRadius(20);
	    	} else if(magnitude>=4.0 && magnitude<=4.9){
	    		smp.setColor(yellow);
	    		smp.setRadius(10);
	    	} else if(magnitude<4.0){
	    		smp.setColor(blue);
	    		smp.setRadius(6);
	    	}
	    	mk=smp;
    		updatedMarkers.add(mk);
	    }
    	map.addMarkers(updatedMarkers);
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation(),feature.getProperties());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		fill(255,250,240);
		rect(50,50,150,250,7);
		fill(color(0));
		textAlign(LEFT,CENTER);
		textSize(12);
		text("Earthquake key", 55, 75);
		fill(color(255,0,0));
		ellipse(75,95,15,15);
		fill(color(255,255,0));
		ellipse(75,115,10,10);
		fill(color(0,0,255));
		ellipse(75,135,8,8);
		
		fill(0);
		text("5.0+ magnitude",90,95);
		text("4.0+ magnitude",90,115);
		text("Below 4.0",90,135);
		
	}
}
