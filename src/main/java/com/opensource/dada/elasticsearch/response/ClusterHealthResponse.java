/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.unit.TimeValue;

/**
 * @author dadasaheb patil
 *
 */
public class ClusterHealthResponse extends AbstractResponse {

	private String clusterName;
	private ClusterHealthStatus status;
	private boolean timedOut;
	private int numberOfNodes;
	private int numberOfDataNodes;
	private int activePrimaryShards;
	private int activeShards;
	private int relocatingShards;
	private int initializingShards;
	private int unassignedShards;
	private int delayedUnassignedShards = 0;
	private int numberOfPendingTasks = 0;
	private int numberOfInFlightFetch = 0;
	private TimeValue taskMaxWaitingTime = TimeValue.timeValueMillis(0);
	private double activeShardsPercent;
    
	/**
	 * 
	 */
	public ClusterHealthResponse() {
		super();
	}

	/**
	 * @return the clusterName
	 */
	public String getClusterName() {
		return clusterName;
	}

	/**
	 * @param clusterName the clusterName to set
	 */
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	/**
	 * @return the status
	 */
	public ClusterHealthStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ClusterHealthStatus status) {
		this.status = status;
	}

	/**
	 * @return the timedOut
	 */
	public boolean isTimedOut() {
		return timedOut;
	}

	/**
	 * @param timedOut the timedOut to set
	 */
	public void setTimedOut(boolean timedOut) {
		this.timedOut = timedOut;
	}

	/**
	 * @return the numberOfNodes
	 */
	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	/**
	 * @param numberOfNodes the numberOfNodes to set
	 */
	public void setNumberOfNodes(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}

	/**
	 * @return the numberOfDataNodes
	 */
	public int getNumberOfDataNodes() {
		return numberOfDataNodes;
	}

	/**
	 * @param numberOfDataNodes the numberOfDataNodes to set
	 */
	public void setNumberOfDataNodes(int numberOfDataNodes) {
		this.numberOfDataNodes = numberOfDataNodes;
	}

	/**
	 * @return the activePrimaryShards
	 */
	public int getActivePrimaryShards() {
		return activePrimaryShards;
	}

	/**
	 * @param activePrimaryShards the activePrimaryShards to set
	 */
	public void setActivePrimaryShards(int activePrimaryShards) {
		this.activePrimaryShards = activePrimaryShards;
	}

	/**
	 * @return the activeShards
	 */
	public int getActiveShards() {
		return activeShards;
	}

	/**
	 * @param activeShards the activeShards to set
	 */
	public void setActiveShards(int activeShards) {
		this.activeShards = activeShards;
	}

	/**
	 * @return the relocatingShards
	 */
	public int getRelocatingShards() {
		return relocatingShards;
	}

	/**
	 * @param relocatingShards the relocatingShards to set
	 */
	public void setRelocatingShards(int relocatingShards) {
		this.relocatingShards = relocatingShards;
	}

	/**
	 * @return the initializingShards
	 */
	public int getInitializingShards() {
		return initializingShards;
	}

	/**
	 * @param initializingShards the initializingShards to set
	 */
	public void setInitializingShards(int initializingShards) {
		this.initializingShards = initializingShards;
	}

	/**
	 * @return the unassignedShards
	 */
	public int getUnassignedShards() {
		return unassignedShards;
	}

	/**
	 * @param unassignedShards the unassignedShards to set
	 */
	public void setUnassignedShards(int unassignedShards) {
		this.unassignedShards = unassignedShards;
	}

	/**
	 * @return the delayedUnassignedShards
	 */
	public int getDelayedUnassignedShards() {
		return delayedUnassignedShards;
	}

	/**
	 * @param delayedUnassignedShards the delayedUnassignedShards to set
	 */
	public void setDelayedUnassignedShards(int delayedUnassignedShards) {
		this.delayedUnassignedShards = delayedUnassignedShards;
	}

	/**
	 * @return the numberOfPendingTasks
	 */
	public int getNumberOfPendingTasks() {
		return numberOfPendingTasks;
	}

	/**
	 * @param numberOfPendingTasks the numberOfPendingTasks to set
	 */
	public void setNumberOfPendingTasks(int numberOfPendingTasks) {
		this.numberOfPendingTasks = numberOfPendingTasks;
	}

	/**
	 * @return the numberOfInFlightFetch
	 */
	public int getNumberOfInFlightFetch() {
		return numberOfInFlightFetch;
	}

	/**
	 * @param numberOfInFlightFetch the numberOfInFlightFetch to set
	 */
	public void setNumberOfInFlightFetch(int numberOfInFlightFetch) {
		this.numberOfInFlightFetch = numberOfInFlightFetch;
	}

	/**
	 * @return the taskMaxWaitingTime
	 */
	public TimeValue getTaskMaxWaitingTime() {
		return taskMaxWaitingTime;
	}

	/**
	 * @param taskMaxWaitingTime the taskMaxWaitingTime to set
	 */
	public void setTaskMaxWaitingTime(TimeValue taskMaxWaitingTime) {
		this.taskMaxWaitingTime = taskMaxWaitingTime;
	}

	/**
	 * @return the activeShardsPercent
	 */
	public double getActiveShardsPercent() {
		return activeShardsPercent;
	}

	/**
	 * @param activeShardsPercent the activeShardsPercent to set
	 */
	public void setActiveShardsPercent(double activeShardsPercent) {
		this.activeShardsPercent = activeShardsPercent;
	}

}
