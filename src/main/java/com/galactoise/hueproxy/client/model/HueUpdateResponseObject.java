package com.galactoise.hueproxy.client.model;

import java.util.HashMap;

public class HueUpdateResponseObject {
	
	private HashMap<String,Object> success;
	private HueErrorResponseObject error;
	
	public HashMap<String, Object> getSuccess() {
		return success;
	}
	
	public void setSuccess(HashMap<String, Object> success) {
		this.success = success;
	}
	
	public HueErrorResponseObject getError() {
		return error;
	}
	
	public void setError(HueErrorResponseObject error) {
		this.error = error;
	}
}
