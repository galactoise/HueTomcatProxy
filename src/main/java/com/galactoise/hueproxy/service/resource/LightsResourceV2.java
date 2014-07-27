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
import com.galactoise.hueproxy.client.model.PointSymbolSelectionObject;

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
	
	@PUT
	@Path("/{id}/ravetime")
	public void doRavetimeById(@PathParam("id") String lightId){
		HashMap<String, String> pointSymbols = new HashMap<String,String>();
		pointSymbols.put("1", "0A00F1F01F1F1001F1FF100000000001F2F");
		pointSymbols.put("2", "0A00F1F01F1F1001F1FF100000000001F2F");
		
		try {
			client.updatePointSymbolsByLightId(lightId, pointSymbols);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not update point symbols by id due to exception..." + e);
		}
		
		PointSymbolSelectionObject psso = new PointSymbolSelectionObject();
		psso.setDuration(2000);
		psso.setSymbolSelection("01010501010102010301040105");
		
		try {
			client.transmitPointSymbolByGroupId("0", psso);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not trigger point symbols by id due to exception..." + e);
		}
	}
	
	@PUT
	@Path("/{id}/togglePower")
	public Light toggleLightPower(@PathParam("id") String lightId){
		try {
			Light light = client.getLightById(lightId);
			light.getState().setOn(!light.getState().getOn());
			
			//Do update
			client.updateLightById(lightId, light);
			

			light.setId(lightId);
			return light;			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not get light by id due to exception..." + e);
			return null;
		}
	}
	
	@PUT
	@Path("/{id}")
	public void updateLight(@PathParam("id") String lightId, Light lightToUpdate){
		try {
			client.updateLightById(lightId, lightToUpdate);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not update light by id due to exception..." + e);
		}
	}
}
