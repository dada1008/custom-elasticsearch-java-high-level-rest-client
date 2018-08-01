/**
 * 
 */
package com.opensource.dada.elasticsearch.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.opensource.dada.elasticsearch.request.DeleteByQueryRequest;
import com.opensource.dada.elasticsearch.response.DeleteByQueryResponse;

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
		DeleteByQueryResponse response = client.deleteByQuery(dbqRequest);
		assertNotNull(response);
		assertEquals(200, response.getResponseCode(),"Delete by query request failed");
		System.out.println("DeleteByQueryResponse: "+response);
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
