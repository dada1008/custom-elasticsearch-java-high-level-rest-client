/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

/**
 * @author dapatil
 *
 */
public class GetMappingsRequest extends org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest implements BaseRequest{

	/**
	 * 
	 */
	public GetMappingsRequest() {
	}

	@Override
	public String getRequestMethod() {
		return "GET";
	}

	@Override
	public String getRequestURI() {
		StringBuilder sb = new StringBuilder(getJoinedIndices());
		sb.append("/").append("_mapping");
		String type = getJoinedTypes();
		if (type != null && !type.isEmpty()) {
			sb.append("/").append(type);
		}		
		return sb.toString();
	}
	
	public final String getJoinedIndices() {
		String[] indices = this.indices();
		if (indices!=null && indices.length > 0) {
			return String.join(",", indices);
		}

		return "_all";
	}
	
	public final String getJoinedTypes() {
		String[] types = this.types();
		if (types!=null && types.length > 0) {
			return String.join(",", types);
		}

		return null;
	}

}
