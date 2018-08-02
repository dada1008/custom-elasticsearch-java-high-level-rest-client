/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author dadasaheb patil
 *
 */
public abstract class AbstractRequest implements BaseRequest {
	private Object requestPayload;
	private Set<String> cleanApiParameters = new LinkedHashSet<>();
	private Map<String,Object> parameters = new LinkedHashMap<>();

	/**
	 * 
	 */
	public AbstractRequest() {
	}

	public abstract String getIndex();

	public abstract String getType();

	public boolean addCleanApiParameter(String param) {
        return this.cleanApiParameters.add(param);
    }
	
	public boolean removeCleanApiParameter(String param) {
        return this.cleanApiParameters.remove(param);
    }

    public void setParameter(String paramKey, Object paramValue) {
    	this.parameters.put(paramKey, paramValue);
    }
    
    public void removeParameter(String paramKey) {
    	this.parameters.remove(paramKey);
    }
    
	protected String getMiddleURI() {
		return null;
	}

	protected String getRelativeURI() {
		return null;
	}

	@Override
	public String getRequestURI() {
		StringBuilder sb = new StringBuilder();
		
		String indexName = getIndex();
		if (indexName != null && !indexName.isEmpty()) {
			sb.append(indexName);
		}
		
		String middleURI = getMiddleURI();
		if (middleURI != null && !middleURI.isEmpty()) {
			sb.append("/").append(middleURI);
		}

		String typeName = getType();
		if (typeName != null && !typeName.isEmpty()) {
			sb.append("/").append(typeName);
		}

		String relativeURI = getRelativeURI();
		if (relativeURI != null && !relativeURI.isEmpty()) {
			sb.append("/").append(relativeURI);
		}

		if (!parameters.isEmpty() || !cleanApiParameters.isEmpty()) {
			sb.append(buildQueryString());
        }
		
		return sb.toString();
	}

	protected String buildQueryString() {
        StringBuilder queryString = new StringBuilder();

        if (!cleanApiParameters.isEmpty()) {
            queryString.append("/").append(String.join(",",cleanApiParameters));
        }

        queryString.append("?");
        for (Entry<String, Object> entry : parameters.entrySet()) {
            queryString.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue().toString())
                    .append("&");
        }
        // if there are any params  ->  deletes the final ampersand
        // if no params             ->  deletes the question mark
        queryString.deleteCharAt(queryString.length() - 1);

        return queryString.toString();
    }
	
	public Object getRequestPayload() {
		return requestPayload;
	}

	public void setRequestPayload(Object requestPayload) {
		this.requestPayload = requestPayload;
	}

}
