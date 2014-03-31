package com.caines.categorize.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.caines.categorize.client.BasicService;
import com.caines.categorize.shared.datamodel.Category;
import com.caines.categorize.shared.datamodel.RLink;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Query;

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
	public List<RLink> getRlinks(Date from, Date to,Integer minScore,Double scorePercent) {
		Query<RLink> q=SDao.getRLinkDao().getQuery();
				if(from != null){
				q.filter("date >", from).filter("date <", to);	
				}
				if(minScore != null){
					q.filter("score > ", minScore);
				}
				if(scorePercent != null){
					//q.filter("scorePercent >", scorePercent);
				}
				q.limit(50);
		if(q.list().size() < 6){
			return q.list();
		}
		return new ArrayList<>(q.list().subList(0, 5));
	}

	@Override
	public List<RLink> getRlinks(String name) {
		return getRlinks(null, null, null, null);
	}
	
	
}
