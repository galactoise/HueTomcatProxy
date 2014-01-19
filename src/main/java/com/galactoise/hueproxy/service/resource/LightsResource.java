package com.galactoise.hueproxy.service.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.q42.jue.FullLight;
import nl.q42.jue.Light;
import nl.q42.jue.StateUpdate;
import nl.q42.jue.exceptions.ApiException;

@Path("/lights")
@Produces(MediaType.APPLICATION_JSON)
public class LightsResource extends AbstractHueProxyResource {

	@GET
	public List<Light> getAllLights(){
		try {
			return bridge.getLights();
		} catch (IOException | ApiException e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	@GET
	@Path("/{id}")
	public Light getLightById(@PathParam("id") String lightId){
	
		try {
			return bridge.getLightById(lightId);
		} catch (IOException | ApiException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PUT
	@Path("/{id}/ravetime")
	public void doRavetimeById(@PathParam("id") String lightId){
		try {
//			bridge.setLightStateById(lightId, stateUpdate);
			bridge.setPointSymbolById(lightId, "{\"1\":\"0A00F1F01F1F1001F1FF100000000001F2F\"}");
			bridge.transmitSymbolToGroupById("0", "{\"symbolselection\":\"01010501010102010301040105\",\"duration\":5000}");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bridge.transmitSymbolToGroupById("0", "{\"symbolselection\":\"01010501010102010301040105\",\"duration\":5000}");
		} catch (IOException | ApiException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/{id}/togglePower")
	public void toggleLightPower(@PathParam("id") String lightId){
		try {
			FullLight light = bridge.getLightById(lightId);
			StateUpdate update = new StateUpdate();
			update.setOn(!light.getState().isOn());
			bridge.setLightStateById(lightId, update);
		} catch (IOException | ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
