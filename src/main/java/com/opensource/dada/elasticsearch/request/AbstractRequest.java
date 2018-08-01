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
public abstract class AbstractRequest implements BaseRequest {
	protected Set<String> indices = new LinkedHashSet<String>();
	protected Set<String> types = new LinkedHashSet<String>();
	protected Object requestPayload;

	/**
	 * 
	 */
	public AbstractRequest() {
	}

	public final boolean addIndex(String indexName) {
		return this.indices.add(indexName);
	}

	public final boolean addIndices(Collection<? extends String> indexNames) {
		return this.indices.addAll(indexNames);
	}

	public final String getJoinedIndices() {
		if (indices.size() > 0) {
			return String.join(",", indices);
		}
		
		return "_all";
	}

	public final boolean addType(String typeName) {
		return this.types.add(typeName);
	}

	public final boolean addTypes(Collection<? extends String> typeNames) {
		return this.types.addAll(typeNames);
	}

	public final String getJoinedTypes() {
		if (types.size() > 0) {
			return String.join(",", types);
		}

		return null;
	}

	protected String getMiddleURI() {
		return null;
	}

	protected String getRelativeURI() {
		return null;
	}

	@Override
	public final String getRequestURI() {
		StringBuilder sb = new StringBuilder();
		String indexName = getJoinedIndices();
		if (indexName != null && !indexName.isEmpty()) {
			sb.append(indexName);

			String middleURI = getMiddleURI();

			if (middleURI != null && !middleURI.isEmpty()) {
				sb.append("/").append(middleURI);
			}

			String typeName = getJoinedTypes();
			if (typeName != null && !typeName.isEmpty()) {
				sb.append("/").append(typeName);
			}

			String relativeURI = getRelativeURI();

			if (relativeURI != null && !relativeURI.isEmpty()) {
				sb.append("/").append(relativeURI);
			}
		}

		return sb.toString();
	}

	public Object getRequestPayload() {
		return requestPayload;
	}

	public void setRequestPayload(Object requestPayload) {
		this.requestPayload = requestPayload;
	}
	
}
