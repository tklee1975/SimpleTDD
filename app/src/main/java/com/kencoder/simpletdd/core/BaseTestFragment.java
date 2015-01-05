package com.kencoder.simpletdd.core;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BaseTestFragment extends Fragment implements TestMenuView.TestMenuViewDelegate {
    private static final String TAG = "SimpleTDD.BaseTestFragment";


    private static final int MENU_ITEM_ID_SUBTEST = 1;
	private static final int MENU_ITEM_ID_BACK = 2;

    protected RelativeLayout mLayout = null;
	protected TestMenuView mTestMenu = null;
    protected TextView mStatusText = null;
    private Menu mOptionsMenu;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d("MyTest", "OnCreateView!!!");
		mLayout = new RelativeLayout(this.getActivity());
		// mLayout = (RelativeLayout) inflater.inflate(R.mLayout.test_base_layout, container, false);
		mLayout.setBackgroundColor(Color.LTGRAY);
		
		setHasOptionsMenu(true);

		ActionBar actionBar = this.getActivity().getActionBar();
        if(actionBar != null) {
            actionBar.show();
            actionBar.setTitle(this.getClass().getName());
        }
		
		// Setup menu
		setupMenu();
		
		
		return mLayout;
	}


	@SuppressWarnings("UnusedDeclaration")
    protected void clearViews() {
		if(mLayout == null) {
			Log.d("TDD", "BaseTestFragment.clearViews: mLayout is null");
			return;
		}

		List<View> deleteList = new ArrayList<View>();
		int cnt = mLayout.getChildCount();
		for(int i=0; i<cnt; i++){
			View view = mLayout.getChildAt(i);
			if(view == null || view == mTestMenu) {
				continue; 
			}
			deleteList.add(view);
		}

		for(View view : deleteList){
			if(view == mStatusText) {
				mStatusText = null;
			}
			
			mLayout.removeView(view);
		}
	}
	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}


    protected void clearStatus() {
        if(mStatusText == null) {
            return;
        }

        mStatusText.setText("");
    }


	protected void setStatus(String text)
	{
		if(mLayout == null) {
			Log.d("TDD.BaseTestFragment", "mLayout is null");
			return;
		}
		
		if(mStatusText == null) {
			TextView tv = new TextView(this.getActivity());
			tv.setBackgroundColor(Color.TRANSPARENT);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			
			RelativeLayout.LayoutParams lp = lpWithTopLeft(0, 0,
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			mLayout.addView(tv, 0, lp);
			
			mStatusText = tv;
		}
		
		mStatusText.setText(text);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		setUp();
	}
	
	protected void setUp()
	{
		
	}
	
	protected void tearDown()
	{
		
	}

	@Override
	public void onStop() {
		tearDown();

		super.onStop();
	}	
	
	protected void setMenuList(List<String> testList)
	{
		// TO BE IMPLEMENT
	}
	
	protected void setupMenu()
	{
		List<String> testList = new ArrayList<String>();
		setMenuList(testList);
		
		if(testList.size() == 0) {
			return;
		}
		
		createMenu(testList);
	}
	
	protected void createMenu(List<String> testList)
	{
		TestMenuView menu = new TestMenuView(getActivity(), testList);
		menu.setDelegate(this);
		mTestMenu = menu;
		
		int width = 300;
		int height = testList.size() * 100;
		if(height > 500) {
			height = 500;
		}
		
		// Add to the mLayout
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		// params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		
		
		// Setting the adapater
		
		//listView.se setListAdapter(adapter);

		mLayout.addView(menu, params);
	}
	
	public void toggleMenu() {
		int visible = this.getMenuVisibility();
		if(visible == View.VISIBLE) {
			hideMenu();
		} else {
			showMenu();
		}
	}
	
	public void showMenu() {
		if(mTestMenu == null) {
			Log.d("TDD", "showMenu: mTestMenu is null");
			return;
		}

        setMenuItemName("Hide Menu");

        // Log.d("TDD", "hideMenu: try to showMenu");
		mTestMenu.setVisibility(View.VISIBLE);
		
		if(mLayout != null) {
			mLayout.bringChildToFront(mTestMenu);
		}
	}
	
	public int getMenuVisibility() 
	{
		if(mTestMenu == null) {
			Log.d("TDD", "getMenuVisibility: mTestMenu is null");
			return -1; 
		} 
		
		return mTestMenu.getVisibility();
	}

	public void hideMenu() {
		if(mTestMenu == null) {
			Log.d("TDD", "hideMenu: mTestMenu is null");
			return;
		}
		Log.d("TDD", "hideMenu: try to hideMenu");
        setMenuItemName("Show Menu");
		mTestMenu.setVisibility(View.GONE);
	}

    private void setMenuItemName(String name) {
        if(mOptionsMenu == null) {
            Log.d(TAG, "setMenuItemName: mOptionsMenu is null");
            return;
        }

        MenuItem item = mOptionsMenu.findItem(MENU_ITEM_ID_SUBTEST);
        if(item != null) {
            item.setTitle(name);
        } else {
            Log.d(TAG, "setMenuItemName: menuItem is null");
        }
    }

    protected void invokeMethod(String methodName)
	{
		try {
			Method m = this.getClass().getMethod(methodName);
			if(m == null){ 
				Log.e("TDD", "runTest: method is null. name=" + methodName);
			} else {
				m.invoke(this);		// param of should not be null
			}
		} catch (NoSuchMethodException e) {
			Log.e("TDD", "runTest: Method not found: " + methodName, e);
		} catch (Exception e) {
			Log.e("TDD", "runTest: Exception=" + e + " method=" + methodName, e);
		}		
	}
	
	protected void willRunTest()
	{
		// To be implement
	}

	protected void didRunTest()
	{
		// To be implement
	}

	@Override
	public void runTest(String methodName) {
		willRunTest();
		
		invokeMethod(methodName);
		
		didRunTest();
	}
	

	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Log.d("TDD", "onCreateOptionsMenu");

        mOptionsMenu = menu;
		
		menu.add(Menu.NONE, MENU_ITEM_ID_SUBTEST, 0, "Hide Menu");  // note: default is showing
		menu.add(Menu.NONE, MENU_ITEM_ID_BACK, 0, "Back");
	}

	
	
	protected ActionBar getActionBar()
	{
		return this.getActivity().getActionBar();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("TDD.BaseTestFragment", "onOptionsItemSelected: item=" + item.getItemId());
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
			case MENU_ITEM_ID_SUBTEST:{
				toggleMenu();
				return true;
			}
			
			case android.R.id.home:
			case MENU_ITEM_ID_BACK:
			{
				TestHelper.popTest();
				return true;
			}
		}
		
		
		return super.onOptionsItemSelected(item);
	}

	public RelativeLayout.LayoutParams lpWithTopLeft(int x, int y, int w, int h)
	{
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w, h);
		lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lp.leftMargin = x;
		lp.topMargin = y;
		
		return lp;
	}
	
	public LayoutParams addSubview(View view, int x, int y, int w, int h)
	{
		if(mLayout == null) {
			Log.d("TDD", "addSubView: mLayout is null");
			return null;
		}
		
		LayoutParams lp = lpWithTopLeft(x, y, w, h);
		
		mLayout.addView(view, lp); 	//
		
		return view.getLayoutParams();
	}


    public void toast(String msg)
    {
        TestHelper.toast(this.getActivity(), msg);
    }

}


