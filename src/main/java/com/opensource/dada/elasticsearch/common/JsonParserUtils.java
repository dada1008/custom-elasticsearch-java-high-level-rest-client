/**
 * 
 */
package com.opensource.dada.elasticsearch.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.opensource.dada.elasticsearch.response.ClusterHealthResponse;
import com.opensource.dada.elasticsearch.response.ClusterHealthResponseParser;
import com.opensource.dada.elasticsearch.response.GetAliasesResponse;
import com.opensource.dada.elasticsearch.response.GetAliasesResponseParser;
import com.opensource.dada.elasticsearch.response.GetMappingsResponse;
import com.opensource.dada.elasticsearch.response.GetMappingsResponseParser;
import com.opensource.dada.elasticsearch.response.GetNodesInfoResponse;
import com.opensource.dada.elasticsearch.response.GetNodesInfoResponseParser;
import com.opensource.dada.elasticsearch.response.GetRepositoriesResponse;
import com.opensource.dada.elasticsearch.response.GetRepositoriesResponseParser;

/**
 * @author dadasaheb patil
 *
 */
public class JsonParserUtils {

	public static ObjectMapper objectMapper = new ObjectMapper();
	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		SimpleModule module = new SimpleModule();
		module.addDeserializer(GetAliasesResponse.class, new GetAliasesResponseParser());
		module.addDeserializer(GetNodesInfoResponse.class, new GetNodesInfoResponseParser());
		module.addDeserializer(ClusterHealthResponse.class, new ClusterHealthResponseParser());
		module.addDeserializer(GetRepositoriesResponse.class, new GetRepositoriesResponseParser());
		module.addDeserializer(GetMappingsResponse.class, new GetMappingsResponseParser());
		objectMapper.registerModule(module);
	}
	
	/**
	 * 
	 */
	public JsonParserUtils() {
	}

	public static void ensureExpectedToken(JsonToken expected, JsonToken actual, Supplier<JsonLocation> location) {
        if (actual != expected) {
        	int lineNumber = -1;
            int columnNumber = -1;
            JsonLocation jsonLocation = location.get();
            if (jsonLocation != null) {
                lineNumber = jsonLocation.getLineNr();
                columnNumber = jsonLocation.getColumnNr();
            }
            String message = "Failed to parse object: expecting token of type [%s] but found [%s]; line number [%s], column number [%s].";
            throw new RuntimeException(String.format(Locale.ROOT, message, expected, actual, lineNumber, columnNumber));
        }
    }
	
	public static Map<String, Object> readMap(JsonParser parser,MapFactory mapFactory) throws IOException {
		Map<String, Object> map = mapFactory.newMap();
        JsonToken token = parser.currentToken();
        if (token == null) {
            token = parser.nextToken();
        }
        if (token == JsonToken.START_OBJECT) {
            token = parser.nextToken();
        }
        for (; token == JsonToken.FIELD_NAME; token = parser.nextToken()) {
            // Must point to field name
            String fieldName = parser.currentName();
            // And then the value...
            token = parser.nextToken();
            Object value = readValue(parser, mapFactory, token);
            map.put(fieldName, value);
        }
        return map;
    }
	
	public static Object readValue(JsonParser parser, MapFactory mapFactory, JsonToken token) throws IOException {
        if (token == JsonToken.VALUE_NULL) {
            return null;
        } else if (token == JsonToken.VALUE_STRING) {
            return parser.getText();
        } else if (token == JsonToken.VALUE_NUMBER_INT || token == JsonToken.VALUE_NUMBER_FLOAT) {
            return parser.getNumberValue();
        } else if (token == JsonToken.VALUE_TRUE || token == JsonToken.VALUE_FALSE) {
            return parser.getBooleanValue();
        } else if (token == JsonToken.START_OBJECT) {
            return readMap(parser, mapFactory);
        } else if (token == JsonToken.START_ARRAY) {
            return readList(parser, mapFactory);
        } else if (token == JsonToken.VALUE_EMBEDDED_OBJECT) {
            return parser.getBinaryValue();
        }
        return null;
    }
	
	public static List<Object> readList(JsonParser parser, MapFactory mapFactory) throws IOException {
		JsonToken token = parser.currentToken();
        if (token == null) {
            token = parser.nextToken();
        }
        if (token == JsonToken.FIELD_NAME) {
            token = parser.nextToken();
        }
        if (token == JsonToken.START_ARRAY) {
            token = parser.nextToken();
        } else {
        	JsonLocation location = parser.getTokenLocation();
            throw new RuntimeException("Failed to parse list:  expecting "
                    + JsonToken.START_ARRAY + " but got " + token+", location line:"+location.getLineNr()+" column:"+location.getColumnNr());
        }

        List<Object> list = new ArrayList<>();
        for (; token != null && token != JsonToken.END_ARRAY; token = parser.nextToken()) {
            list.add(readValue(parser, mapFactory, token));
        }
        return list;
    }
	/**
     * @throws ParsingException with a "unknown token found" reason
     */
    public static void throwUnknownToken(JsonToken token, JsonLocation location) {
        throw new RuntimeException("Failed to parse object: unexpected token ["+token+"] found, location line:"+location.getLineNr()+" column:"+location.getColumnNr());
    }
    
	
	interface MapFactory {
        Map<String, Object> newMap();
    }
	public static final MapFactory ORDERED_MAP_FACTORY = LinkedHashMap::new;
}
