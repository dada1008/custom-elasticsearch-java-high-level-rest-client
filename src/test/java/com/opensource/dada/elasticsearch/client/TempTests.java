package com.opensource.dada.elasticsearch.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.opensource.dada.elasticsearch.response.AliasData;
import com.opensource.dada.elasticsearch.response.GetAliasesResponse;

@RunWith(JUnitPlatform.class)
public class TempTests {

	static String aliasResponse = "{" + "\"logstash-2015.05.19\": {" + "\"aliases\": { }" + "}," + "\"bank\": {"
			+ "\"aliases\": { }" + "}," + "\"companydatabase\": {" + "\"aliases\": { }" + "},"
			+ "\"logstash-2015.05.20\": {" + "\"aliases\": { }" + "}," + "\"logstash-2015.05.18\": {"
			+ "\"aliases\": { }" + "}," + "\"shakespeare\": {" + "\"aliases\": {" + "\"alias-1\": { },"
			+ "\"alias-2\": { }" + "}" + "}" + "}";
	static String aliasResponse2 = "{" + "\"discovery-index\": {" + "\"aliases\": {" + "\"1-discoveredTarget\": {"
			+ "\"filter\": {" + "\"term\": {" + "\"tenantId\": \"1\"" + "}" + "}," + "\"index_routing\": \"1\","
			+ "\"search_routing\": \"1\"" + "}" + "}" + "}," + "\"group-index\": {" + "\"aliases\": {"
			+ "\"1-elementManagerGroup\": {" + "\"filter\": {" + "\"term\": {" + "\"tenantId\": \"1\"" + "}" + "},"
			+ "\"index_routing\": \"1\"," + "\"search_routing\": \"1\"" + "}" + "}" + "},"
			+ "\"1-vulnerability-asset-index-unique\": {" + "\"aliases\": {" + "\"1-vulnerabilityAsset\": { }" + "}"
			+ "}," + "\"1-vulnerability-scanholder-index-unique\": {" + "\"aliases\": {"
			+ "\"1-vulnerabilityScanHolder\": { }" + "}" + "}," + "\"remediation-index\": {" + "\"aliases\": {"
			+ "\"1-vulnerabilityRemediationContent\": {" + "\"filter\": {" + "\"term\": {" + "\"tenantId\": \"1\"" + "}"
			+ "}," + "\"index_routing\": \"1\"," + "\"search_routing\": \"1\"" + "}" + "}" + "},"
			+ "\"1-vulnerability-management-index-unique\": {" + "\"aliases\": {" + "\"1-vulnerabilityMapping\": { }"
			+ "}" + "}," + "\"1-activity-index-unique\": {" + "\"aliases\": {" + "\"1-activity\": { }" + "}" + "},"
			+ "\"vm-index\": {" + "\"aliases\": {" + "\"1-elementManagerTarget\": {" + "\"filter\": {" + "\"term\": {"
			+ "\"tenantId\": \"1\"" + "}" + "}," + "\"index_routing\": \"1\"," + "\"search_routing\": \"1\"" + "},"
			+ "\"test-dada\": { }" + "}" + "}" + "}";

	@Disabled
	public void testJsonNodeIteration() {
		Map<String, Map> rootNode = null;
		try {
			rootNode = CustomRestHighLevelClient.objectMapper.readValue(aliasResponse, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (rootNode != null) {
			for (Entry<String, Map> entry : rootNode.entrySet()) {
				System.out.println("indexName:" + entry.getKey());
				System.out.println("value:" + entry.getValue());
				Map<String, Map> aliases = entry.getValue();
				for (Entry<String, Map> entryAlias : aliases.entrySet()) {
					System.out.println("indexName:" + entryAlias.getKey());
					System.out.println("value:" + entryAlias.getValue());

				}
			}
		}
	}
	
	@Disabled
	public void testGetAliasResponseParser() {
		GetAliasesResponse response = null;
		try {
			response = CustomRestHighLevelClient.objectMapper.readValue(aliasResponse2, GetAliasesResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(response);
		Map<String, Set<AliasData>> aliases = response.getAliases();
		assertTrue(aliases!=null && !aliases.isEmpty());
		Set<AliasData> aliasDataSet = aliases.get("vm-index");
		assertTrue(aliasDataSet!=null && !aliasDataSet.isEmpty());
	}
}
