package framework.util.persistent;

import java.util.Date;

import org.prevayler.Transaction;

import framework.rpgsystem.evolution.Feature;

public class RemoveFeature implements Transaction{

	private static final long serialVersionUID = 1L;
	
	private Feature removeFeature;
	
	public RemoveFeature(){}
	
	public RemoveFeature(Feature toRemove){
		removeFeature = toRemove;
	}
	
	public void executeOn(Object feature, Date arg1) {
		((DataCollection)feature).getFeatures().removeFeature(removeFeature);
	}	
}
