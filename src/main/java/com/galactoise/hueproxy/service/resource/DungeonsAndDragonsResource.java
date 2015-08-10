package com.galactoise.hueproxy.service.resource;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/dungeons/")
@Produces(MediaType.TEXT_PLAIN)
public class DungeonsAndDragonsResource extends AbstractHueProxyResource {
	
	@GET
	@Path("/frostDragonRoom")
	public Object surgeLights(){
	/*	try{
			for(int i = 1; i <= 3; i++){
				StateUpdate update = new StateUpdate();
				update.setHue(44048);
				update.setBrightness(180);
				bridge.setLightStateById(String.valueOf(i), update);
			}
		} catch (IOException | ApiException e) {
			e.printStackTrace();
		}*/

		return null;
	}
	
	@GET
	@Path("/candleLight")
	public Object orangeCandleLight(){
		/*try{
			for(int i = 1; i <= 3; i++){
				StateUpdate update = new StateUpdate();
				update.setHue(9607);
				update.setBrightness(127);
				bridge.setLightStateById(String.valueOf(i), update);
			}
		} catch (IOException | ApiException e) {
			e.printStackTrace();
		}*/
		return null;
	}
	
	@GET
	@Path("/lightingFlicker")
	public Object lightingFlicker(){
		/*try{
			FullLight light = bridge.getLightById("1");
			int brightness = light.getState().getBrightness();
			for(int i = 1; i <= 3; i++){			
				StateUpdate update = new StateUpdate();
				if(brightness > 255){
					update.setBrightness(255);
				}else{
					update.setBrightness(brightness + 64);
				}
				bridge.setLightStateById(String.valueOf(i), update);
			}
			for(int i = 1; i <= 3; i++){
				StateUpdate update = new StateUpdate();
				update.setBrightness(brightness);
				bridge.setLightStateById(String.valueOf(i), update);
			}
		} catch (IOException | ApiException e) {
			e.printStackTrace();
		}*/
		return null;
	}
	
	@GET
	@Path("/flickerAndWait")
	public Object flickerAndWait(@QueryParam("numFlickers") int numFlickers){
		for(int i = 0; i < numFlickers; i++){
			lightingFlicker();
			int extraPause = (int)(Math.random() * 5000);
			System.out.println(extraPause);
			try {
				Thread.sleep(5000 + extraPause);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
