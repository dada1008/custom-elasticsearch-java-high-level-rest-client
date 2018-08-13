/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import java.util.Set;

/**
 * @author dadasaheb patil
 *
 */
public class MappingData {

	private String alias;

    private Object filter;

    private String indexRouting;

    private String searchRouting;

    private Set<String> searchRoutingValues;
    
	/**
	 * 
	 */
	public MappingData() {
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the filter
	 */
	public Object getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(Object filter) {
		this.filter = filter;
	}

	/**
	 * @return the indexRouting
	 */
	public String getIndexRouting() {
		return indexRouting;
	}

	/**
	 * @param indexRouting the indexRouting to set
	 */
	public void setIndexRouting(String indexRouting) {
		this.indexRouting = indexRouting;
	}

	/**
	 * @return the searchRouting
	 */
	public String getSearchRouting() {
		return searchRouting;
	}

	/**
	 * @param searchRouting the searchRouting to set
	 */
	public void setSearchRouting(String searchRouting) {
		this.searchRouting = searchRouting;
	}

	/**
	 * @return the searchRoutingValues
	 */
	public Set<String> getSearchRoutingValues() {
		return searchRoutingValues;
	}

	/**
	 * @param searchRoutingValues the searchRoutingValues to set
	 */
	public void setSearchRoutingValues(Set<String> searchRoutingValues) {
		this.searchRoutingValues = searchRoutingValues;
	}

}
