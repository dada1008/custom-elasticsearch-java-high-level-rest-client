/**
 * 
 */
package com.opensource.dada.elasticsearch.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.CheckedConsumer;
import org.elasticsearch.common.xcontent.NamedXContentRegistry.Entry;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.opensource.dada.elasticsearch.request.DeleteByQueryRequest;
import com.opensource.dada.elasticsearch.request.GetAliasesRequest;
import com.opensource.dada.elasticsearch.request.GetNodesInfoRequest;
import com.opensource.dada.elasticsearch.response.AbstractResponse;
import com.opensource.dada.elasticsearch.response.DeleteByQueryResponse;
import com.opensource.dada.elasticsearch.response.GetAliasesResponse;
import com.opensource.dada.elasticsearch.response.GetAliasesResponseParser;
import com.opensource.dada.elasticsearch.response.GetNodesInfoResponse;
import com.opensource.dada.elasticsearch.response.GetNodesInfoResponseParser;

/**
 * @author dadasaheb patil
 *
 */
public class CustomRestHighLevelClient extends RestHighLevelClient {

	public static ObjectMapper objectMapper = new ObjectMapper();
	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		SimpleModule module = new SimpleModule();
		module.addDeserializer(GetAliasesResponse.class, new GetAliasesResponseParser());
		module.addDeserializer(GetNodesInfoResponse.class, new GetNodesInfoResponseParser());
		objectMapper.registerModule(module);
	}
	/**
	 * @param restClientBuilder
	 */
	public CustomRestHighLevelClient(RestClientBuilder restClientBuilder) {
		super(restClientBuilder);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param restClientBuilder
	 * @param namedXContentEntries
	 */
	public CustomRestHighLevelClient(RestClientBuilder restClientBuilder, List<Entry> namedXContentEntries) {
		super(restClientBuilder, namedXContentEntries);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param restClient
	 * @param doClose
	 * @param namedXContentEntries
	 */
	public CustomRestHighLevelClient(RestClient restClient, CheckedConsumer<RestClient, IOException> doClose,
			List<Entry> namedXContentEntries) {
		super(restClient, doClose, namedXContentEntries);
		// TODO Auto-generated constructor stub
	}
	
	public DeleteByQueryResponse deleteByQuery(DeleteByQueryRequest dbqRequest) throws IOException {
		DeleteByQueryResponse responseObject = new DeleteByQueryResponse();
		
		HttpEntity entity = null;
		Object payloadObject = dbqRequest.getRequestPayload();
		if(payloadObject!=null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}
		
		Response response = null;
		
		//try {
			Map<String,String> params = new HashMap<>();
			response = this.getLowLevelClient().performRequest(dbqRequest.getRequestMethod(), dbqRequest.getRequestURI(), params,entity);
			String responseString = EntityUtils.toString(response.getEntity());
			if(responseString!=null) {
				//Map<String, Object> map = XContentHelper.convertToMap(XContentType.JSON.xContent(), responseString, true);
				responseObject = objectMapper.readValue(responseString, DeleteByQueryResponse.class);
				responseObject.setResponseJson(responseString);
			}
			
			updateResponse(response, responseObject);
			
		/*} catch (ResponseException e) {
			e.printStackTrace();
			response = e.getResponse();
			if(response!=null) {
				updateResponse(response, responseObject);
			} else {
				responseObject.setError(e.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(response!=null) {
				updateResponse(response, responseObject);
			} else {
				responseObject.setError(e.getMessage());
			}
		}*/
		
		return responseObject;
	}

	private void updateResponse(Response response, AbstractResponse responseObject) {
		StatusLine statusLine = response.getStatusLine();
		int responseCode = statusLine.getStatusCode();
		String error = statusLine.getReasonPhrase();
		responseObject.setResponseCode(responseCode);
		if(!responseObject.isRequestSuccessful()) {
			responseObject.setError(error);
		}
	}
	
	public GetAliasesResponse getAliases(GetAliasesRequest gaRequest) throws IOException {
		GetAliasesResponse responseObject = new GetAliasesResponse();

		HttpEntity entity = null;
		Object payloadObject = gaRequest.getRequestPayload();
		if (payloadObject != null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}

		Response response = null;

		Map<String, String> params = new HashMap<>();
		response = this.getLowLevelClient().performRequest(gaRequest.getRequestMethod(), gaRequest.getRequestURI(),
				params, entity);
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			responseObject = objectMapper.readValue(responseString, GetAliasesResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}
	
	public GetNodesInfoResponse getNodesInfos(GetNodesInfoRequest gniRequest) throws IOException {
		GetNodesInfoResponse responseObject = new GetNodesInfoResponse();

		HttpEntity entity = null;
		Object payloadObject = gniRequest.getRequestPayload();
		if (payloadObject != null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}

		Response response = null;

		Map<String, String> params = new HashMap<>();
		response = this.getLowLevelClient().performRequest(gniRequest.getRequestMethod(), gniRequest.getRequestURI(),
				params, entity);
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			responseObject = objectMapper.readValue(responseString, GetNodesInfoResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}
	
}
