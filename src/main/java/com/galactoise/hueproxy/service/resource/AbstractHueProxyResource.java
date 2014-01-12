package com.galactoise.hueproxy.service.resource;

import java.net.InetAddress;
import java.net.UnknownHostException;

import de.jaetzold.philips.hue.HueBridge;

public abstract class AbstractHueProxyResource {

	protected HueBridge bridge;
	protected String username;
	
	public AbstractHueProxyResource(){
		username = "19a42dc711b3063f1454afb728f30a2f";
		try {
			bridge = new HueBridge(InetAddress.getByName("192.168.1.252"),username);
			bridge.authenticate(false);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
