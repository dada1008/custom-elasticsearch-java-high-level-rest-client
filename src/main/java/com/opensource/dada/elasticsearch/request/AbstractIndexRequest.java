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
public abstract class AbstractIndexRequest extends AbstractRequest {
	private Set<String> indices = new LinkedHashSet<>();
	private Set<String> types = new LinkedHashSet<>();

	/**
	 * 
	 */
	public AbstractIndexRequest() {
		super();
	}

	public final boolean addIndex(String indexName) {
		return this.indices.add(indexName);
	}
	
	public final boolean removeIndex(String indexName) {
		return this.indices.remove(indexName);
	}

	public final boolean addIndices(Collection<? extends String> indexNames) {
		return this.indices.addAll(indexNames);
	}

	public final String getJoinedIndices() {
		if (this.indices.size() > 0) {
			return String.join(",", indices);
		}

		return "_all";
	}

	public final boolean addType(String typeName) {
		return this.types.add(typeName);
	}
	
	public final boolean removeType(String typeName) {
		return this.types.remove(typeName);
	}

	public final boolean addTypes(Collection<? extends String> typeNames) {
		return this.types.addAll(typeNames);
	}

	public final String getJoinedTypes() {
		if (this.types.size() > 0) {
			return String.join(",", types);
		}

		return null;
	}
	
	@Override
	public String getIndex() {
		return getJoinedIndices();
	}
	
	@Override
	public String getType() {
		return getJoinedTypes();
	}
}
