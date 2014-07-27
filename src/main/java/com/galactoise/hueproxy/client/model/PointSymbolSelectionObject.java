package com.galactoise.hueproxy.client.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class PointSymbolSelectionObject {
	
	private String symbolSelection;
	private int duration;

	@JsonProperty("symbolselection")
	public String getSymbolSelection() {
		return symbolSelection;
	}

	public void setSymbolSelection(String symbolSelection) {
		this.symbolSelection = symbolSelection;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
