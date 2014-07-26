package com.galactoise.hueproxy.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.galactoise.hueproxy.client.model.Light;

public class HueClient {

	protected HttpClient client;
	protected ObjectMapper mapper;
	protected String address;
	protected String username;
	
	public static final String ROOT_PATH = "/api";

	public static final String LIGHTS_PATH = "/lights";
	public static final String LIGHT_BY_ID_PATH = "/lights/%s";
	
	public HueClient(){
		client = HttpClients.createDefault();
		mapper = new ObjectMapper();
		HttpGet get = new HttpGet();
		addDefaultHeadersToMessage(get);
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public <T> T deserialize(String jsonString, TypeReference<T> typeReference) throws JsonParseException, JsonMappingException, IOException{
		if(jsonString == null){
			return null;
		}
		return mapper.readValue(jsonString, typeReference);
	}
	
	public String serialize(Object objectToSerialize) throws JsonGenerationException, JsonMappingException, IOException{
		if(objectToSerialize == null){
			return null;
		}
		return mapper.writeValueAsString(objectToSerialize);
	}
	
	public void addDefaultHeadersToMessage(HttpMessage message){
		message.addHeader(new BasicHeader("Content-Type","application/json"));
		message.addHeader(new BasicHeader("Accept","application/json"));
	}
	
	public String getApiRoot() throws UnsupportedEncodingException{
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(address);
		urlBuilder.append(ROOT_PATH);
		if(username != null){
			urlBuilder.append(URLEncoder.encode(username, "UTF-8"));
		}
		return urlBuilder.toString();
	}
	
	protected <T> T get(String url, TypeReference<T> responseTypeReference) throws ClientProtocolException, IOException{
		HttpGet get = new HttpGet(url);
		addDefaultHeadersToMessage(get);
		
		HttpResponse response = client.execute(get);
		String responseBody = EntityUtils.toString(response.getEntity());
		System.out.println(responseBody);
		return deserialize(responseBody, responseTypeReference);
	} 
	
	protected <T> T post(String url, TypeReference<T> responseTypeReference, Object requestBodyObject) throws ClientProtocolException, IOException{
		StringEntity entity = new StringEntity(serialize(requestBodyObject));
		HttpPost post = new HttpPost(url);
		post.setEntity(entity);
		addDefaultHeadersToMessage(post);
		
		HttpResponse response = client.execute(post);
		String responseBody = EntityUtils.toString(response.getEntity());
		System.out.println(responseBody);
		return deserialize(responseBody, responseTypeReference);
	} 

	public Light getLightById(String id) throws ClientProtocolException, IOException{
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(getApiRoot());
		urlBuilder.append(String.format(LIGHT_BY_ID_PATH, id));
		return get(urlBuilder.toString(), new TypeReference<Light>(){});
	}
	
	public HashMap<String, Light> getLights() throws ClientProtocolException, IOException{
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(getApiRoot());
		urlBuilder.append(LIGHTS_PATH);
		return get(urlBuilder.toString(), new TypeReference<HashMap<String,Light>>(){});
	}
	
	public Light updateLightById(String id, Light light) throws ClientProtocolException, IOException{
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(getApiRoot());
		urlBuilder.append(String.format(LIGHT_BY_ID_PATH, id));
		return post(urlBuilder.toString(), new TypeReference<Light>(){},light);
	}
	
}
