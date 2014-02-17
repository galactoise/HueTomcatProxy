package com.galactoise.hueproxy.service.resource;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.galactoise.hueproxy.model.LightsTemplate;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;

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
	
	@POST
	@Path("/{key}")
	public void setTemplate(@PathParam("key") String key, LightsTemplate template){
		String templateString;
		try {
			templateString = mapper.writeValueAsString(template);
		} catch (IOException e1) {
			LOGGER.severe("Could not serialize template");
			memcachedClient.shutdown();
			return;
		}
		LOGGER.severe("Key: " + key);
		LOGGER.severe("Template: " + templateString);
		try{
			memcachedClient.set(key, 600, templateString);
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
}
