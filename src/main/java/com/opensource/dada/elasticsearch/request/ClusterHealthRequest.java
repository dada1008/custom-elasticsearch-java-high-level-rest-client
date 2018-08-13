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
		if(timeout!=null) {
    		setParameter("timeout", timeout.getMillis()+"ms");
    	}
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

    public ClusterHealthRequest level(Level level) {
    	if(level!=null) {
    		setParameter("level", level.getId());
    	}
        return this;
    }
    
    public ClusterHealthRequest setLevelToIndices() {
    	return level(Level.INDICES);
    }
    
    public ClusterHealthRequest setLevelToShards() {
    	return level(Level.SHARDS);
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

	
	public enum Level {
        CLUSTER("cluster"), INDICES("indices"), SHARDS("shards");

        private final String id;

        Level(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
