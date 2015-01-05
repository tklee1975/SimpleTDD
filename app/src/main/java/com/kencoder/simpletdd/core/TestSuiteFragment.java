package com.kencoder.simpletdd.core;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class TestSuiteFragment extends ListFragment {
    private static final String TAG = "SimpleTDD.TestSuiteFragment";
    private List<String> testList = new ArrayList<String>();
	private List<String> filterList = new ArrayList<String>();
	private SearchView searchView = null;
	private ArrayAdapter<String> listAdapter = null;
	private TextView headerView = null;
	private BaseTestFragment mCurrentFragment;
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	
		String name = filterList.get(position);
		Log.d("MyTest", "position=" + position + " id=" + id + " name=" + name);
		showTest(name);
	}
	
	protected void setupTest(List<String> testList)
	{
		String[] tests = getTestName();
		
		for(String test : tests) {
			testList.add(test);
		}
	}
	
	protected void setFilterInfo(String name)
	{
		String info;
		if(name.length() == 0) {
			info = "All Test";
		} else {
			info = "Filter '" + name + "'";
		}
		
		info += "; count: " + filterList.size();
		
		if(headerView != null) {
			headerView.setText(info);
		}
		
	}
	
	protected BaseTestFragment getTestByName(String name)
	{
		String className = getPackageName() + "." + name;
	    
		return (BaseTestFragment) TestHelper.getFragmentByClassname(className);
	}
	
	public void showTest(String name)
	{
		BaseTestFragment testFrag = getTestByName(name);
		if(testFrag == null) {
			Log.e("MyTest", "Fail to get the instance. name=" + name);
			return;
		}
		
		TestHelper.showTest(testFrag);
		mCurrentFragment = testFrag;
	}
	
	protected void setupSearchView()
	{
		searchView = new SearchView(this.getActivity());
		searchView.setIconifiedByDefault(true);
		
		
		OnQueryTextListener listener = new OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String query) {
				//Log.d("TDD", "onQueryTextSubmit: query=" + query);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				//Log.d("TDD", "onQueryTextChange: query=" + newText);
				filterTestByKeyword(newText);
				return false;
			}
			
		};
		
		searchView.setOnQueryTextListener(listener);
	}
	
	
	protected void filterTestByKeyword(String keyword) {
		keyword = keyword.trim().toLowerCase();
		
		TestHelper.saveFilter(getActivity(), keyword);
		
		// Filtering 
		filterList.clear();
		for(String test : testList) {
			String testStr = test.replaceAll("Test$", "").toLowerCase();
			// Log.d("TDD", "filterTestByKeyword: testStr=" + testStr);
			
			// Filter IN:
			//		1. keyword is undefined
			//		2. testname match keyword
			if(keyword.length() == 0 || testStr.indexOf(keyword) >= 0) {
				filterList.add(test);
			}
		}
		
		// Log.d("TDD", "FilterList: " + filterList);

		// note: listAdapter is linked to filterList
		this.listAdapter.notifyDataSetChanged();
		
		// Log.d("TDD", "listAdapter: " + listAdapter.getCount());
		
		setFilterInfo(keyword);
	}
	
	
	protected void searchSearchViewText(String text) {
		if(this.searchView == null) {
			return;
		}
		
		this.searchView.setQuery(text, false);
	}
	
	protected void setupFilter() 
	{
		String text = TestHelper.getFilter();
		
		this.searchSearchViewText(text);
        this.filterTestByKeyword(text);
	}
	
	protected void setupActionBar()
	{
		ActionBar actionBar = this.getActivity().getActionBar();
        if(actionBar == null) {
            Log.e(TAG, "setupActionBar: actionBar is null");
            return;
        }
        actionBar.setTitle("Test List");
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);

	}
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "OnCreate");
		
		setHasOptionsMenu(true);
		// this.set
		// TestHelper.setSupportDelegate(getSupportDelegate());
		
		setupSearchView();
		
        
        // Setup ActionBar
        setupActionBar();
	}
	
	protected void setupListAdapter()
	{
		testList.clear();
		setupTest(testList);
		
		filterList.clear();
		filterList.addAll(testList);
		

        Log.d(TAG, "setupListAdapter: filterList=" + filterList);
		
		int layout = android.R.layout.simple_list_item_1;
		listAdapter = new ArrayAdapter<String>(getActivity(), layout, filterList);
	}
	
	protected void setupHeaderView()
	{
		headerView = new TextView(this.getActivity());
		headerView.setGravity(Gravity.CENTER);
		setFilterInfo("");
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Log.d("TDD", "TestSuiteFragment: onOptionsItemSelected. item=" + item);
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        {
	        	//Log.d("TDD", "TestSuiteFragment: home");
	            this.getActivity().finish();
	            return true;
	        }
	        default:
	        {
	        	if("search".equalsIgnoreCase(item.getTitle().toString())) {
	        		// Log.d("TDD", "Search Clicked");
	        		// searchView.setVisibility(View.VISIBLE);
	        		if(searchView != null) {
	        			searchView.setIconified(false);
	        		} else {
	        			//Log.d("TDD", "searchView is null");
	        		}
	        		
	        		return true;
	        	}
	        	
	            return super.onOptionsItemSelected(item);
	        }
	    }
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d("TDD", "TestSuiteFragment.onPause()");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("TDD", "TestSuiteFragment.onResume()");
		
		setupActionBar();
		setupFilter();
	}

	protected TestSupportDelegate getSupportDelegate()
	{
		return null;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		Log.d(TAG, "onCreateView: listAdapter=" + listAdapter);
		view.setBackgroundColor(Color.LTGRAY);

		// Setup the adapter if it is not yet setup!
		if(listAdapter == null) {
			setupListAdapter();
			setListAdapter(listAdapter);
		}


        return view;
	}
	
	

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
	}

	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d("TDD", "TestSuiteFragment.onAttach()");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d("TDD", "TestSuiteFragment.onDetach()");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		TestHelper.loadFilter(this.getActivity());
	}

	
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		Log.d("TDD", "onCreateOptionsMenu");
		
		MenuItem menuItem = menu.add("search");
		menuItem.setIcon(android.R.drawable.ic_search_category_default);
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuItem.setActionView(searchView);
		
		
		///searchView  = (SearchView) MenuItemCompat.getActionView(menuItem);
		//searchView.setIconified(false);
	}
	
	

	protected String[] getTestName() {
		String[] tests = {
				"ATest",
				"ATest",
				"ATest",
			};
		
		return tests;
	}
	
	protected String getPackageName()
	{
		String packageName = this.getClass().getPackage().getName();
		
		return packageName;
	}
	
	@Override
	public String toString() {
		// 
		return super.toString() + " class=TestSuiteFragment";
	}

	public BaseTestFragment getCurrentTestFragment() {
		return mCurrentFragment;
	}
}
