package com.caines.categorize.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.caines.categorize.server.SDao;
import com.caines.categorize.shared.FieldVerifier;
import com.caines.categorize.shared.datamodel.RLink;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Categorizing implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final BasicServiceAsync greetingService = GWT
			.create(BasicService.class);
	Map<String,RLink> rlinkMap = new HashMap<String, RLink>();
	Map<String,Hyperlink> hyperlinkMap = new HashMap<>();
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		if(true){
			new SimpleFront().show();
		} else {
			showTabs();
		}
	    
	
	}

	public void showSubmit() {
		final HorizontalPanel buttons = new HorizontalPanel();
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(buttons);
		//async call to build buttons
			greetingService.categoriesServer("", new AsyncCallback<List<String>>() {
				
				@Override
				public void onSuccess(List<String> result) {
					for(String cat : result){
						Button but = new Button(cat);
						but.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								greetingService.addCategory("blah", new AsyncCallback<Void>() {
									
									@Override
									public void onSuccess(Void result) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										
									}
								});
							}
						});
						buttons.add(but);
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		{
			HorizontalPanel hp = new HorizontalPanel();
			Label l = new Label("Categories");
			hp.add(l);
			//hp.add(categories);
			vp.add(hp);
		}
		
		
		
		RootPanel.get("nameFieldContainer").add(vp);
	}

	public Widget showTree() {
		TreeItem root = new TreeItem();
	    root.setText("root");
	    root.addTextItem("item0");
	    root.addTextItem("item1");
	    root.addTextItem("item2");

	    // Add a CheckBox to the tree
	    TreeItem item = new TreeItem(new CheckBox("item3"));
	    root.addItem(item);

	    Tree t = new Tree();
	    t.addItem(root);

	    return t;
	}
	public void showTabs(){

		TabPanel panel = new TabPanel();
		Panel flowpanel;

		flowpanel = new FlowPanel();
		flowpanel.add(new Label("R"));
		panel.add(flowpanel, "Reduce Reddit");
		
		flowpanel = new HorizontalPanel();
		flowpanel.add(new Label("From"));
		final DatePicker fromDP = new DatePicker();
		final DatePicker todp = new DatePicker();
		Date currentDate = new Date();
		CalendarUtil.addDaysToDate( currentDate, -7 );
		todp.setValue(currentDate);
		flowpanel.add(todp);
		flowpanel.add(new Label("To"));
		flowpanel.add(fromDP);
		panel.add(flowpanel, "Date");

		panel.add(showTree(), "Category");

		flowpanel = new FlowPanel();
		flowpanel.add(new Label(""));
		panel.add(flowpanel, "Subreddit");

		flowpanel = new FlowPanel();
		flowpanel.add(new Label(""));
		panel.add(flowpanel, "Score");

		panel.selectTab(0);

		//panel.setSize("500px", "250px");
		panel.addStyleName("table-center");
		
		RootPanel.get().add(panel);
		final VerticalPanel vp=new VerticalPanel();
		greetingService.getRlinks(fromDP.getValue(),todp.getValue(),5,.2,new AsyncCallback<List<RLink>>() {
			
			@Override
			public void onSuccess(List<RLink> result) {
				// TODO Auto-generated method stub
				for(RLink rl :result){
					Hyperlink link = new Hyperlink(rl.text, rl.url);
					rlinkMap.put(rl.url, rl);
					hyperlinkMap.put(rl.url, link);
					vp.add(link);	
				}		
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
		vp.add(new Button("refresh"));

		RootPanel.get().add(vp);
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				Hyperlink hy=hyperlinkMap.get(event.getValue());
				hy.removeFromParent();
				addPage(rlinkMap.get(event.getValue()));
			}
		});
		
		
	}

	TabPanel page;
	public void addPage(RLink rl) {
		Panel flowpanel;
		if(page == null){
			page =  new TabPanel();
			RootPanel.get().add(page);
		}
		flowpanel = new VerticalPanel();
		flowpanel.getElement().setAttribute("cellpadding", "5");
		page.insert(flowpanel, page.getTabBar().getTabCount()+"",0);
		page.selectTab(0);
		SimpleFront.displayPage(rl, flowpanel);

		
	}
}
