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

import org.elasticsearch.common.xcontent.XContentParser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * @author dadasaheb patil
 *
 */
public class JsonParserUtils {

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
	public static final MapFactory ORDERED_MAP_FACTORY = LinkedHashMap::new;
}
