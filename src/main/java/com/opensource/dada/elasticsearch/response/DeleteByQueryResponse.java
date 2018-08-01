/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dadasaheb patil
 *
 */
public class DeleteByQueryResponse extends AbstractResponse {

	private int took;
	private boolean timed_out;
	private int total;
	private int deleted;
	private int batches;
	private int version_conflicts;
	private int noops;
	Retries retries;
	private int throttled_millis;
	private int requests_per_second;
	private int throttled_until_millis;
	List<Object> failures = new ArrayList<Object>();

	/**
	 * 
	 */
	public DeleteByQueryResponse() {
		super();
	}

	public int getTook() {
		return took;
	}

	public boolean getTimed_out() {
		return timed_out;
	}

	public int getTotal() {
		return total;
	}

	public int getDeleted() {
		return deleted;
	}

	public int getBatches() {
		return batches;
	}

	public int getVersion_conflicts() {
		return version_conflicts;
	}

	public int getNoops() {
		return noops;
	}

	public Retries getRetries() {
		return retries;
	}

	public int getThrottled_millis() {
		return throttled_millis;
	}

	public int getRequests_per_second() {
		return requests_per_second;
	}

	public int getThrottled_until_millis() {
		return throttled_until_millis;
	}

	public void setTook(int took) {
		this.took = took;
	}

	public void setTimed_out(boolean timed_out) {
		this.timed_out = timed_out;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public void setBatches(int batches) {
		this.batches = batches;
	}

	public void setVersion_conflicts(int version_conflicts) {
		this.version_conflicts = version_conflicts;
	}

	public void setNoops(int noops) {
		this.noops = noops;
	}

	public void setRetries(Retries retries) {
		this.retries = retries;
	}

	public void setThrottled_millis(int throttled_millis) {
		this.throttled_millis = throttled_millis;
	}

	public void setRequests_per_second(int requests_per_second) {
		this.requests_per_second = requests_per_second;
	}

	public void setThrottled_until_millis(int throttled_until_millis) {
		this.throttled_until_millis = throttled_until_millis;
	}
}
