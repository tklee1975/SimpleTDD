package com.kencoder.simpletdd.core;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

/**
 * The main activity of the test framework 
 * 
 * @author kenlee
 *
 */
public class TestSuiteActivity extends ActionBarActivity implements TestSupportDelegate {
	public static final int CONTENT_VIEW_ID = 99998888;
	
	protected BaseTestFragment mCurrentFragment = null;

	private TestSuiteFragment mMainFrag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FrameLayout frame = new FrameLayout(this);
		frame.setId(CONTENT_VIEW_ID);
		setContentView(frame, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		TestHelper.setSupportDelegate(this);
		
		Log.d("TDD", "TestSuiteActivity.onCreate: state=" + savedInstanceState);
		
		if(savedInstanceState == null) {
		    TestSuiteFragment frag = getMainTestFragment();
		    
		    if(frag == null) {
		    	Log.d("TDDandroid", "Fail to create the TestSuiteFragment");
		    } else { 
		    	addFragment(frag, false);
		    }
		    mMainFrag = frag;
		}
	}
	
	public TestSuiteFragment getMainTestFragment()
	{
		Class mainClass = TestHelper.getMainTestFragment();
		
		if(mainClass == null) {
			Log.d("TDDandroid", "MainTestFragment undefined");
			return null;
		}
		
		Fragment frag = TestHelper.getFragmentByClass(mainClass);
		
		return (TestSuiteFragment) frag;
	}
	
	public void addFragment(Fragment fragment, boolean enableBack)
	{
		FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
	
		trans.replace(CONTENT_VIEW_ID, fragment);
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		if(enableBack) {
			trans.addToBackStack(null);
		}
		
		trans.commit();
	}

	@Override
	public void showFragment(Fragment newTest) {
		// 
		addFragment(newTest, true);
	}

	@Override
	public void popFragment() {
		//getSupportFragmentManager().popBackStack();
		getSupportFragmentManager().popBackStackImmediate();
	}
	
	public Fragment getVisibleFragment(){
	    FragmentManager fragmentManager = this.getSupportFragmentManager();
	    
	    List<Fragment> fragments = fragmentManager.getFragments();
	    int count = fragments.size(); 
	    
	    // Log.d("TDD", "getVisibleFragment: count=" + count);
	    for(int i=count-1; i>=0; i--){
	    	Fragment fragment = fragments.get(i);
	    	if(fragment == null) {
	    		continue;
	    	}
	    	
	    	// Log.d("TDD", "i=" + i + " Fragment=" + fragment);
	        if(fragment != null && fragment.getUserVisibleHint()) {
	            return fragment;
	        }
	    }
	    return null;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Fragment currentFrag = getVisibleFragment();
		
		Log.d("TDD", "TestSuiteActivity: onOptionsItemSelected. currentFrag=" + currentFrag);
		if(currentFrag == null) {
			Log.d("TDD:TestSuiteActivity", "onOptionsItemSelected: currentFrag is null");
			return super.onOptionsItemSelected(item);
		}
		
		return currentFrag.onOptionsItemSelected(item);
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		// TODO 
		super.onAttachFragment(fragment);
		Log.d("TDD", "TestSuiteActivity.onAttachFragment. fragment=" + fragment);
	}

	@Override
	protected void onDestroy() {
		// 
		super.onDestroy();
		Log.d("TDD", "TestSuiteActivity.onDestroy");
	}

	@Override
	protected void onPause() {
		// 
		super.onPause();
		Log.d("TDD", "TestSuiteActivity.onPause");
	}

	@Override
	protected void onResume() {
		// 
		super.onResume();
		Log.d("TDD", "TestSuiteActivity.onResume");
	}

	@Override
	protected void onResumeFragments() {
		// 
		super.onResumeFragments();
		Log.d("TDD", "TestSuiteActivity.onResumeFragments");
	}

	@Override
	protected void onStart() {
		// 
		super.onStart();
		Log.d("TDD", "TestSuiteActivity.onStart");
	}

	@Override
	protected void onStop() {
		// 
		super.onStop();
		Log.d("TDD", "TestSuiteActivity.onStop");
	}



	
	
}
