/**
 * 
 */
package com.opensource.dada.elasticsearch.response;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
public class GetAliasesResponseParser extends JsonDeserializer<GetAliasesResponse> {

	@Override
	public GetAliasesResponse deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		if (parser.currentToken() == null) {
            parser.nextToken();
        }
		JsonParserUtils.ensureExpectedToken(JsonToken.START_OBJECT, parser.currentToken(), parser::getTokenLocation);
        
		GetAliasesResponse response = new GetAliasesResponse();
		Map<String, Set<AliasData>> aliases = new HashMap<>();
		String currentFieldName;
		JsonToken token =null;
		while (parser.nextToken() != JsonToken.END_OBJECT) {
            if (parser.currentToken() == JsonToken.FIELD_NAME) {
                currentFieldName = parser.currentName();

                if ("status".equals(currentFieldName)) {
                    if ((token = parser.nextToken()) != JsonToken.FIELD_NAME) {
                    	JsonParserUtils.ensureExpectedToken(JsonToken.VALUE_NUMBER_INT, token, parser::getTokenLocation);
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
                	Map<String, Object> map = JsonParserUtils.readMap(parser, JsonParserUtils.ORDERED_MAP_FACTORY);
                    aliasData.setFilter(map);
                }
            } else if (token == JsonToken.VALUE_EMBEDDED_OBJECT) {
                if ("filter".equals(currentFieldName)) {
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
}
