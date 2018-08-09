/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.unit.TimeValue;

/**
 * @author dadasaheb patil
 *
 */
public class ClusterHealthRequest extends AbstractIndexRequest {
	
	/**
	 * 
	 */
	public ClusterHealthRequest() {
		super();
	}

	@Override
	public String getRequestMethod() {
		return "GET";
	}

	@Override
	public final String getRelativeURI() {
		return "_cluster/health";
	}
	
	public ClusterHealthRequest timeout(TimeValue timeout) {
		setParameter("timeout", timeout.getMillis()+"ms");
        return this;
    }

    public ClusterHealthRequest waitForStatus(ClusterHealthStatus waitForStatus) {
        setParameter("wait_for_status", waitForStatus.name());
        return this;
    }

    public ClusterHealthRequest waitForGreenStatus() {
        return waitForStatus(ClusterHealthStatus.GREEN);
    }

    public ClusterHealthRequest waitForYellowStatus() {
        return waitForStatus(ClusterHealthStatus.YELLOW);
    }

	@Override
	public final String getRequestURI() {
		StringBuilder sb = new StringBuilder(getRelativeURI());
		String indexName = getIndex();
		if (indexName != null && !indexName.isEmpty()) {
			sb.append("/").append(indexName);
		}
		if (!parameters.isEmpty() || !cleanApiParameters.isEmpty()) {
			sb.append(buildQueryString());
        }
		return sb.toString();
	}

}
