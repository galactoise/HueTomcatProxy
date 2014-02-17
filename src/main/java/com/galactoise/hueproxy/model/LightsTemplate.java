package com.galactoise.hueproxy.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include=Inclusion.NON_NULL)
public class LightsTemplate {

	protected StateUpdateWrapper[] states;
	protected Boolean loop;
	protected Boolean random;
	protected Integer numLoops;
	
	public StateUpdateWrapper[] getStates() {
		return states;
	}
	
	public void setStates(StateUpdateWrapper[] states) {
		this.states = states;
	}

	public Boolean getLoop() {
		return loop;
	}

	public void setLoop(Boolean loop) {
		this.loop = loop;
	}

	public Boolean getRandom() {
		return random;
	}

	public void setRandom(Boolean random) {
		this.random = random;
	}

	public Integer getNumLoops() {
		return numLoops;
	}

	public void setNumLoops(Integer numLoops) {
		this.numLoops = numLoops;
	}
}
