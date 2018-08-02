/**
 * 
 */
package com.opensource.dada.elasticsearch.response;

/**
 * @author dadasaheb patil
 *
 */
public interface BaseResponse {
	int getResponseCode();
	String getError();
	String getResponseJson();
}
