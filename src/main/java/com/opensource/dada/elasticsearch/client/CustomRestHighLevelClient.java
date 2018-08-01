/**
 * 
 */
package com.opensource.dada.elasticsearch.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.CheckedConsumer;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.NamedXContentRegistry.Entry;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensource.dada.elasticsearch.request.DeleteByQueryRequest;
import com.opensource.dada.elasticsearch.response.AbstractResponse;
import com.opensource.dada.elasticsearch.response.DeleteByQueryResponse;

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
	
	public DeleteByQueryResponse deleteByQuery(DeleteByQueryRequest dbqRequest) {
		DeleteByQueryResponse responseObject = new DeleteByQueryResponse();
		
		String payload = null;
		Object payloadObject = dbqRequest.getRequestPayload();
		if(payloadObject!=null) {
			payload = payloadObject.toString();
		}
		
		HttpEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		Response response = null;
		
		try {
			Map<String,String> params = new HashMap<>();
			response = this.getLowLevelClient().performRequest(dbqRequest.getRequestMethod(), dbqRequest.getRequestURI(), params,entity);
			String responseString = EntityUtils.toString(response.getEntity());
			if(responseString!=null) {
				//Map<String, Object> map = XContentHelper.convertToMap(XContentType.JSON.xContent(), responseString, true);
				responseObject = objectMapper.readValue(responseString, DeleteByQueryResponse.class);
				responseObject.setResponseString(responseString);
			}
			
			updateResponse(response, responseObject);
			
		} catch (ResponseException e) {
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
		}
		
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
}
