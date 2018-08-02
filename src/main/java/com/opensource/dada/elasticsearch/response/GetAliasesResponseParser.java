/**
 * 
 */
package com.opensource.dada.elasticsearch.response;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.elasticsearch.common.xcontent.XContentParser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author dapatil
 *
 */
public class GetAliasesResponseParser extends JsonDeserializer<GetAliasesResponse> {

	@Override
	public GetAliasesResponse deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		if (parser.currentToken() == null) {
            parser.nextToken();
        }
        ensureExpectedToken(JsonToken.START_OBJECT, parser.currentToken(), parser::getTokenLocation);
        
		GetAliasesResponse response = new GetAliasesResponse();
		Map<String, Set<AliasData>> aliases = new HashMap<>();
		String currentFieldName;
		JsonToken token =null;
		while (parser.nextToken() != JsonToken.END_OBJECT) {
            if (parser.currentToken() == JsonToken.FIELD_NAME) {
                currentFieldName = parser.currentName();

                if ("status".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                        ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
                        response.setResponseCode(parser.getIntValue());
                    }
                } else if ("error".equals(currentFieldName)) {
                    token = parser.nextToken();
                    if (token == JsonToken.VALUE_STRING) {
                        String error = parser.getText();
                        response.setError(error);
                    } else if (token == JsonToken.START_OBJECT) {
                        parser.nextToken();
                        //exception = ElasticsearchException.innerFromXContent(parser, true);
                        String exception = parser.getValueAsString();
                        String error = exception;
                        if(response.getError()!=null) {
                        	error = response.getError() + ", exception:"+exception;
                        }
                        response.setError(error);
                    } else if (token == JsonToken.START_ARRAY) {
                        parser.skipChildren();
                    }
                } else {
                    String indexName = parser.currentName();
                    if (parser.nextToken() == JsonToken.START_OBJECT) {
                        Set<AliasData> parseInside = parseAliases(parser);
                        aliases.put(indexName, parseInside);
                    }
                }
            }
        }
		response.setAliases(aliases);
		return response;
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
	
	private static Set<AliasData> parseAliases(JsonParser parser) throws IOException {
        Set<AliasData> aliases = new HashSet<>();
        JsonToken token;
        String currentFieldName = null;
        while ((token = parser.nextToken()) != JsonToken.END_OBJECT) {
            if (token == JsonToken.FIELD_NAME) {
                currentFieldName = parser.currentName();
            } else if (token == JsonToken.START_OBJECT) {
                if ("aliases".equals(currentFieldName)) {
                    while (parser.nextToken() != JsonToken.END_OBJECT) {
                        AliasData aliasData = parseAliasData(parser);
                        aliases.add(aliasData);
                    }
                } else {
                    parser.skipChildren();
                }
            } else if (token == JsonToken.START_ARRAY) {
                parser.skipChildren();
            }
        }
        return aliases;
    }
	
	public static AliasData parseAliasData(JsonParser parser) throws IOException {
        AliasData aliasData = new AliasData();
        
        aliasData.setAlias(parser.currentName());
        
        String currentFieldName = null;
        JsonToken token = parser.nextToken();
        if (token == null) {
            // no data...
            return aliasData;
        }
        
        while ((token = parser.nextToken()) != JsonToken.END_OBJECT) {
            if (token == JsonToken.FIELD_NAME) {
                currentFieldName = parser.currentName();
            } else if (token == JsonToken.START_OBJECT) {
                if ("filter".equals(currentFieldName)) {
                	Map<String, Object> map = readMap(parser, ORDERED_MAP_FACTORY);
                    aliasData.setFilter(map);
                }
            } else if (token == JsonToken.VALUE_EMBEDDED_OBJECT) {
                if ("filter".equals(currentFieldName)) {
                	Map<String, Object> map = new LinkedHashMap<>();
                    aliasData.setFilter(parser.getEmbeddedObject());
                }
            } else if (token == JsonToken.VALUE_STRING) {
                if ("routing".equals(currentFieldName)) {
                	String value = parser.getText();
                	aliasData.setIndexRouting(value);
                	aliasData.setSearchRouting(value);
                } else if ("index_routing".equals(currentFieldName) || "indexRouting".equals(currentFieldName)) {
                	aliasData.setIndexRouting(parser.getText());
                } else if ("search_routing".equals(currentFieldName) || "searchRouting".equals(currentFieldName)) {
                	aliasData.setSearchRouting(parser.getText());
                }
            }
        }
        return aliasData;
    }
	
	static Map<String, Object> readMap(JsonParser parser,MapFactory mapFactory) throws IOException {
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
	
	static Object readValue(JsonParser parser, MapFactory mapFactory, JsonToken token) throws IOException {
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
	
	static List<Object> readList(JsonParser parser, MapFactory mapFactory) throws IOException {
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
                    + XContentParser.Token.START_ARRAY + " but got " + token+", location line:"+location.getLineNr()+" column:"+location.getColumnNr());
        }

        List<Object> list = new ArrayList<>();
        for (; token != null && token != JsonToken.END_ARRAY; token = parser.nextToken()) {
            list.add(readValue(parser, mapFactory, token));
        }
        return list;
    }
	
	interface MapFactory {
        Map<String, Object> newMap();
    }
	static final MapFactory ORDERED_MAP_FACTORY = LinkedHashMap::new;
}
