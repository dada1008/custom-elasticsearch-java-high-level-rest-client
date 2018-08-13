/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import java.util.List;

import org.elasticsearch.snapshots.SnapshotInfo;

/**
 * @author dadasaheb patil
 *
 */
public class GetSnapshotsResponse extends AbstractResponse {
	private List<SnapshotInfo> snapshots;

	/**
	 * @return the snapshots
	 */
	public List<SnapshotInfo> getSnapshots() {
		return snapshots;
	}

	/**
	 * @param snapshots the snapshots to set
	 */
	public void setSnapshots(List<SnapshotInfo> snapshots) {
		this.snapshots = snapshots;
	}

}
