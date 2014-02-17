package com.galactoise.hueproxy.service.resource;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;

import com.galactoise.hueproxy.model.LightsTemplate;
import com.galactoise.hueproxy.model.StateUpdateWrapper;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;
import nl.q42.jue.StateUpdate;
import nl.q42.jue.exceptions.ApiException;

@Path("/templates/")
@Produces("application/json")
public class TemplatesResource extends AbstractHueProxyResource{


	protected MemcachedClient memcachedClient;
	protected ObjectMapper mapper; 
	protected ObjectWriter writer;
	
	public TemplatesResource(){
		super();
		try {
			memcachedClient = new MemcachedClient(new ConnectionFactoryBuilder().setDaemon(true).setFailureMode(FailureMode.Retry).build(), AddrUtil.getAddresses("192.168.1.253:11211"));
			
		} catch (IOException e) {
			LOGGER.severe("Couldn't initialize memcached client.");
			e.printStackTrace();
		}
		try {
			mapper = new ObjectMapper();
		} catch (Exception e) {
			LOGGER.severe("Couldn't initialize jackson objects.");
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/{key}")
	public void setTemplate(@PathParam("key") String key, LightsTemplate template){
		String templateString;
		try {
			templateString = mapper.writeValueAsString(template);
		} catch (IOException e1) {
			LOGGER.severe("Could not serialize template");
			LOGGER.severe(e1.getMessage());
			memcachedClient.shutdown();
			return;
		}
		LOGGER.severe("Key: " + key);
		LOGGER.severe("Template: " + templateString);
		try{
			Future<Boolean> isSet = memcachedClient.set(key, 600, templateString);
			Boolean b = isSet.get(5, TimeUnit.SECONDS);
			LOGGER.severe("Set: " + b.toString());
		}catch(Exception e){
			LOGGER.severe("Memcached operation failed: " + e.getMessage());
		}finally{
			memcachedClient.shutdown();
		}
	}
	
	@GET
	@Path("/{key}")
	public Object getTemplate(@PathParam("key") String key){
		LOGGER.severe("Key to retrieve: " + key);
		Object response = "Not set";
		try{
			response = memcachedClient.get(key);
		}catch(Exception e){
			LOGGER.severe("Memcached operation failed: " + e.getMessage());
		}finally{
			memcachedClient.shutdown();
		}
		return response;
	}
	
	@PUT
	@Path("/{key}/run")
	public void runTemplate(@PathParam("key") String key){
		LOGGER.severe("Key to retrieve and run: " + key);
		String lightsTemplate;
		try{
			lightsTemplate = (String) memcachedClient.get(key);
		}catch(Exception e){
			LOGGER.severe("Memcached operation failed: " + e.getMessage());
			return;
		}finally{
			memcachedClient.shutdown();
		}
		LightsTemplate template;
		try {
			template = mapper.readValue(lightsTemplate, new TypeReference<LightsTemplate>(){});
		} catch (IOException e) {
			LOGGER.severe("Could not deserialize retrieved template into object.");
			LOGGER.severe(e.getMessage());
			return;
		}
		runTemplate(template);
	}
	
	@PUT
	@Path("/test")
	public void testTemplate(LightsTemplate template){
		runTemplate(template);
	}

	private void runTemplate(LightsTemplate template) {
		int numLoops = 1;
		//Don't allow infinite loops, for now
		if(template.getNumLoops() == null || template.getNumLoops() == 0){
			numLoops = 1;
		}else{
			numLoops = template.getNumLoops();
		}
		for(int i = 0; i < numLoops; i++){
			StateUpdateWrapper[] states = template.getStates();
			if(states == null || states.length < 1){
				LOGGER.severe("No states to update.");
				return;
			}
			for(StateUpdateWrapper state : states){
				if(state.getType() == null){
					LOGGER.severe("No type defined for state.");
					continue;
				}
				StateUpdate stateUpdate = state.getStateUpdate();
				if(stateUpdate == null){
					LOGGER.severe("No update defined for state.");
				}
				try{
					switch(state.getType()){
					case LIGHT:
						bridge.setLightStateById(String.valueOf(state.getTarget()), state.getStateUpdate());
						break;
					default: break;
					}
				}catch (IOException | ApiException e){
					LOGGER.severe("Could not update light due to exception.");
					LOGGER.severe(e.getMessage());
				}
				Integer transitionTime = stateUpdate.getTransitionTime();
				Integer postTransitionDuration = state.getPostTransitionDuration();
				try{
					if(transitionTime != null && transitionTime > 0){
						Thread.sleep(transitionTime);
					}
					if(postTransitionDuration != null && postTransitionDuration > 0){
						Thread.sleep(postTransitionDuration);
					}
				} catch (InterruptedException e) {
					LOGGER.severe("Thread sleep interrupted.");
				}
			}
		}
	}
}
