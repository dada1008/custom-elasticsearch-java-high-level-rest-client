/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

/**
 * @author dadasaheb patil
 *
 */
public abstract class AbstractResponse implements BaseResponse {

	private int responseCode;
	private String error;
	private String responseJson;
	
	/**
	 * 
	 */
	public AbstractResponse() {
	}

	/**
	 * @return the responseCode
	 */
	@Override
	public final int getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public final void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the errorMessage
	 */
	@Override
	public final String getError() {
		return error;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public final void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the responseJson
	 */
	@Override
	public final String getResponseJson() {
		return responseJson;
	}

	/**
	 * @param responseJson the responseJson to set
	 */
	public final void setResponseJson(String responseJson) {
		this.responseJson = responseJson;
	}

	public final boolean isRequestSuccessful() {
		return this.responseCode==200;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + responseCode;
		result = prime * result + ((responseJson == null) ? 0 : responseJson.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractResponse other = (AbstractResponse) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (responseCode != other.responseCode)
			return false;
		if (responseJson == null) {
			if (other.responseJson != null)
				return false;
		} else if (!responseJson.equals(other.responseJson))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" [responseCode=" + responseCode + ", error=" + error + ", responseJson="
				+ responseJson + "]";
	}
	
}
