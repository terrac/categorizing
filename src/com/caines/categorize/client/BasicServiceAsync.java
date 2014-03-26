package com.caines.categorize.client;

import java.util.List;

import com.caines.categorize.shared.datamodel.RLink;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BasicServiceAsync {

	void categoriesServer(String name, AsyncCallback<List<String>> callback);

	void sendServer(String link, String categories, AsyncCallback<Void> callback);

	void addCategory(String category,AsyncCallback<Void> callback);

	void getRlinks(AsyncCallback<List<RLink>> callback);

}
