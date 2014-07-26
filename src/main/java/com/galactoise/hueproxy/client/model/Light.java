package com.galactoise.hueproxy.client.model;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class Light {

	private LightState state;
	private String name;
	private Map<String,String> pointSymbol;
	private String swVersion;
	private String modelId;
	private String type;
	private String id; //Hue indexes as the json key, but it's useful to have here too
	
	public LightState getState() {
		return state;
	}
	
	public void setState(LightState state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("pointsymbol")
	public Map<String,String> getPointSymbol() {
		return pointSymbol;
	}

	@JsonProperty("pointsymbol")
	public void setPointSymbol(HashMap<String,String> pointSymbol) {
		this.pointSymbol = pointSymbol;
	}

	@JsonProperty("swversion")
	public String getSwVersion() {
		return swVersion;
	}

	@JsonProperty("swversion")
	public void setSwVersion(String swVersion) {
		this.swVersion = swVersion;
	}

	@JsonProperty("modelid")
	public String getModelId() {
		return modelId;
	}

	@JsonProperty("modelid")
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
