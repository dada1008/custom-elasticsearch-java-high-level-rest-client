/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.Version;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.opensource.dada.elasticsearch.common.JsonParserUtils;

/**
 * @author dadasaheb patil
 *
 */
public class GetMappingsResponseParser extends JsonDeserializer<GetMappingsResponse> {

	@Override
	public GetMappingsResponse deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		if (parser.currentToken() == null) {
			parser.nextToken();
		}
		JsonParserUtils.ensureExpectedToken(JsonToken.START_OBJECT, parser.currentToken(), parser::getTokenLocation);

		GetMappingsResponse response = new GetMappingsResponse();
		String currentFieldName;
		JsonToken token = null;
		while ((token = parser.nextToken()) != JsonToken.END_OBJECT) {
			if (token == null) {
				// no data...
				break;
			}
			if (parser.currentToken() == JsonToken.FIELD_NAME) {
				currentFieldName = parser.currentName();

				if ("status".equals(currentFieldName)) {
					if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
						JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token,
								parser::getTokenLocation);
						response.setResponseCode(parser.getIntValue());
					}
				} else if ("error".equals(currentFieldName)) {
					token = parser.nextToken();
					if (token == JsonToken.VALUE_STRING) {
						String error = parser.getText();
						response.setError(error);
					} else if (token == JsonToken.START_OBJECT) {
						parser.nextToken();
						String exception = parser.getValueAsString();
						String error = exception;
						if (response.getError() != null) {
							error = response.getError() + ", exception:" + exception;
						}
						response.setError(error);
					} else if (token == JsonToken.START_ARRAY) {
						parser.skipChildren();
					}
				} else if ("cluster_name".equals(currentFieldName)) {
					if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
						JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_STRING, token, parser::getTokenLocation);
						response.setClusterName(parser.getText());
					}
				} else if ("_nodes".equals(currentFieldName)) {
					if (parser.nextToken() == JsonToken.START_OBJECT) {
						parseNodes(parser, response);
					}
				} else if ("nodes".equals(currentFieldName)) {
					Map<String, NodesInfo> nodesInfos = parseNodesInfos(parser);
					response.setNodes(nodesInfos);
				}
			}
		}
		return response;
	}

	private static Map<String, NodesInfo> parseNodesInfos(JsonParser parser) throws IOException {
		Map<String, NodesInfo> nodesInfos = new HashMap<>();
		JsonToken token = parser.nextToken();
		if (token == JsonToken.START_OBJECT) {
			while (parser.nextToken() != JsonToken.END_OBJECT) {
				String nodeId = parser.getText();
				NodesInfo nodeInfo = parseNodesInfo(parser);
				nodeInfo.setNodeId(nodeId);
				nodesInfos.put(nodeId, nodeInfo);
			}
		}
		return nodesInfos;
	}

	public static NodesInfo parseNodesInfo(JsonParser parser) throws IOException {
		NodesInfo nodeInfo = new NodesInfo();

		String currentFieldName = null;
		JsonToken token = parser.nextToken();
		if (token == null) {
			// no data...
			return nodeInfo;
		}

		while ((token = parser.nextToken()) != JsonToken.END_OBJECT) {
			if (token == JsonToken.FIELD_NAME) {
				currentFieldName = parser.currentName();
			} else if (token == JsonToken.START_OBJECT) {
				if ("settings".equals(currentFieldName) || "os".equals(currentFieldName)
						|| "process".equals(currentFieldName) || "jvm".equals(currentFieldName)
						|| "thread_pool".equals(currentFieldName) || "transport".equals(currentFieldName)
						|| "http".equals(currentFieldName) || "ingest".equals(currentFieldName)) {
					Map<String, Object> map = JsonParserUtils.readMap(parser, JsonParserUtils.ORDERED_MAP_FACTORY);
					Map<String, Object> othersMap = nodeInfo.getOthers();
					if (othersMap == null) {
						othersMap = new LinkedHashMap<>();
					}
					othersMap.put(currentFieldName, map);
					nodeInfo.setOthers(othersMap);
				}
			} else if (token == JsonToken.VALUE_STRING) {
				if ("name".equals(currentFieldName)) {
					String value = parser.getText();
					nodeInfo.setNodeName(value);
				} else if ("transport_address".equals(currentFieldName)) {
					nodeInfo.setAddress(parser.getText());
				} else if ("host".equals(currentFieldName)) {
					nodeInfo.setHostName(parser.getText());
				} else if ("ip".equals(currentFieldName)) {
					nodeInfo.setHostAddress(parser.getText());
				} else if ("version".equals(currentFieldName)) {
					Version version = Version.fromString(parser.getText());
					nodeInfo.setVersion(version);
				} else if ("ephemeral_id".equals(currentFieldName)) {
					nodeInfo.setEphemeralId(parser.getText());
				} else if ("roles".equals(currentFieldName)) {
					String role = parser.getText();
					if (role != null && !role.isEmpty()) {
						Set<String> roles = nodeInfo.getRoles();
						if (roles == null) {
							roles = new LinkedHashSet<>();
						}
						roles.add(role);
						nodeInfo.setRoles(roles);
					}
				}
			} else if (token == JsonToken.START_ARRAY) {
				if ("plugins".equals(currentFieldName) || "modules".equals(currentFieldName)) {
					List<Object> list = JsonParserUtils.readList(parser, JsonParserUtils.ORDERED_MAP_FACTORY);
					Map<String, Object> othersMap = nodeInfo.getOthers();
					if (othersMap == null) {
						othersMap = new LinkedHashMap<>();
					}
					othersMap.put(currentFieldName, list);
					nodeInfo.setOthers(othersMap);
				}
			} else {
				parser.skipChildren();
			}
		}
		return nodeInfo;
	}

	public static void parseNodes(JsonParser parser, GetNodesInfoResponse response) throws IOException {
		String currentFieldName = null;
		JsonToken token = null;
		while ((token = parser.nextToken()) != JsonToken.END_OBJECT) {
			if (token == JsonToken.FIELD_NAME) {
				currentFieldName = parser.currentName();
			} else if (token == JsonToken.VALUE_NUMBER_INT) {
				if ("total".equals(currentFieldName)) {
					response.setTotalNodes(parser.getIntValue());
				} else if ("successful".equals(currentFieldName)) {
					response.setSuccessfulNodes(parser.getIntValue());
				} else if ("failed".equals(currentFieldName)) {
					response.setFailedNodes(parser.getIntValue());
				}
			}
		}
	}
}
