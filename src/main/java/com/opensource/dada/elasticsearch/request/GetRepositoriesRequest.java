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
public class GetRepositoriesRequest extends AbstractNodeRequest {

	private Set<String> repositories = new LinkedHashSet<String>();
	/**
	 * 
	 */
	public GetRepositoriesRequest() {
		super();
	}
	
	public final boolean addRepository(String aliasName) {
		return this.repositories.add(aliasName);
	}

	public final boolean addRepository(Collection<? extends String> aliasNames) {
		return this.repositories.addAll(aliasNames);
	}
	
	public final String getJoinedRepository() {
		if (repositories.size() > 0) {
			return String.join(",", repositories);
		}

		return "_all";
	}

	@Override
	public String getRequestMethod() {
		return "GET";
	}

	@Override
	public final String getRelativeURI() {
		return "_snapshot";
	}

	@Override
	public String getRequestURI() {
		return super.getRequestURI()+"/"+getJoinedRepository();
	}
	
}
