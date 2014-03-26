package com.caines.categorize.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;






import javax.persistence.Embedded;
import javax.persistence.Id;

import com.caines.categorize.server.SDao;
import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;


public class JsonCache implements Serializable{
	private Date date;

	public JsonCache() {
	
	}
	public JsonCache(String url,String text) {
		this.url = url;
		this.id = url;
		this.text = text;
		this.date = new Date(new Date().getTime()+1800000);
		System.out.println();
	}
	@Id
	public String id;
	public String url;
	public String text;
	
	public Key<JsonCache> getKey(){
		return new Key(JsonCache.class,id);
	}
	
	public boolean isValid(){
		return date.after(new Date());
	}
}
