package com.galactoise.hueproxy.service.resource;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.q42.jue.FullConfig;
import nl.q42.jue.exceptions.ApiException;


@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
public class ConfigResource extends AbstractHueProxyResource {


	@GET
	public FullConfig getFullConfig(){
		try {
			return bridge.getFullConfig();
		} catch (IOException | ApiException e) {
			LOGGER.severe("Could not retrieve full config.");
		}
		return null;
	}
}
