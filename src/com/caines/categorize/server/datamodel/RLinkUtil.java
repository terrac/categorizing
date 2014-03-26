package com.caines.categorize.server.datamodel;

import com.caines.categorize.shared.datamodel.Category;
import com.caines.categorize.shared.datamodel.RLink;

public class RLinkUtil {
	public void addCategory(RLink rl,String category){
		rl.categories.add(CategoryUtil.getCategory(category));
		
	}
	public void addCategories(RLink rl,String categories2) {
		for(String a : categories2.split(",")){
			addCategory(rl,a);
		}
	}
}
