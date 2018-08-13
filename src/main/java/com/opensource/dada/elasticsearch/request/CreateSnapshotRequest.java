/**
 * 
 */
package com.opensource.dada.elasticsearch.request;

import static com.opensource.dada.elasticsearch.common.JsonParserUtils.objectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.elasticsearch.common.settings.Settings;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author dadasaheb patil
 *
 */
public class CreateSnapshotRequest extends AbstractNodeRequest {

	private String repository;
	private String snapshot;
	private Settings settings;
	private Set<String> indices = new LinkedHashSet<>();
	private boolean partial = false;
	private boolean includeGlobalState = true;
	/**
	 * 
	 */
	public CreateSnapshotRequest() {
		super();
	}
	
	/**
	 * @return the repository
	 */
	public String getRepository() {
		return repository;
	}

	/**
	 * @param repository the repository to set
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}
	

	/**
	 * @return the snapshot
	 */
	public String getSnapshot() {
		return snapshot;
	}

	/**
	 * @param snapshot the snapshot to set
	 */
	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
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
	 * @return the partial
	 */
	public boolean isPartial() {
		return partial;
	}

	/**
	 * @param partial the partial to set
	 */
	public void setPartial(boolean partial) {
		this.partial = partial;
	}

	/**
	 * @return the includeGlobalState
	 */
	public boolean isIncludeGlobalState() {
		return includeGlobalState;
	}

	/**
	 * @param includeGlobalState the includeGlobalState to set
	 */
	public void setIncludeGlobalState(boolean includeGlobalState) {
		this.includeGlobalState = includeGlobalState;
	}

	public boolean addIndex(String indexName) {
		return this.indices.add(indexName);
	}
	
	public boolean addIndices(Collection<String>  indices) {
		return this.indices.addAll(indices);
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
		return super.getRequestURI()+"/"+repository+"/"+getSnapshot();
	}

	public CreateSnapshotRequest setWaitForCompletion(boolean waitForCompletion) {
		setParameter("wait_for_completion", waitForCompletion);
		return this;
	}
	
	@Override
	public Object getRequestPayload() {
		ObjectNode rootNode = objectMapper.createObjectNode();
		if(repository!=null && !repository.isEmpty()) {
			rootNode.put("repository",repository);
		}
		if(snapshot!=null && !snapshot.isEmpty()) {
			rootNode.put("snapshot",snapshot);
		}
		
		ArrayNode arrayNode = rootNode.putArray("indices");
		for(String indexName: indices) {
			arrayNode.add(indexName);
		}
		
		try {
			rootNode.set("settings", objectMapper.readTree(settings.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		rootNode.put("partial",partial);
		rootNode.put("include_global_state",includeGlobalState);
		
		return rootNode.toString();
	}
}
