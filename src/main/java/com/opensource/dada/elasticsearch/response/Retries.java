/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

/**
 * @author dadasaheb patil
 *
 */
public class Retries {
	private int bulk;
	private int search;

	// Getter Methods

	public int getBulk() {
		return bulk;
	}

	public int getSearch() {
		return search;
	}

	public void setBulk(int bulk) {
		this.bulk = bulk;
	}

	public void setSearch(int search) {
		this.search = search;
	}
}
