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
public class GetNodesInfoResponse extends AbstractResponse {

	Map<String, NodesInfo> nodes;
	int totalNodes;
	int successfulNodes;
	int failedNodes;
	String clusterName;
	
	/**
	 * 
	 */
	public GetNodesInfoResponse() {
		super();
	}

	/**
	 * @return the nodes
	 */
	public Map<String, NodesInfo> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(Map<String, NodesInfo> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the totalNodes
	 */
	public int getTotalNodes() {
		return totalNodes;
	}

	/**
	 * @param totalNodes the totalNodes to set
	 */
	public void setTotalNodes(int totalNodes) {
		this.totalNodes = totalNodes;
	}

	/**
	 * @return the successfulNodes
	 */
	public int getSuccessfulNodes() {
		return successfulNodes;
	}

	/**
	 * @param successfulNodes the successfulNodes to set
	 */
	public void setSuccessfulNodes(int successfulNodes) {
		this.successfulNodes = successfulNodes;
	}

	/**
	 * @return the failedNodes
	 */
	public int getFailedNodes() {
		return failedNodes;
	}

	/**
	 * @param failedNodes the failedNodes to set
	 */
	public void setFailedNodes(int failedNodes) {
		this.failedNodes = failedNodes;
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
	
}
