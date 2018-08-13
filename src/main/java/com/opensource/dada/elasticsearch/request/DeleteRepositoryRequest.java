/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

/**
 * @author dadasaheb patil
 *
 */
public class DeleteRepositoryRequest extends AbstractNodeRequest {

	private String repository;
	/**
	 * 
	 */
	public DeleteRepositoryRequest() {
		super();
	}
	
	/**
	 * @return the repository
	 */
	public String getRepository() {
		return repository;
	}

	/**
	 * @param repository the repository to set
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}
	

	@Override
	public String getRequestMethod() {
		return "DELETE";
	}

	@Override
	public final String getRelativeURI() {
		return "_snapshot";
	}

	@Override
	public String getRequestURI() {
		return super.getRequestURI()+"/"+repository;
	}

}
