package com.caines.categorize.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caines.categorize.shared.datamodel.JsonCache;
import com.caines.categorize.shared.datamodel.RLink;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class BasicBotService extends HttpServlet {
	static List<String> subreddits = Arrays.asList(new String[]{"changemyview"});
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SDao.getRLinkDao().deleteKeys(SDao.getRLinkDao().getQuery().listKeys());
		try {
			String subreddit = subreddits.get(new Random().nextInt(subreddits.size()));
			String urlpath = "http://www.reddit.com/r/"+subreddit+"/hot/.json";
			JsonElement je = getJSON(urlpath);
			for(JsonElement j : je.getAsJsonObject().get("data").getAsJsonObject().get("children").getAsJsonArray()){
				JsonObject jo = j.getAsJsonObject().get("data").getAsJsonObject();
				String url=jo.get("url").getAsString();
				String text=jo.get("selftext").getAsString();
				String title=jo.get("title").getAsString();
				int score = jo.get("score").getAsInt();
				RLink rl = new RLink(url,title,score,new Date(1000*(long) Double.parseDouble(jo.get("created").getAsString())),text);
				SDao.getRLinkDao().put(rl);
				doComments(rl,jo.get("id").getAsString());
			}
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static JsonElement getJSON(String urlpath)
			throws UnsupportedEncodingException, IOException,
			MalformedURLException, InterruptedException {
		JsonCache jsonc = SDao.getJsonCacheDao().getRN(urlpath);
		JsonElement je;
		if(jsonc != null&&jsonc.isValid()// and is less than 30 minutes old
				){
			je = new JsonParser().parse(jsonc.text);			
		} else {
			JsonReader reader = new JsonReader(new InputStreamReader(new URL(urlpath).openStream(), "UTF-8"));
			je=new JsonParser().parse(reader);
			Thread.sleep(3000);
			SDao.getJsonCacheDao().put(new JsonCache(urlpath,je.toString()));
		}
		
		return je;
	}
	private static void doComments(RLink rl,String id) throws IOException, InterruptedException {
			JsonElement je= getJSON("http://www.reddit.com/comments/"+id+".json?");
			for(JsonElement j : je.getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("children").getAsJsonArray()){
				JsonObject jo = j.getAsJsonObject().get("data").getAsJsonObject();
				String text=jo.get("selftext").getAsString();
				int score = jo.get("score").getAsInt();
				rl.addComment(text);
			}
			SDao.getRLinkDao().put(rl);

	}
}
