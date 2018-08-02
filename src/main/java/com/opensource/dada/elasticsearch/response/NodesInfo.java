/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import java.util.Map;
import java.util.Set;

import org.elasticsearch.Version;
import org.elasticsearch.cluster.node.DiscoveryNode.Role;

/**
 * @author dapatil
 *
 */
public class NodesInfo {
	
	private String nodeName;
    private String nodeId;
    private String ephemeralId;
    private String hostName;
    private String hostAddress;
    private String address;
    private Version version;
    private Set<String> roles;
    private Map<String, Object> others;
	/**
	 * 
	 */
	public NodesInfo() {
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the ephemeralId
	 */
	public String getEphemeralId() {
		return ephemeralId;
	}

	/**
	 * @param ephemeralId the ephemeralId to set
	 */
	public void setEphemeralId(String ephemeralId) {
		this.ephemeralId = ephemeralId;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the hostAddress
	 */
	public String getHostAddress() {
		return hostAddress;
	}

	/**
	 * @param hostAddress the hostAddress to set
	 */
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the version
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}

	/**
	 * @return the roles
	 */
	public Set<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	/**
	 * @return the others
	 */
	public Map<String, Object> getOthers() {
		return others;
	}

	/**
	 * @param others the others to set
	 */
	public void setOthers(Map<String, Object> others) {
		this.others = others;
	}
	
}
