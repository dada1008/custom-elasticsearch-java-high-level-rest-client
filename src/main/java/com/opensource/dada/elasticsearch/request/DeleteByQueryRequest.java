/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

/**
 * @author dadasaheb patil
 *
 */
public class DeleteByQueryRequest extends AbstractIndexRequest {

	/**
	 * 
	 */
	public DeleteByQueryRequest() {
		super();
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public final String getRelativeURI() {
		return "_delete_by_query";
	}

}
