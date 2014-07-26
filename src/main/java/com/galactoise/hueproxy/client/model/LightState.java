package com.galactoise.hueproxy.client.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class LightState {

	private Boolean on;
	private Boolean reachable;
	private Integer bri;
	private Integer hue;
	private Integer sat;
	private Integer ct;
	private Double[] xy;
	private AlertEnum alert;
	private EffectEnum effect;
	private ColorModeEnum colorMode;
	
	public Boolean getOn() {
		return on;
	}

	public void setOn(Boolean on) {
		this.on = on;
	}

	public Boolean getReachable() {
		return reachable;
	}

	public void setReachable(Boolean reachable) {
		this.reachable = reachable;
	}

	public Integer getBri() {
		return bri;
	}

	public void setBri(Integer bri) {
		this.bri = bri;
	}

	public Integer getHue() {
		return hue;
	}

	public void setHue(Integer hue) {
		this.hue = hue;
	}

	public Integer getSat() {
		return sat;
	}

	public void setSat(Integer sat) {
		this.sat = sat;
	}

	public Integer getCt() {
		return ct;
	}

	public void setCt(Integer ct) {
		this.ct = ct;
	}

	public Double[] getXy() {
		return xy;
	}

	public void setXy(Double[] xy) {
		this.xy = xy;
	}

	@JsonIgnore
	public AlertEnum getAlert() {
		return alert;
	}

	@JsonIgnore
	public void setAlert(AlertEnum alert) {
		this.alert = alert;
	}

	@JsonIgnore
	public EffectEnum getEffect() {
		return effect;
	}
	
	@JsonIgnore
	public void setEffect(EffectEnum effect) {
		this.effect = effect;
	}

	@JsonIgnore
	public ColorModeEnum getColorMode() {
		return colorMode;
	}

	@JsonProperty("colormode")
	public String getColorModeFieldValue(){
		return getColorMode().getColorModeFieldValue();
	}

	@JsonIgnore
	public void setColorMode(ColorModeEnum colorMode) {
		this.colorMode = colorMode;
	}
	
	@JsonProperty("colormode")
	public void setColorMode(String colorMode){
		for(ColorModeEnum enumValue : ColorModeEnum.values()){
			if(enumValue.getColorModeFieldValue().equalsIgnoreCase(colorMode)){
				setColorMode(enumValue);
			}
		}
	}

	public enum AlertEnum{
		NONE	("none"),
		SELECT	("select"),
		LSELECT	("lselect");
		
		private final String alertFieldValue;
		
		AlertEnum(String alertFieldValue){
			this.alertFieldValue = alertFieldValue;
		}

		public String getAlertFieldValue() {
			return alertFieldValue;
		}
	}
	
	public enum EffectEnum{
		NONE,
		COLORLOOP
	}
	
	public enum ColorModeEnum{
		HS	("hs"),
		XY	("xy"),
		CT	("ct");
		
		private final String colorModeFieldValue;
		
		ColorModeEnum(String colorModeFieldValue){
			this.colorModeFieldValue = colorModeFieldValue;
		}

		public String getColorModeFieldValue() {
			return colorModeFieldValue;
		}
	}
}
