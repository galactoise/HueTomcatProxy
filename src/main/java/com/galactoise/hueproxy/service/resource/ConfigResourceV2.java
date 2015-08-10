package com.galactoise.hueproxy.service.resource;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/v2/")
@Produces(MediaType.APPLICATION_JSON)
public class ConfigResourceV2 extends AbstractHueProxyResource{

	protected static final Logger LOGGER = Logger.getLogger(ConfigResourceV2.class.getName());
	
	@GET
	public Object getSimpleConfig(){
		try {
			return client.getBridgeSimpleConfiguration();
		} catch (IOException e) {
			LOGGER.severe("Couldn't get bridge configuration.");
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/config")
	public Object getFullConfig(){
		try {
			return client.getBridgeFullConfiguration();
		} catch (IOException e) {
			LOGGER.severe("Couldn't get bridge configuration.");
			e.printStackTrace();
		}
		return null;
	}
}
