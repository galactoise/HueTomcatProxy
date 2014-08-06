package com.galactoise.hueproxy.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.galactoise.hueproxy.client.model.LightState;

@JsonSerialize(include=Inclusion.NON_NULL)
public class StateUpdateWrapperV2 {

	private LightState stateUpdate;
	private StateUpdateType type;
	private Integer postTransitionDuration;	//sleep time after this event - may need refactoring
	private Integer target;					//Can be a light id or a group id
	
	public LightState getStateUpdate() {
		return stateUpdate;
	}
	
	public void setStateUpdate(LightState stateUpdate) {
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
