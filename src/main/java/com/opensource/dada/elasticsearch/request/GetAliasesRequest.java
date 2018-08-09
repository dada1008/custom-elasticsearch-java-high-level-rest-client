/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author dadasaheb patil
 *
 */
public class GetAliasesRequest extends AbstractIndexRequest {
	private Set<String> aliases = new LinkedHashSet<String>();
	
	/**
	 * 
	 */
	public GetAliasesRequest() {
		super();
	}

	public final boolean addAlias(String aliasName) {
		return this.aliases.add(aliasName);
	}

	public final boolean addAliases(Collection<? extends String> aliasNames) {
		return this.aliases.addAll(aliasNames);
	}
	
	public final String getJoinedAliases() {
		if (aliases.size() > 0) {
			return String.join(",", aliases);
		}

		return null;
	}
	
	@Override
	public String getRequestMethod() {
		return "GET";
	}

	@Override
	public final String getRelativeURI() {
		return "_alias";
	}
	
	@Override
	public final String getRequestURI() {
		StringBuilder sb = new StringBuilder(super.getRequestURI());
		
		String aliasName = getJoinedAliases();
		if (aliasName != null && !aliasName.isEmpty()) {
			sb.append("/").append(aliasName);
		}
		
		return sb.toString();
	}

}
