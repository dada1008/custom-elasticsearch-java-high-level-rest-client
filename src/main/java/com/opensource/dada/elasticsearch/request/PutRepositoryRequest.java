/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

import static org.elasticsearch.common.settings.Settings.Builder.EMPTY_SETTINGS;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.common.settings.Settings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static com.opensource.dada.elasticsearch.common.JsonParserUtils.objectMapper;

/**
 * @author dadasaheb patil
 *
 */
public class PutRepositoryRequest extends AbstractNodeRequest {

	private String name;

    private String type;

    private boolean verify = true;
    
	private Settings settings = EMPTY_SETTINGS;
	
	/**
	 * 
	 */
	public PutRepositoryRequest() {
		super();
	}

	@Override
	public String getRequestMethod() {
		return "PUT";
	}

	@Override
	public final String getRelativeURI() {
		return "_snapshot";
	}

	@Override
	public String getRequestURI() {
		return super.getRequestURI()+"/"+name;
	}
	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the verify
	 */
	public boolean isVerify() {
		return verify;
	}

	/**
	 * @param verify the verify to set
	 */
	public void setVerify(boolean verify) {
		this.verify = verify;
	}
	
	@Override
	public Object getRequestPayload() {
		ObjectNode rootNode = objectMapper.createObjectNode();
		if(name!=null && !name.isEmpty()) {
			rootNode.put("name",name);
		}
		if(type!=null && !type.isEmpty()) {
			rootNode.put("type",type);
		}
		
		try {
			rootNode.set("settings", objectMapper.readTree(settings.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		rootNode.put("verify",verify);
		
		return rootNode.toString();
	}
	
}
