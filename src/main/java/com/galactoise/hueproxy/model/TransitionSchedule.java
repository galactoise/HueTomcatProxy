package com.galactoise.hueproxy.model;

import java.util.LinkedList;

import nl.q42.jue.StateUpdate;

public class TransitionSchedule {

	protected LinkedList<StateUpdate> states;
	protected boolean loop;
	protected boolean random;
	protected int numLoops;
	
	public LinkedList<StateUpdate> getStates() {
		return states;
	}
	
	public void setStates(LinkedList<StateUpdate> states) {
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
