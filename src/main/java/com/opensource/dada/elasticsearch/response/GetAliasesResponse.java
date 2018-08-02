/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import java.util.Map;
import java.util.Set;

/**
 * @author dadasaheb patil
 *
 */
public class GetAliasesResponse extends AbstractResponse {

	Map<String, Set<AliasData>> aliases;
	/**
	 * 
	 */
	public GetAliasesResponse() {
		super();
	}
	/**
	 * @return the aliases
	 */
	public Map<String, Set<AliasData>> getAliases() {
		return aliases;
	}
	/**
	 * @param aliases the aliases to set
	 */
	public void setAliases(Map<String, Set<AliasData>> aliases) {
		this.aliases = aliases;
	}

}
