package framework.util.persistent;

import java.io.Serializable;
import java.util.ArrayList;

import framework.rpgsystem.evolution.Feature;

public class ListFeature implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Feature> features;
	
	public ListFeature(){
		features = new ArrayList<Feature>();
	}
	
	public void addFeature(Feature feature){
		features.add(feature);
	}
	
	public void removeFeature(Feature feature){
		features.remove(feature);
	}
	
	public void removeFeature(int feature){
		features.remove(feature);
	}
	
	public void cleanFeatures(){
		features.clear();
	}
}
