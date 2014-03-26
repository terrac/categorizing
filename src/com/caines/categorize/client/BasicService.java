package com.caines.categorize.client;

import java.util.List;

import com.caines.categorize.shared.datamodel.RLink;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("basic")
public interface BasicService extends RemoteService {
	List<String> categoriesServer(String name) throws IllegalArgumentException;
	void sendServer(String link,String categories) throws IllegalArgumentException;
	void addCategory(String category);
	List<RLink> getRlinks();
}
