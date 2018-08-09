/**
 * 
 */
package com.opensource.dada.elasticsearch.response;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.unit.TimeValue;

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
public class ClusterHealthResponseParser extends JsonDeserializer<ClusterHealthResponse> {

	@Override
	public ClusterHealthResponse deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		if (parser.currentToken() == null) {
            parser.nextToken();
        }
		JsonParserUtils.ensureExpectedToken(JsonToken.START_OBJECT, parser.currentToken(), parser::getTokenLocation);
        
		ClusterHealthResponse response = new ClusterHealthResponse();
		String currentFieldName;
		JsonToken token =null;
		while (parser.nextToken() != JsonToken.END_OBJECT) {
            if (parser.currentToken() == JsonToken.FIELD_NAME) {
                currentFieldName = parser.currentName();

                if ("cluster_name".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	response.setClusterName(parser.getText());
                    }
                } else if ("status".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                        String status = parser.getText();
                    	response.setStatus(ClusterHealthStatus.fromString(status));
                    }
                } else if ("timed_out".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	response.setTimedOut(parser.getBooleanValue());
                    }
                } else if ("number_of_nodes".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setNumberOfNodes(parser.getIntValue());
                    }
                } else if ("number_of_data_nodes".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setNumberOfDataNodes(parser.getIntValue());
                    }
                } else if ("active_primary_shards".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setActivePrimaryShards(parser.getIntValue());
                    }
                } else if ("active_shards".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setActiveShards(parser.getIntValue());
                    }
                } else if ("relocating_shards".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setRelocatingShards(parser.getIntValue());
                    }
                } else if ("initializing_shards".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setInitializingShards(parser.getIntValue());
                    }
                } else if ("unassigned_shards".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setUnassignedShards(parser.getIntValue());
                    }
                } else if ("delayed_unassigned_shards".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setDelayedUnassignedShards(parser.getIntValue());
                    }
                } else if ("number_of_pending_tasks".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setNumberOfPendingTasks(parser.getIntValue());
                    }
                } else if ("number_of_in_flight_fetch".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setNumberOfInFlightFetch(parser.getIntValue());
                    }
                } else if ("task_max_waiting_in_queue_millis".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                    	response.setTaskMaxWaitingTime(TimeValue.timeValueMillis(parser.getLongValue()));
                    }
                } else if ("active_shards_percent_as_number".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	response.setActiveShardsPercent(parser.getDoubleValue());
                    }
                }
            }
        }
		return response;
	}

}
