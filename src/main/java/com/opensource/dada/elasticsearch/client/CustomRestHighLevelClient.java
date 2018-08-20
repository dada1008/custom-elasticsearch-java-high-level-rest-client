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
import org.elasticsearch.common.xcontent.NamedXContentRegistry.Entry;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentType;

import com.opensource.dada.elasticsearch.common.JsonParserUtils;
import com.opensource.dada.elasticsearch.request.ClusterHealthRequest;
import com.opensource.dada.elasticsearch.request.CreateSnapshotRequest;
import com.opensource.dada.elasticsearch.request.DeleteByQueryRequest;
import com.opensource.dada.elasticsearch.request.DeleteRepositoryRequest;
import com.opensource.dada.elasticsearch.request.DeleteSnapshotRequest;
import com.opensource.dada.elasticsearch.request.GetAliasesRequest;
import com.opensource.dada.elasticsearch.request.GetMappingsRequest;
import com.opensource.dada.elasticsearch.request.GetNodesInfoRequest;
import com.opensource.dada.elasticsearch.request.GetRepositoriesRequest;
import com.opensource.dada.elasticsearch.request.GetSnapshotsRequest;
import com.opensource.dada.elasticsearch.request.PutRepositoryRequest;
import com.opensource.dada.elasticsearch.response.AbstractResponse;
import com.opensource.dada.elasticsearch.response.ClusterHealthResponse;
import com.opensource.dada.elasticsearch.response.CreateSnapshotResponse;
import com.opensource.dada.elasticsearch.response.DeleteByQueryResponse;
import com.opensource.dada.elasticsearch.response.DeleteRepositoryResponse;
import com.opensource.dada.elasticsearch.response.DeleteSnapshotResponse;
import com.opensource.dada.elasticsearch.response.GetAliasesResponse;
import com.opensource.dada.elasticsearch.response.GetMappingsResponse;
import com.opensource.dada.elasticsearch.response.GetNodesInfoResponse;
import com.opensource.dada.elasticsearch.response.GetRepositoriesResponse;
import com.opensource.dada.elasticsearch.response.GetSnapshotsResponse;
import com.opensource.dada.elasticsearch.response.PutRepositoryResponse;

/**
 * @author dadasaheb patil
 *
 */
public class CustomRestHighLevelClient extends RestHighLevelClient {

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
		if (payloadObject != null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}

		Response response = null;

		// try {
		Map<String, String> params = new HashMap<>();
		response = this.getLowLevelClient().performRequest(dbqRequest.getRequestMethod(), dbqRequest.getRequestURI(),
				params, entity);
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			// Map<String, Object> map =
			// XContentHelper.convertToMap(XContentType.JSON.xContent(), responseString,
			// true);
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, DeleteByQueryResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		/*
		 * } catch (ResponseException e) { e.printStackTrace(); response =
		 * e.getResponse(); if(response!=null) { updateResponse(response,
		 * responseObject); } else { responseObject.setError(e.getMessage()); } } catch
		 * (IOException e) { e.printStackTrace(); if(response!=null) {
		 * updateResponse(response, responseObject); } else {
		 * responseObject.setError(e.getMessage()); } }
		 */

		return responseObject;
	}

	private void updateResponse(Response response, AbstractResponse responseObject) {
		StatusLine statusLine = response.getStatusLine();
		int responseCode = statusLine.getStatusCode();
		String error = statusLine.getReasonPhrase();
		responseObject.setResponseCode(responseCode);
		if (!responseObject.isRequestSuccessful()) {
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
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, GetAliasesResponse.class);
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
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, GetNodesInfoResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}

	public ClusterHealthResponse getClusterHealth(ClusterHealthRequest chRequest) throws IOException {
		ClusterHealthResponse responseObject = new ClusterHealthResponse();

		HttpEntity entity = null;
		Object payloadObject = chRequest.getRequestPayload();
		if (payloadObject != null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}

		Response response = null;

		Map<String, String> params = new HashMap<>();
		try {
			response = this.getLowLevelClient().performRequest(chRequest.getRequestMethod(), chRequest.getRequestURI(),
					params, entity);
		} catch (ResponseException e) {
			e.printStackTrace();
			response = e.getResponse();
			if (response == null) {
				responseObject.setError(e.getMessage());
			}
		}
		
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, ClusterHealthResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}
	
	public GetMappingsResponse getMappings(GetMappingsRequest gmRequest) throws IOException {
		GetMappingsResponse responseObject = new GetMappingsResponse();

		HttpEntity entity = null;

		Response response = null;

		Map<String, String> params = new HashMap<>();
		response = this.getLowLevelClient().performRequest(gmRequest.getRequestMethod(), gmRequest.getRequestURI(),
				params, entity);
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, GetMappingsResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}
	
	//This method created temparary until we do json parsing changes for GetMappingsResponse for method getMappings
	public boolean isMappingExists(GetMappingsRequest gmRequest) throws IOException {
		boolean isMappingExists = false;

		Response response = null;
		try {
			String indexName = gmRequest.getJoinedIndices();
			String mappingType = gmRequest.getJoinedTypes();
			response = this.getLowLevelClient().performRequest(gmRequest.getRequestMethod(), indexName + "/_mapping/" + mappingType);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try (InputStream is = response.getEntity().getContent()) {
			Map<String, Object> map = XContentHelper.convertToMap(XContentType.JSON.xContent(), is, true);
			isMappingExists = !map.containsKey("error");
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
		
		return isMappingExists;
	}
	
	public PutRepositoryResponse createRepository(PutRepositoryRequest prRequest) throws IOException {
		PutRepositoryResponse responseObject = new PutRepositoryResponse();

		HttpEntity entity = null;
		Object payloadObject = prRequest.getRequestPayload();
		if (payloadObject != null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}

		Response response = null;

		Map<String, String> params = new HashMap<>();
		try {
			response = this.getLowLevelClient().performRequest(prRequest.getRequestMethod(), prRequest.getRequestURI(),
					params, entity);
		} catch (ResponseException e) {
			e.printStackTrace();
			response = e.getResponse();
			if (response == null) {
				responseObject.setError(e.getMessage());
			}
		}
		
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, PutRepositoryResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}
	
	public GetRepositoriesResponse getRepository(GetRepositoriesRequest grRequest) throws IOException {
		GetRepositoriesResponse responseObject = new GetRepositoriesResponse();

		HttpEntity entity = null;
		Object payloadObject = grRequest.getRequestPayload();
		if (payloadObject != null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}

		Response response = null;

		Map<String, String> params = new HashMap<>();
		try {
			response = this.getLowLevelClient().performRequest(grRequest.getRequestMethod(), grRequest.getRequestURI(),
					params, entity);
		} catch (ResponseException e) {
			e.printStackTrace();
			response = e.getResponse();
			if (response == null) {
				responseObject.setError(e.getMessage());
			}
		}
		
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, GetRepositoriesResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}
	
	public GetSnapshotsResponse getSnapshots(GetSnapshotsRequest grRequest) throws IOException {
		GetSnapshotsResponse responseObject = new GetSnapshotsResponse();

		HttpEntity entity = null;
		Object payloadObject = grRequest.getRequestPayload();
		if (payloadObject != null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}

		Response response = null;

		Map<String, String> params = new HashMap<>();
		try {
			response = this.getLowLevelClient().performRequest(grRequest.getRequestMethod(), grRequest.getRequestURI(),
					params, entity);
		} catch (ResponseException e) {
			e.printStackTrace();
			response = e.getResponse();
			if (response == null) {
				responseObject.setError(e.getMessage());
			}
		}
		
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, GetSnapshotsResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}
	
	public CreateSnapshotResponse createSnapshot(CreateSnapshotRequest grRequest) throws IOException {
		CreateSnapshotResponse responseObject = new CreateSnapshotResponse();

		HttpEntity entity = null;
		Object payloadObject = grRequest.getRequestPayload();
		if (payloadObject != null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}

		Response response = null;

		Map<String, String> params = new HashMap<>();
		try {
			response = this.getLowLevelClient().performRequest(grRequest.getRequestMethod(), grRequest.getRequestURI(),
					params, entity);
		} catch (ResponseException e) {
			e.printStackTrace();
			response = e.getResponse();
			if (response == null) {
				responseObject.setError(e.getMessage());
			}
		}
		
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, CreateSnapshotResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}
	
	public DeleteSnapshotResponse deleteSnapshot(DeleteSnapshotRequest grRequest) throws IOException {
		DeleteSnapshotResponse responseObject = new DeleteSnapshotResponse();

		HttpEntity entity = null;
		Object payloadObject = grRequest.getRequestPayload();
		if (payloadObject != null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}

		Response response = null;

		Map<String, String> params = new HashMap<>();
		try {
			response = this.getLowLevelClient().performRequest(grRequest.getRequestMethod(), grRequest.getRequestURI(),
					params, entity);
		} catch (ResponseException e) {
			e.printStackTrace();
			response = e.getResponse();
			if (response == null) {
				responseObject.setError(e.getMessage());
			}
		}
		
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, DeleteSnapshotResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}
	
	public DeleteRepositoryResponse deleteRepository(DeleteRepositoryRequest grRequest) throws IOException {
		DeleteRepositoryResponse responseObject = new DeleteRepositoryResponse();

		HttpEntity entity = null;
		Object payloadObject = grRequest.getRequestPayload();
		if (payloadObject != null) {
			String payload = payloadObject.toString();
			entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
		}

		Response response = null;

		Map<String, String> params = new HashMap<>();
		try {
			response = this.getLowLevelClient().performRequest(grRequest.getRequestMethod(), grRequest.getRequestURI(),
					params, entity);
		} catch (ResponseException e) {
			e.printStackTrace();
			response = e.getResponse();
			if (response == null) {
				responseObject.setError(e.getMessage());
			}
		}
		
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null) {
			responseObject = JsonParserUtils.objectMapper.readValue(responseString, DeleteRepositoryResponse.class);
			responseObject.setResponseJson(responseString);
		}

		updateResponse(response, responseObject);

		return responseObject;
	}

}
