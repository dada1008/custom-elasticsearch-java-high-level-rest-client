/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

/**
 * @author dadasaheb patil
 *
 */
public class GetNodesInfoRequest extends AbstractNodeRequest {

	/**
	 * 
	 */
	public GetNodesInfoRequest() {
		super();
	}

	@Override
	public String getRequestMethod() {
		return "GET";
	}

	@Override
	public final String getRelativeURI() {
		return "_nodes";
	}
	
	public boolean includeSettings() {
        return addCleanApiParameter("settings");
    }

    public boolean includeOs() {
        return addCleanApiParameter("os");
    }

    public boolean includeProcess() {
        return addCleanApiParameter("process");
    }

    public boolean includeJvm() {
        return addCleanApiParameter("jvm");
    }

    public boolean includeThreadPool() {
        return addCleanApiParameter("thread_pool");
    }

    public boolean includeNetwork() {
        return addCleanApiParameter("network");
    }

    public boolean includeTransport() {
        return addCleanApiParameter("transport");
    }

    public boolean includeHttp() {
        return addCleanApiParameter("http");
    }

    public boolean includePlugins() {
        return addCleanApiParameter("plugins");
    }
}
