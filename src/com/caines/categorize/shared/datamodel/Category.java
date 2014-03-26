package com.caines.categorize.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import com.caines.categorize.server.SDao;
import com.googlecode.objectify.Key;


public class Category implements Serializable {
	public Category() {
	
	}
	public Category(String name) {
		this.id = name;
		
	}
	@Id
	public String id;
	public String name;
	public double amount;
	
	
	public Key<Category> getKey(){
		return new Key(Category.class,id);
	}
	public static List<String> toString(List<Category> list) {
		List<String> a = new ArrayList();
		for(Category b : list){
			a.add(b.getName());
		}
		return a;
	}
	public String getName() {
		return name;
	}
	
	
}
