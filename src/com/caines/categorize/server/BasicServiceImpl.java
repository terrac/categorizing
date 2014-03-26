package com.caines.categorize.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.caines.categorize.client.BasicService;
import com.caines.categorize.shared.datamodel.Category;
import com.caines.categorize.shared.datamodel.RLink;
import com.google.gson.stream.JsonReader;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class BasicServiceImpl extends RemoteServiceServlet implements
		BasicService {

	@Override
	public List<String> categoriesServer(String name) throws IllegalArgumentException {
		//get top 7 from categories based on highest points
		//get 3 random categories and mix in.
		
		return Category.toString(SDao.getCategoryDao().getQuery().limit(7).filter("amount >", "-1").list());
	}

	@Override
	public void sendServer(String link, String categories)
			throws IllegalArgumentException {
		//create rlink, add categories
		//RLink rlink = new RLink(link,"",0,null);
		//rlink.addCategories(categories);
		//SDao.getRLinkDao().put(rlink);
	}

	@Override
	public void addCategory(String category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RLink> getRlinks() {
		List<RLink> rlist=SDao.getRLinkDao().getQuery()
				//.filter("date >", new Date())
				.limit(5).list();
		return rlist;
	}
	
	
}
