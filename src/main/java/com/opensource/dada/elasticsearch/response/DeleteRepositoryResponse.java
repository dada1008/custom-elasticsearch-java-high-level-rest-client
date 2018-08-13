/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

/**
 * @author dadasaheb patil
 *
 */
public class DeleteRepositoryResponse extends AbstractResponse {
	private boolean acknowledged;

	/**
	 * @return the acknowledged
	 */
	public boolean isAcknowledged() {
		return acknowledged;
	}

	/**
	 * @param acknowledged the acknowledged to set
	 */
	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}


}
