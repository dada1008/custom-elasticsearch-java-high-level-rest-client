/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

/**
 * @author dadasaheb patil
 *
 */
public class DeleteSnapshotRequest extends AbstractNodeRequest {

	private String repository;
	private String snapshot;
	/**
	 * 
	 */
	public DeleteSnapshotRequest() {
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
	

	/**
	 * @return the snapshot
	 */
	public String getSnapshot() {
		return snapshot;
	}

	/**
	 * @param snapshot the snapshot to set
	 */
	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
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
		return super.getRequestURI()+"/"+repository+"/"+snapshot;
	}

}
