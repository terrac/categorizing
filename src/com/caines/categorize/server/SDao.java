package com.caines.categorize.server;

import com.caines.categorize.shared.datamodel.Category;
import com.caines.categorize.shared.datamodel.JsonCache;
import com.caines.categorize.shared.datamodel.RLink;


public class SDao {
	public static Dao<Category> getCategoryDao() {
		return new Dao<Category>(Category.class);
	}
	
	public static Dao<RLink> getRLinkDao() {
		return new Dao<RLink>(RLink.class);
	}

	public static Dao<JsonCache> getJsonCacheDao() {
		return new Dao<JsonCache>(JsonCache.class);
	}

}
