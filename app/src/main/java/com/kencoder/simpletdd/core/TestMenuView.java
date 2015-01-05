package com.kencoder.simpletdd.core;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TestMenuView extends ListView {
	public interface TestMenuViewDelegate
	{
		public void runTest(String testMethodName);
	}
	
	protected List<String> testList = new ArrayList<String>();
	protected ArrayAdapter<String> adapter = null;
	protected TestMenuViewDelegate delegate = null;
	
	public TestMenuView(Context context, List<String> testArray) {
		super(context);

		int layoutID = android.R.layout.simple_list_item_1;		
		adapter = new ArrayAdapter<String>(context, layoutID, testArray);
		setAdapter(adapter);
		
		testList.clear();
		testList.addAll(testArray);
		
		
		setBackgroundColor(0xAAFFFFFF);
		
		// 
		this.setOnItemClickListener(createOnItemClickListener());
	}

	private OnItemClickListener createOnItemClickListener() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String name = testList.get(position);
				if(delegate != null) {
					try{
						delegate.runTest(name);
					}catch(Exception e) {
						Log.d("TDD", "exception", e);
						e.printStackTrace();
					}
				}
			}
			
		};
	}

	public void setDelegate(TestMenuViewDelegate _delegate) {
		delegate = _delegate;
	}

}
