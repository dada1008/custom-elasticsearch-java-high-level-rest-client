/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import org.elasticsearch.snapshots.SnapshotInfo;

/**
 * @author dadasaheb patil
 *
 */
public class CreateSnapshotResponse extends AbstractResponse {
	private SnapshotInfo snapshot;

	/**
	 * @return the snapshot
	 */
	public SnapshotInfo getSnapshot() {
		return snapshot;
	}

	/**
	 * @param snapshots the snapshot to set
	 */
	public void setSnapshot(SnapshotInfo snapshot) {
		this.snapshot = snapshot;
	}

}
