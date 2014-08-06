package com.galactoise.hueproxy.client.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonFilter("LightState")
@JsonSerialize(include = Inclusion.NON_NULL)
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

	public void setBrightness(Integer brightness){
		this.bri = brightness;
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

	public void setSaturation(Integer saturation){
		this.sat = saturation;
	}
	
	public void setSat(Integer sat) {
		this.sat = sat;
	}

	public Integer getCt() {
		return ct;
	}

	public void setColorTemperature(Integer colorTemperature){
		this.ct = colorTemperature;
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
	
	@JsonProperty("alert")
	public String getAlertFieldValue(){
		if(getAlert() == null){ 
			return null;
		} else{
			return getAlert().getAlertFieldValue();
		}
	}

	@JsonIgnore
	public void setAlert(AlertEnum alert) {
		this.alert = alert;
	}
	
	@JsonProperty("alert")
	public void setAlert(String alert){
		for(AlertEnum enumValue : AlertEnum.values()){
			if(enumValue.getAlertFieldValue().equalsIgnoreCase(alert)){
				setAlert(enumValue);
			}
		}
	}

	@JsonIgnore
	public EffectEnum getEffect() {
		return effect;
	}
	
	@JsonProperty("effect")
	public String getEffectFieldValue(){
		if(getEffect() == null){
			return null;
		}else{
			return getEffect().getEffectFieldValue();
		}
	}
	
	@JsonIgnore
	public void setEffect(EffectEnum effect) {
		this.effect = effect;
	}
	
	@JsonProperty("effect")
	public void setEffect(String effect){
		for(EffectEnum enumValue : EffectEnum.values()){
			if(enumValue.getEffectFieldValue().equalsIgnoreCase(effect)){
				setEffect(enumValue);
			}
		}
	}

	@JsonIgnore
	public ColorModeEnum getColorMode() {
		return colorMode;
	}

	@JsonProperty("colormode")
	public String getColorModeFieldValue(){
		if(getColorMode() == null){
			return null;
		}else{
			return getColorMode().getColorModeFieldValue();
		}
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
		NONE		("none"),
		COLORLOOP	("colorloop");
		
		private final String effectFieldValue;

		EffectEnum(String effectFieldValue){
			this.effectFieldValue = effectFieldValue;
		}
		
		public String getEffectFieldValue() {
			return effectFieldValue;
		}
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
