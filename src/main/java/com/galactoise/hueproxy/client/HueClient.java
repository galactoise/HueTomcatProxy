package com.galactoise.hueproxy.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

import com.galactoise.hueproxy.client.model.HueUpdateResponseObject;
import com.galactoise.hueproxy.client.model.Light;
import com.galactoise.hueproxy.client.model.PointSymbolSelectionObject;

public class HueClient {

	protected HttpClient client;
	protected ObjectMapper mapper;
	protected String address;
	protected String username;
	protected SimpleFilterProvider filters; 
	
	public static final String ROOT_PATH = "/api";

	public static final String LIGHTS_PATH = "/lights";
	public static final String LIGHT_BY_ID_PATH = "/lights/%s";
	public static final String LIGHT_STATE_BY_ID_PATH = "/lights/%s/state";
	public static final String LIGHT_POINTSYMBOL_BY_ID_PATH = "/lights/%s/pointsymbol";
	public static final String TRANSMIT_POINTSYMBOL_BY_GROUP_ID_PATH = "/groups/%s/transmitsymbol";
	
	public static final String[] UPDATE_LIGHT_FILTER_SET = new String[]{"name"};
	public static final String[] UPDATE_LIGHT_STATE_FILTER_SET = new String[]{"on","bri","hue","sat","xy","ct","alert","colormode","effect","transitiontime"};
	
	public HueClient(){
		client = HttpClients.createDefault();
		mapper = new ObjectMapper();
		HttpGet get = new HttpGet();
		addDefaultHeadersToMessage(get);		
		filters = new SimpleFilterProvider();
		filters.setFailOnUnknownId(false);
		filters.addFilter("Light", SimpleBeanPropertyFilter.filterOutAllExcept((Set<String>)null));
		filters.addFilter("LightState", SimpleBeanPropertyFilter.filterOutAllExcept((Set<String>)null));
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
		return mapper.writer(filters).writeValueAsString(objectToSerialize);
	}
	
	public void setFilterByName(SimpleFilterProvider filterProvider, String filterName, Set<String> allowedFilterSet){
		filterProvider.removeFilter(filterName);
		filterProvider.addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept(allowedFilterSet));
	}
	
	/*public void setFilterByName(SimpleFilterProvider filterProvider, String filterName, Set<String> allowedFilterSet, Set<String> ignoredFilterSet){
		filterProvider.removeFilter(filterName);
		filterProvider.addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept(allowedFilterSet));
	}*/
	
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
	
	protected <T> T put(String url, TypeReference<T> responseTypeReference, Object requestBodyObject) throws ClientProtocolException, IOException{
		String serializedString = serialize(requestBodyObject);
		System.out.println(serializedString);
		StringEntity entity = new StringEntity(serializedString);
		HttpPut put = new HttpPut(url);
		put.setEntity(entity);
		addDefaultHeadersToMessage(put);
		
		HttpResponse response = client.execute(put);
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
	
	public HueUpdateResponseObject[] updateLightById(String id, Light light) throws ClientProtocolException, IOException{
		
		setFilterByName(filters, "Light", new HashSet<String>(Arrays.asList(UPDATE_LIGHT_FILTER_SET)));
		setFilterByName(filters, "LightState", new HashSet<String>(Arrays.asList(UPDATE_LIGHT_STATE_FILTER_SET)));
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(getApiRoot());
		urlBuilder.append(String.format(LIGHT_BY_ID_PATH, id));
		HueUpdateResponseObject[] lightUpdateResponse = put(urlBuilder.toString(), new TypeReference<HueUpdateResponseObject[]>(){},light);
		
		StringBuilder urlBuilder2 = new StringBuilder();
		urlBuilder2.append(getApiRoot());
		urlBuilder2.append(String.format(LIGHT_STATE_BY_ID_PATH, id));
		//HueUpdateResponseObject[] lightStateUpdateResponse = put(urlBuilder2.toString(), new TypeReference<HueUpdateResponseObject[]>(){},light.getState());
		put(urlBuilder2.toString(), new TypeReference<HueUpdateResponseObject[]>(){},light.getState());

		filters.removeFilter("Light");
		filters.removeFilter("LightState");
		
		List<HueUpdateResponseObject> responseList = Arrays.asList(lightUpdateResponse);
		//responseList.addAll(Arrays.asList(lightStateUpdateResponse));
		return responseList.toArray(new HueUpdateResponseObject[responseList.size()]);
	}
	
	public void updatePointSymbolsByLightId(String lightId, HashMap<String,String> pointSymbols) throws ClientProtocolException, IOException{
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(getApiRoot());
		urlBuilder.append(String.format(LIGHT_POINTSYMBOL_BY_ID_PATH, lightId));
//		HueUpdateResponseObject[] lightUpdateResponse = put(urlBuilder.toString(), new TypeReference<HueUpdateResponseObject[]>(){},pointSymbols);
		put(urlBuilder.toString(), new TypeReference<HueUpdateResponseObject[]>(){},pointSymbols);
	}
	
	public void transmitPointSymbolByGroupId(String groupId, PointSymbolSelectionObject psso) throws ClientProtocolException, IOException{
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(getApiRoot());
		urlBuilder.append(String.format(TRANSMIT_POINTSYMBOL_BY_GROUP_ID_PATH, groupId));
//		HueUpdateResponseObject[] lightUpdateResponse = put(urlBuilder.toString(), new TypeReference<HueUpdateResponseObject[]>(){},psso);
		put(urlBuilder.toString(), new TypeReference<HueUpdateResponseObject[]>(){},psso);
	}
	
	
}
