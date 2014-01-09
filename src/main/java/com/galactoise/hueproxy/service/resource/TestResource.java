package com.galactoise.hueproxy.service.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/")
@Produces("application/json")
public class TestResource {

	@GET
	public String testMethod(){
		return "{}";
	}
}
