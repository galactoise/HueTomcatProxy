package com.galactoise.hueproxy.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import nl.q42.jue.StateUpdate;

@JsonSerialize(include=Inclusion.NON_NULL)
public class StateUpdateWrapper {

	private StateUpdate stateUpdate;
	private StateUpdateType type;
	private Integer postTransitionDuration;
	private Integer target;
	
	public StateUpdate getStateUpdate() {
		return stateUpdate;
	}
	
	public void setStateUpdate(StateUpdate stateUpdate) {
		this.stateUpdate = stateUpdate;
	}
	
	public StateUpdateType getType() {
		return type;
	}
	
	public void setType(StateUpdateType type) {
		this.type = type;
	}
	
	public Integer getPostTransitionDuration() {
		return postTransitionDuration;
	}
	
	public void setPostTransitionDuration(Integer postTransitionDuration) {
		this.postTransitionDuration = postTransitionDuration;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}
}
