package com.galactoise.hueproxy.model;

import nl.q42.jue.StateUpdate;

public class StateUpdateWrapper {

	private StateUpdate stateUpdate;
	private StateUpdateType type;
	private int postTransitionDuration;
	
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
	
	public int getPostTransitionDuration() {
		return postTransitionDuration;
	}
	
	public void setPostTransitionDuration(int postTransitionDuration) {
		this.postTransitionDuration = postTransitionDuration;
	}
}
