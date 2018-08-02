/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

/**
 * @author dadasaheb patil
 *
 */
public abstract class AbstractNodeRequest extends AbstractRequest {
	/**
	 * 
	 */
	public AbstractNodeRequest() {
		super();
	}

	@Override
	public String getIndex() {
		return null;
	}
	
	@Override
	public String getType() {
		return null;
	}
}
