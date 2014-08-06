package com.galactoise.hueproxy.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include=Inclusion.NON_NULL)
public class LightsTemplateV2 {

	protected StateUpdateWrapperV2[] states;
	protected Boolean loop;
	protected Boolean random;
	protected Integer numLoops;
	
	public StateUpdateWrapperV2[] getStates() {
		return states;
	}
	
	public void setStates(StateUpdateWrapperV2[] states) {
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
