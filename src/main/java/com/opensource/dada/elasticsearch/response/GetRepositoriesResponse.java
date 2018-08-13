/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import java.util.Map;

import org.elasticsearch.cluster.metadata.RepositoryMetaData;

/**
 * @author dadasaheb patil
 *
 */
public class GetRepositoriesResponse extends AbstractResponse {
	private Map<String, RepositoryMetaData> repositories;

	/**
	 * @return the repositories
	 */
	public Map<String, RepositoryMetaData> getRepositories() {
		return repositories;
	}

	/**
	 * @param repositories the repositories to set
	 */
	public void setRepositories(Map<String, RepositoryMetaData> repositories) {
		this.repositories = repositories;
	}
	
}
