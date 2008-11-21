package framework.util.persistent;

import java.util.Date;

import org.prevayler.Transaction;

import framework.rpgsystem.evolution.Feature;

public class AddFeature implements Transaction{

	private static final long serialVersionUID = 1L;
	
	private Feature tempFeature;
	
	public AddFeature(){}
	
	public AddFeature(Feature toKeep){
		tempFeature = toKeep;
	}

	public void executeOn(Object feature, Date arg1) {
		((DataCollection)feature).getFeatures().addFeature(tempFeature);		
	}
}
