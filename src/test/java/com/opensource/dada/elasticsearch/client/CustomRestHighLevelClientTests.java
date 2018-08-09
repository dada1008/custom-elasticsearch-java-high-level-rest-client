/**
 * 
 */
package com.opensource.dada.elasticsearch.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.opensource.dada.elasticsearch.request.ClusterHealthRequest;
import com.opensource.dada.elasticsearch.request.DeleteByQueryRequest;
import com.opensource.dada.elasticsearch.request.GetAliasesRequest;
import com.opensource.dada.elasticsearch.request.GetNodesInfoRequest;
import com.opensource.dada.elasticsearch.response.ClusterHealthResponse;
import com.opensource.dada.elasticsearch.response.DeleteByQueryResponse;
import com.opensource.dada.elasticsearch.response.GetAliasesResponse;
import com.opensource.dada.elasticsearch.response.GetNodesInfoResponse;

/**
 * @author dadasaheb patil
 *
 */
@RunWith(JUnitPlatform.class)
public class CustomRestHighLevelClientTests {
	
	static CustomRestHighLevelClient client;
	
	@BeforeAll
	public static void setUp() {
		
		HttpHost esHost = new HttpHost("localhost",9200 , "http");
		RestClientBuilder restClientBuilder = RestClient.builder(esHost);
		client = new CustomRestHighLevelClient(restClientBuilder);
	}

	@Test
	public void testDeleteByQuery() {
		DeleteByQueryRequest dbqRequest = new DeleteByQueryRequest();
		
		String indexName = "abc";
		String typeName = "account";
		dbqRequest.addIndex(indexName);
		dbqRequest.addType(typeName);
		
		MatchAllQueryBuilder matchAll = new MatchAllQueryBuilder();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(matchAll);
		dbqRequest.setRequestPayload(searchSourceBuilder);
		DeleteByQueryResponse response = null;
		try {
			response = client.deleteByQuery(dbqRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("DeleteByQueryResponse: "+response);
		assertNotNull(response);
		assertEquals(200, response.getResponseCode(),"Delete by query request failed");
		assertNotNull(response.getResponseJson());
	}
	
	@Test
	public void testGetAliases() {
		GetAliasesRequest gaRequest = new GetAliasesRequest();
		
		String aliasName = "alias-2";
		gaRequest.addAlias(aliasName);
		
		GetAliasesResponse response = null;
		try {
			response = client.getAliases(gaRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("GetAliasesResponse: "+response);
		assertNotNull(response);
		assertEquals(200, response.getResponseCode(),"Get aliases request failed");
		assertNotNull(response.getResponseJson());
	}
	
	@Test
	public void testGetNodesInfo() {
		GetNodesInfoRequest gniRequest = new GetNodesInfoRequest();
		
		GetNodesInfoResponse response = null;
		try {
			response = client.getNodesInfos(gniRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("GetNodesInfoResponse: "+response);
		assertNotNull(response);
		assertEquals(200, response.getResponseCode(),"Get nodes request failed");
		assertNotNull(response.getResponseJson());
		assertNotNull(response.getNodes());
		assertFalse(response.getNodes().isEmpty());
	}
	
	@Test
	public void testGetClusterHealth() {
		ClusterHealthRequest chRequest = new ClusterHealthRequest();
		chRequest.waitForGreenStatus().timeout(TimeValue.timeValueMillis(1000));
		ClusterHealthResponse response = null;
		try {
			response = client.getClusterHealth(chRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ClusterHealthResponse: "+response);
		assertNotNull(response);
		assertEquals(200, response.getResponseCode(),"Get cluster health request failed");
		assertNotNull(response.getResponseJson());
		assertNotNull(response.getStatus());
		assertTrue(response.getActiveShards()>0);
	}
	
	@AfterAll
	public static void tearDown() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception while closing:"+e);
		}
	}
}
