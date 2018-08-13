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
public class GetSnapshotsRequest extends AbstractNodeRequest {

	private String repository;
	private Set<String> snapshots = new LinkedHashSet<>();
	/**
	 * 
	 */
	public GetSnapshotsRequest() {
		super();
	}
	
	public final boolean addSnapshot(String snapshotName) {
		return this.snapshots.add(snapshotName);
	}

	public final boolean addSnapshot(Collection<? extends String> snapshotNames) {
		return this.snapshots.addAll(snapshotNames);
	}
	
	public final String getJoinedSnapshot() {
		if (snapshots.size() > 0) {
			return String.join(",", snapshots);
		}

		return "_all";
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
		return "GET";
	}

	@Override
	public final String getRelativeURI() {
		return "_snapshot";
	}

	@Override
	public String getRequestURI() {
		return super.getRequestURI()+"/"+repository+"/"+getJoinedSnapshot();
	}
	
}
