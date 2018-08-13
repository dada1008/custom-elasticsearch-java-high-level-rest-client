package com.opensource.dada.elasticsearch.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static com.opensource.dada.elasticsearch.common.JsonParserUtils.objectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elasticsearch.cluster.metadata.RepositoryMetaData;
import org.elasticsearch.common.settings.Settings;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.opensource.dada.elasticsearch.request.PutRepositoryRequest;
import com.opensource.dada.elasticsearch.response.AliasData;
import com.opensource.dada.elasticsearch.response.GetAliasesResponse;
import com.opensource.dada.elasticsearch.response.GetNodesInfoResponse;
import com.opensource.dada.elasticsearch.response.GetRepositoriesResponse;
import com.opensource.dada.elasticsearch.response.NodesInfo;

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

	static String nodesResponse = 
			"{" + 
			"  \"_nodes\": {" + 
			"    \"total\": 1," + 
			"    \"successful\": 1," + 
			"    \"failed\": 0" + 
			"  }," + 
			"  \"cluster_name\": \"dca-index\"," + 
			"  \"nodes\": {" + 
			"    \"TD7A7g92R8usvjrbGLM7Lg\": {" + 
			"      \"name\": \"TD7A7g9\"," + 
			"      \"transport_address\": \"127.0.0.1:9300\"," + 
			"      \"host\": \"127.0.0.1\"," + 
			"      \"ip\": \"127.0.0.1\"," + 
			"      \"version\": \"6.2.4\"," + 
			"      \"build_hash\": \"ccec39f\"," + 
			"      \"total_indexing_buffer\": 103795916," + 
			"      \"roles\": [" + 
			"        \"master\"," + 
			"        \"data\"," + 
			"        \"ingest\"" + 
			"      ]," + 
			"      \"settings\": {" + 
			"        \"cluster\": {" + 
			"          \"name\": \"dca-index\"" + 
			"        }," + 
			"        \"node\": {" + 
			"          \"name\": \"TD7A7g9\"" + 
			"        }," + 
			"        \"path\": {" + 
			"          \"logs\": \"C:\\\\P4V\\\\ES\\\\elasticsearch-6.2.4\\\\logs\"," + 
			"          \"home\": \"C:\\\\P4V\\\\ES\\\\elasticsearch-6.2.4\"" + 
			"        }," + 
			"        \"transport\": {" + 
			"          \"type\": {" + 
			"            \"default\": \"netty4\"" + 
			"          }" + 
			"        }" + 
			"      }," + 
			"      \"os\": {" + 
			"        \"refresh_interval_in_millis\": 1000," + 
			"        \"name\": \"Windows 10\"," + 
			"        \"arch\": \"amd64\"," + 
			"        \"version\": \"10.0\"," + 
			"        \"available_processors\": 8," + 
			"        \"allocated_processors\": 8" + 
			"      }," + 
			"      \"process\": {" + 
			"        \"refresh_interval_in_millis\": 1000," + 
			"        \"id\": 23168," + 
			"        \"mlockall\": true" + 
			"      }," + 
			"      \"thread_pool\": {" + 
			"        \"force_merge\": {" + 
			"          \"type\": \"fixed\"," + 
			"          \"min\": 1," + 
			"          \"max\": 1," + 
			"          \"queue_size\": -1" + 
			"        }," +
			"        \"snapshot\": {" + 
			"          \"type\": \"scaling\"," + 
			"          \"min\": 1," + 
			"          \"max\": 4," + 
			"          \"keep_alive\": \"5m\"," + 
			"          \"queue_size\": -1" + 
			"        }" + 
			"      }," + 
			"      \"transport\": {" + 
			"        \"bound_address\": [" + 
			"          \"127.0.0.1:9300\"," + 
			"          \"[::1]:9300\"" + 
			"        ]," + 
			"        \"publish_address\": \"127.0.0.1:9300\"," + 
			"        \"profiles\": {}" + 
			"      }," + 
			"      \"http\": {" + 
			"        \"bound_address\": [" + 
			"          \"127.0.0.1:9200\"," + 
			"          \"[::1]:9200\"" + 
			"        ]," + 
			"        \"publish_address\": \"127.0.0.1:9200\"," + 
			"        \"max_content_length_in_bytes\": 104857600" + 
			"      }," + 
			"      \"plugins\": []," + 
			"      \"modules\": [" + 
			"        {" + 
			"          \"name\": \"aggs-matrix-stats\"," + 
			"          \"version\": \"6.2.4\"," + 
			"          \"description\": \"Adds aggregations whose input are a list of numeric fields and output includes a matrix.\"," + 
			"          \"classname\": \"org.elasticsearch.search.aggregations.matrix.MatrixAggregationPlugin\"," + 
			"          \"extended_plugins\": []," + 
			"          \"has_native_controller\": false," + 
			"          \"requires_keystore\": false" + 
			"        }" + 
			"      ]," + 
			"      \"ingest\": {" + 
			"        \"processors\": [" + 
			"          {" + 
			"            \"type\": \"append\"" + 
			"          }" + 
			"        ]" + 
			"      }" + 
			"    }," + 
			"    \"2TD7A7g92R8usvjrbGLM7Lg2\": {" + 
			"      \"name\": \"2TD7A7g9\"," + 
			"      \"transport_address\": \"127.0.0.3:9300\"," + 
			"      \"host\": \"127.0.0.3\"," + 
			"      \"ip\": \"127.0.0.3\"," + 
			"      \"version\": \"6.2.4\"," + 
			"      \"build_hash\": \"ccec39f\"," + 
			"      \"total_indexing_buffer\": 103795916," + 
			"      \"roles\": [" + 
			"        \"master\"," + 
			"        \"data\"," + 
			"        \"ingest\"" + 
			"      ]," + 
			"      \"settings\": {" + 
			"        \"cluster\": {" + 
			"          \"name\": \"dca-index\"" + 
			"        }," + 
			"        \"node\": {" + 
			"          \"name\": \"2TD7A7g9\"" + 
			"        }," + 
			"        \"path\": {" + 
			"          \"logs\": \"C:\\\\P4V\\\\ES\\\\elasticsearch-6.2.4\\\\logs\"," + 
			"          \"home\": \"C:\\\\P4V\\\\ES\\\\elasticsearch-6.2.4\"" + 
			"        }," + 
			"        \"transport\": {" + 
			"          \"type\": {" + 
			"            \"default\": \"netty4\"" + 
			"          }" + 
			"        }" + 
			"      }," + 
			"      \"os\": {" + 
			"        \"refresh_interval_in_millis\": 1000," + 
			"        \"name\": \"Windows 10\"," + 
			"        \"arch\": \"amd64\"," + 
			"        \"version\": \"10.0\"," + 
			"        \"available_processors\": 8," + 
			"        \"allocated_processors\": 8" + 
			"      }," + 
			"      \"process\": {" + 
			"        \"refresh_interval_in_millis\": 1000," + 
			"        \"id\": 23168," + 
			"        \"mlockall\": true" + 
			"      }," + 
			"      \"thread_pool\": {" + 
			"        \"force_merge\": {" + 
			"          \"type\": \"fixed\"," + 
			"          \"min\": 1," + 
			"          \"max\": 1," + 
			"          \"queue_size\": -1" + 
			"        }," +
			"        \"snapshot\": {" + 
			"          \"type\": \"scaling\"," + 
			"          \"min\": 1," + 
			"          \"max\": 4," + 
			"          \"keep_alive\": \"5m\"," + 
			"          \"queue_size\": -1" + 
			"        }" + 
			"      }," + 
			"      \"transport\": {" + 
			"        \"bound_address\": [" + 
			"          \"127.0.0.1:9300\"," + 
			"          \"[::1]:9300\"" + 
			"        ]," + 
			"        \"publish_address\": \"127.0.0.1:9300\"," + 
			"        \"profiles\": {}" + 
			"      }," + 
			"      \"http\": {" + 
			"        \"bound_address\": [" + 
			"          \"127.0.0.1:9200\"," + 
			"          \"[::1]:9200\"" + 
			"        ]," + 
			"        \"publish_address\": \"127.0.0.1:9200\"," + 
			"        \"max_content_length_in_bytes\": 104857600" + 
			"      }," + 
			"      \"plugins\": []," + 
			"      \"modules\": [" + 
			"        {" + 
			"          \"name\": \"aggs-matrix-stats\"," + 
			"          \"version\": \"6.2.4\"," + 
			"          \"description\": \"Adds aggregations whose input are a list of numeric fields and output includes a matrix.\"," + 
			"          \"classname\": \"org.elasticsearch.search.aggregations.matrix.MatrixAggregationPlugin\"," + 
			"          \"extended_plugins\": []," + 
			"          \"has_native_controller\": false," + 
			"          \"requires_keystore\": false" + 
			"        }" + 
			"      ]," + 
			"      \"ingest\": {" + 
			"        \"processors\": [" + 
			"          {" + 
			"            \"type\": \"append\"" + 
			"          }" + 
			"        ]" + 
			"      }" + 
			"    }" +
			"  }" + 
			"}";
	
	String getRepositoryResponse = "{" + 
			"\"my_backup\": {" + 
			"\"type\": \"fs\"," + 
			"\"settings\": {" + 
			"\"location\": \"C:/P4V/ES/backup\"" + 
			"}" + 
			"}" + 
			"}";
	
	@Disabled
	public void testJsonNodeIteration() {
		Map<String, Map> rootNode = null;
		try {
			rootNode = objectMapper.readValue(aliasResponse, Map.class);
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
			response = objectMapper.readValue(aliasResponse2, GetAliasesResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(response);
		Map<String, Set<AliasData>> aliases = response.getAliases();
		assertTrue(aliases != null && !aliases.isEmpty());
		Set<AliasData> aliasDataSet = aliases.get("vm-index");
		assertTrue(aliasDataSet != null && !aliasDataSet.isEmpty());
	}
	
	@Disabled
	public void testGetNodesInfosResponseParser() {
		GetNodesInfoResponse response = null;
		try {
			response = objectMapper.readValue(nodesResponse, GetNodesInfoResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(response);
		Map<String, NodesInfo> nodesInfoMap = response.getNodes();
		assertTrue(nodesInfoMap != null && !nodesInfoMap.isEmpty());
		NodesInfo nodesinfo = nodesInfoMap.values().iterator().next();
		assertTrue(nodesinfo != null && nodesinfo.getNodeName()!=null);
	}
	
	@Disabled
	public void testPutRepositoryRequest() {
		PutRepositoryRequest request = new PutRepositoryRequest();
		request.setName("testRepo");
		request.setType("fs");
		Settings settings = Settings.builder().put("location", "location").put("compress", true).build();
		request.setSettings(settings);
		System.out.println("Payload:"+request.getRequestPayload());
	}
	
	@Disabled
	public void testGetRepositoriesResponseParser() {
		GetRepositoriesResponse response = null;
		try {
			response = objectMapper.readValue(getRepositoryResponse, GetRepositoriesResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(response);
		Map<String, RepositoryMetaData> repositoryMap = response.getRepositories();
		assertTrue(repositoryMap != null && !repositoryMap.isEmpty());
		RepositoryMetaData repositoryMetaData = repositoryMap.values().iterator().next();
		assertTrue(repositoryMetaData != null && repositoryMetaData.name()!=null);
	}
}
