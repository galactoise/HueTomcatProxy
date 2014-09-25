package com.galactoise.hueproxy.service.resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

import com.galactoise.hueproxy.client.model.LightState;
import com.galactoise.hueproxy.model.LightsTemplateV2;
import com.galactoise.hueproxy.model.StateUpdateType;
import com.galactoise.hueproxy.model.StateUpdateWrapperV2;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;

@Path("/v2/templates/")
@Produces("application/json")
public class TemplatesResourceV2 extends AbstractHueProxyResource{


	protected MemcachedClient memcachedClient;
	protected ObjectMapper mapper; 
	protected ObjectWriter writer;
	
	public TemplatesResourceV2(){
		super();
		/*try {
			memcachedClient = new MemcachedClient(new ConnectionFactoryBuilder().setDaemon(true).setFailureMode(FailureMode.Retry).build(), AddrUtil.getAddresses("192.168.1.253:11211"));
			
		} catch (IOException e) {
			LOGGER.severe("Couldn't initialize memcached client.");
			e.printStackTrace();
		}*/
		try {

			SimpleFilterProvider filterProvider = new SimpleFilterProvider();
			filterProvider.setFailOnUnknownId(false);
			filterProvider.addFilter("Light", SimpleBeanPropertyFilter.serializeAllExcept(new HashSet<String>()));
			filterProvider.addFilter("LightState", SimpleBeanPropertyFilter.serializeAllExcept(new HashSet<String>()));
			mapper = new ObjectMapper();
			mapper.writer(filterProvider);
			mapper.setFilters(filterProvider);
		} catch (Exception e) {
			LOGGER.severe("Couldn't initialize jackson objects.");
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/run")
	public void testTemplate(LightsTemplateV2 template){
		runTemplate(template);
	}
	
	@PUT
	@Path("/{key}")
	public void setTemplate(@PathParam("key") String key, LightsTemplateV2 template){
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
	
	/*@PUT
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
	}*/
	
	private void runTemplate(LightsTemplateV2 template){
		LOGGER.severe("Running Template");
		int numLoops = (template.getNumLoops() == null || template.getNumLoops() < 1) ? 1 : template.getNumLoops();
		
		StateUpdateWrapperV2[] states =  template.getStates();
		for(int i = 0; i < numLoops; i++){
			if(template.getRandom()){
				LOGGER.severe("Random is not yet implemented.");
				continue;
			}else if(states == null || states.length < 1){
				LOGGER.severe("No states to execute...");
				continue;
			}
			for(StateUpdateWrapperV2 updateWrapper : states){
				StateUpdateType type = updateWrapper.getType();
				Integer target = updateWrapper.getTarget();
				if(type == null){
					LOGGER.severe("Type is a required field.  Please choose from 'LIGHT' or 'GROUP.'");
				}else if(target == null){
					LOGGER.severe("Target is a required field.  Please specify the light or group you want to update.");
				}else if(type == StateUpdateType.GROUP){
					LOGGER.severe("'GROUP' is not yet implemented.");
				}else{
					runStateUpdateOnLight(updateWrapper.getStateUpdate(), target);
				}
			}
		}
	}
	
	private void runStateUpdateOnLight(LightState update, Integer target){
		if(update == null){
			LOGGER.severe("Cannot update a light where no update object is provided.");
			return;
		}
		LOGGER.severe("Executing state update for 'LIGHT' on target: " + target);
		
		try {
			client.updateLightStateByLightId(String.valueOf(target), update);
		} catch (IOException e) {
			LOGGER.severe("Trying to update light state resulted in an exception");
			e.printStackTrace();
		}
	}

	/*private void runTemplate(LightsTemplate template) {
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
	}*/
}
