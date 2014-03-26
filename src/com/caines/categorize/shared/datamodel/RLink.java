package com.caines.categorize.shared.datamodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;





import javax.persistence.Embedded;
import javax.persistence.Id;

import com.caines.categorize.server.SDao;
import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;


public class RLink implements Serializable{
	public RLink() {
	
	}
	public RLink(String url,String text, int score,Date created,String selftext) {
		this.url = url;
		this.text = text;
		this.score = score;
		this.created = created;
		this.selftext = selftext;
	}
	@Id
	public Long id;
	public String url;
	public String text;
	public String selftext;
	public int score;
	public Date created;
	List<String> comments = new ArrayList<String>();
	public List<Category> categories = new ArrayList<Category>();
	
	public Key<RLink> getKey(){
		return new Key(RLink.class,id);
	}
	
	public void addComment(String comment){
		comments.add(comment);
	}
	public List<String> getComments() {
		return comments;
	}
	
}
