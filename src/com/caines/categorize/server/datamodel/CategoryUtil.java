package com.caines.categorize.server.datamodel;

import com.caines.categorize.server.SDao;
import com.caines.categorize.shared.datamodel.Category;

public class CategoryUtil {
	public static Category getCategory(String category) {
		Category cat = SDao.getCategoryDao().getRN(category);
		if(cat == null){
			cat = new Category(category);
			SDao.getCategoryDao().put(cat);
		}
		cat.amount++;
		SDao.getCategoryDao().put(cat);
		
		return cat;
	}
}
