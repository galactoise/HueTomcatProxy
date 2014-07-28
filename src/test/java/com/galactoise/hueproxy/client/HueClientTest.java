package com.galactoise.hueproxy.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.galactoise.hueproxy.client.model.Light;

public class HueClientTest {

	HueClient client;
	
	@Before
	public void setup(){
		client = new HueClient();
		client.setUsername("19a42dc711b3063f1454afb728f30a2f");
		client.setAddress("http://192.168.1.252");
	}
	
	@Test
	@Ignore
	public void testGetLightById() throws ClientProtocolException, IOException{
		Light light = client.getLightById("1");
		assertNotNull(light);
		assertNotNull(light.getState());
		assertNotNull(light.getName());
	}
	
	@Test
	@Ignore
	public void testGetLights() throws ClientProtocolException, IOException{
		HashMap<String, Light> lights = client.getLights();
		assertNotNull(lights);
		assertNotNull(lights.get("1"));
		assertNotNull(lights.get("1").getName());
	}
	
	@Test
	@Ignore
	public void testUpdateLightById() throws ClientProtocolException, IOException{
		Light light = client.getLightById("1");
		String lightName = light.getName();
		if(lightName.equalsIgnoreCase("lamp1")){
			light.setName("lamp1.1");
		}else{
			light.setName("lamp1");
		}
		light.getState().setOn(!light.getState().getOn());
		
		client.updateLightById("1", light);
		light.getState().setOn(!light.getState().getOn());
		
		Light light2 = client.getLightById("1");
		assertFalse(!light.getName().equalsIgnoreCase(light2.getName()));
		assertFalse(light.getState().getOn() == light2.getState().getOn());		
	}
}
