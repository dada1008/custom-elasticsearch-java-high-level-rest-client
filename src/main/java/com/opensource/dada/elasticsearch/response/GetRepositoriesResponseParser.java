/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.ElasticsearchParseException;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.metadata.RepositoryMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;

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
public class GetRepositoriesResponseParser extends JsonDeserializer<GetRepositoriesResponse> {

	@Override
	public GetRepositoriesResponse deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		if (parser.currentToken() == null) {
			parser.nextToken();
		}
		JsonParserUtils.ensureExpectedToken(JsonToken.START_OBJECT, parser.currentToken(), parser::getTokenLocation);

		GetRepositoriesResponse response = new GetRepositoriesResponse();
		JsonToken token = null;
		Map<String, RepositoryMetaData> repositories = new HashMap<>();
		while ((token = parser.nextToken()) != JsonToken.END_OBJECT) {
			if (token == JsonToken.FIELD_NAME) {
				String name = parser.currentName();
				if (parser.nextToken() != JsonToken.START_OBJECT) {
					throw new ElasticsearchParseException("failed to parse repository [{}], expected object", name);
				}
				String type = null;
				Settings settings = Settings.EMPTY;
				while ((token = parser.nextToken()) != JsonToken.END_OBJECT) {
					if (token == JsonToken.FIELD_NAME) {
						String currentFieldName = parser.currentName();
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
		                } else if ("type".equals(currentFieldName)) {
							if (parser.nextToken() != JsonToken.VALUE_STRING) {
								throw new ElasticsearchParseException("failed to parse repository [{}], unknown type",
										name);
							}
							type = parser.getText();
						} else if ("settings".equals(currentFieldName)) {
							if (parser.nextToken() != JsonToken.START_OBJECT) {
								throw new ElasticsearchParseException(
										"failed to parse repository [{}], incompatible params", name);
							}
							settings = parseSettings(parser);
						} else {
							throw new ElasticsearchParseException("failed to parse repository [{}], unknown field [{}]",
									name, currentFieldName);
						}
					} else {
						throw new ElasticsearchParseException("failed to parse repository [{}]", name);
					}
				}
				if (type == null) {
					throw new ElasticsearchParseException("failed to parse repository [{}], missing repository type",
							name);
				}
				repositories.put(name, new RepositoryMetaData(name, type, settings));
			} else {
				throw new ElasticsearchParseException("failed to parse repositories");
			}
		}
		response.setRepositories(repositories);
		return response;
	}

	private Settings parseSettings(JsonParser parser) throws IOException {

		return fromXContent(parser, true, false);
	}

	private static Settings fromXContent(JsonParser parser, boolean allowNullValues, boolean validateEndOfStream)
			throws IOException {
		if (parser.currentToken() == null) {
			parser.nextToken();
		}
		JsonParserUtils.ensureExpectedToken(JsonToken.START_OBJECT, parser.currentToken(), parser::getTokenLocation);
		Builder innerBuilder = Settings.builder();
		StringBuilder currentKeyBuilder = new StringBuilder();
		fromXContent(parser, currentKeyBuilder, innerBuilder, allowNullValues);
		if (validateEndOfStream) {
			// ensure we reached the end of the stream
			JsonToken lastToken = null;
			try {
				while (!parser.isClosed() && (lastToken = parser.nextToken()) == null)
					;
			} catch (Exception e) {
				throw new ElasticsearchParseException(
						"malformed, expected end of settings but encountered additional content starting at line number: [{}], "
								+ "column number: [{}]",
						e, parser.getTokenLocation().getLineNr(), parser.getTokenLocation().getColumnNr());
			}
			if (lastToken != null) {
				throw new ElasticsearchParseException(
						"malformed, expected end of settings but encountered additional content starting at line number: [{}], "
								+ "column number: [{}]",
						parser.getTokenLocation().getLineNr(), parser.getTokenLocation().getColumnNr());
			}
		}
		return innerBuilder.build();
	}

	private static void fromXContent(JsonParser parser, StringBuilder keyBuilder, Settings.Builder builder,
			boolean allowNullValues) throws IOException {
		final int length = keyBuilder.length();
		while (parser.nextToken() != JsonToken.END_OBJECT) {
			if (parser.currentToken() == JsonToken.FIELD_NAME) {
				keyBuilder.setLength(length);
				keyBuilder.append(parser.currentName());
			} else if (parser.currentToken() == JsonToken.START_OBJECT) {
				keyBuilder.append('.');
				fromXContent(parser, keyBuilder, builder, allowNullValues);
			} else if (parser.currentToken() == JsonToken.START_ARRAY) {
				List<String> list = new ArrayList<>();
				while (parser.nextToken() != JsonToken.END_ARRAY) {
					if (parser.currentToken() == JsonToken.VALUE_STRING) {
						list.add(parser.getText());
					} else if (parser.currentToken() == JsonToken.VALUE_NUMBER_INT || parser.currentToken() == JsonToken.VALUE_NUMBER_FLOAT) {
						list.add(parser.getText()); // just use the string representation here
					} else if (parser.currentToken() == JsonToken.VALUE_TRUE || parser.currentToken() == JsonToken.VALUE_FALSE) {
						list.add(String.valueOf(parser.getText()));
					} else {
						throw new IllegalStateException("only value lists are allowed in serialized settings");
					}
				}
				String key = keyBuilder.toString();
				validateValue(key, list, builder, parser, allowNullValues);
				builder.putList(key, list);
			} else if (parser.currentToken() == JsonToken.VALUE_NULL) {
				String key = keyBuilder.toString();
				validateValue(key, null, builder, parser, allowNullValues);
				builder.putNull(key);
			} else if (parser.currentToken() == JsonToken.VALUE_STRING
					|| parser.currentToken() == JsonToken.VALUE_NUMBER_INT) {
				String key = keyBuilder.toString();
				String value = parser.getText();
				validateValue(key, value, builder, parser, allowNullValues);
				builder.put(key, value);
			} else if (parser.currentToken() == JsonToken.VALUE_TRUE) {
				String key = keyBuilder.toString();
				validateValue(key, parser.getText(), builder, parser, allowNullValues);
				builder.put(key, parser.getBooleanValue());
			} else {
				JsonParserUtils.throwUnknownToken(parser.currentToken(), parser.getTokenLocation());
			}
		}
	}

	private static void validateValue(String key, Object currentValue, Settings.Builder builder, JsonParser parser,
			boolean allowNullValues) {
		if (builder.get(key)!=null) {
			throw new ElasticsearchParseException(
					"duplicate settings key [{}] found at line number [{}], column number [{}], previous value [{}], current value [{}]",
					key, parser.getTokenLocation().getLineNr(), parser.getTokenLocation().getColumnNr(),
					builder.get(key), currentValue);
		}

		if (currentValue == null && allowNullValues == false) {
			throw new ElasticsearchParseException(
					"null-valued setting found for key [{}] found at line number [{}], column number [{}]", key,
					parser.getTokenLocation().getLineNr(), parser.getTokenLocation().getColumnNr());
		}
	}
}
