package com.galactoise.hueproxy.service.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;

import com.galactoise.hueproxy.client.model.Light;

@Path("/v2/lights")
@Produces(MediaType.APPLICATION_JSON)
public class LightsResourceV2 extends AbstractHueProxyResource {

	@GET
	public HashMap<String,Light> getLights(){
		try {
			HashMap<String, Light> lights = client.getLights();
			//Take the JSON field key and add it as an ID of each object
			for(String key : lights.keySet()){
				lights.get(key).setId(key);
			}
			return lights;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not get lights due to exception..." + e);
			return null;
		}
	}
	
	@GET
	@Path("/{id}")
	public Light getLightById(@PathParam("id") String lightId){
	
		try {
			Light light = client.getLightById(lightId);
			light.setId(lightId);
			return light;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not get light by id due to exception..." + e);
			return null;
		}
	}
	
	/*@PUT
	@Path("/{id}/ravetime")
	public void doRavetimeById(@PathParam("id") String lightId){
		try {
			bridge.setPointSymbolById(lightId, "{\"1\":\"0A00F1F01F1F1001F1FF100000000001F2F\"}");
			bridge.transmitSymbolToGroupById("0", "{\"symbolselection\":\"01010501010102010301040105\",\"duration\":5000}");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bridge.transmitSymbolToGroupById("0", "{\"symbolselection\":\"01010501010102010301040105\",\"duration\":5000}");
		} catch (IOException | ApiException e) {
			e.printStackTrace();
		}
	}*/
	
	@PUT
	@Path("/{id}/togglePower")
	public Light toggleLightPower(@PathParam("id") String lightId){
		try {
			Light light = client.getLightById(lightId);
			light.getState().setOn(!light.getState().getOn());
			
			//Do update
			

			light.setId(lightId);
			return light;			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not get light by id due to exception..." + e);
			return null;
		}
	}
	
/*	@PUT
	@Path("/{id}")
	public String updateLight(@PathParam("id") String lightId, LightUpdateRequest lightToUpdate){
		try{
			FullLight light = bridge.getLightById(lightId);
			return "{\"test\":\"WHOOOO\"}";
		} catch(IOException | ApiException e){
			e.printStackTrace();
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}*/
}
