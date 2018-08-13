/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import java.util.Map;

/**
 * @author dadasaheb patil
 *
 */
public class GetMappingsResponse extends AbstractResponse {

	Map<String,Map<String, MappingData>> mappings;
	
	/**
	 * 
	 */
	public GetMappingsResponse() {
		super();
	}

	/**
	 * @return the mappings
	 */
	public Map<String, Map<String, MappingData>> getMappings() {
		return mappings;
	}

	/**
	 * @param mappings the mappings to set
	 */
	public void setMappings(Map<String, Map<String, MappingData>> mappings) {
		this.mappings = mappings;
	}

	
	
}
