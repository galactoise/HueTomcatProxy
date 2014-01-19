package com.galactoise.hueproxy.service.resource;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.q42.jue.exceptions.ApiException;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource extends AbstractHueProxyResource{

	@GET
	public String testMethod(){
		
		/*try {
			bridge.authenticate(username);
		} catch (IOException | ApiException e) {
			LOGGER.severe("Could not authenticate due to exception.");
		}*/
		try {
			return "{\"testKey\":\"" + bridge.getFullConfig().toString() + "\"}";
		} catch (IOException | ApiException e) {
			LOGGER.severe("Could not retrieve full config.");
			return "Failed to retrieve config."; 
		}

		//HueJavaSDK implementation
		/*bridge.authenticate(false);
		return "{\"testKey\":\"" + bridge.getGroups().toString() + "\"}";*/
	}
}
