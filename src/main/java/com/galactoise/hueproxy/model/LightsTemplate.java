package com.galactoise.hueproxy.model;

import java.util.LinkedList;

public class LightsTemplate {

	protected LinkedList<StateUpdateWrapper> states;
	protected boolean loop;
	protected boolean random;
	protected int numLoops;
	
	public LinkedList<StateUpdateWrapper> getStates() {
		return states;
	}
	
	public void setStates(LinkedList<StateUpdateWrapper> states) {
		this.states = states;
	}
	
	public boolean isLoop() {
		return loop;
	}
	
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public boolean isRandom() {
		return random;
	}
	
	public void setRandom(boolean random) {
		this.random = random;
	}
	
	public int getNumLoops() {
		return numLoops;
	}
	
	public void setNumLoops(int numLoops) {
		this.numLoops = numLoops;
	}
}
