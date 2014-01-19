package com.galactoise.hueproxy.service.resource;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.q42.jue.HueBridge;
import nl.q42.jue.exceptions.ApiException;

public abstract class AbstractHueProxyResource {


	protected static final Logger LOGGER = Logger.getLogger(AbstractHueProxyResource.class.getName());
	
//	protected HueBridge bridge;
	protected String username;
	protected HueBridge bridge;
	
	public AbstractHueProxyResource(){
		username = "19a42dc711b3063f1454afb728f30a2f";
		
		try {
			bridge = new HueBridge("192.168.1.252",username);
		} catch (IOException | ApiException e) {
			LOGGER.log(Level.SEVERE, "Could not create new hue bridge.");
		}
				
//				HueJavaSDK implementation
/*		try {
			bridge = new HueBridge(InetAddress.getByName("192.168.1.252"),username);
			bridge.authenticate(false);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}*/
	}
}
