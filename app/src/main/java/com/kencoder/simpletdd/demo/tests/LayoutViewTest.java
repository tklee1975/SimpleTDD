package com.kencoder.simpletdd.demo.tests;

import android.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kencoder.simpletdd.core.BaseTestFragment;
import com.kencoder.simpletdd.core.TestHelper;

import java.util.List;
import com.kencoder.simpletdd.R;


public class LayoutViewTest extends BaseTestFragment {
    public static final String TAG = "SimpleTDD.ATest";

	@Override
	protected void setUp() {
	}
	
	@Override
	protected void tearDown() {
	}

	@Override
	protected void setMenuList(List<String> testList) {
		testList.add("testDemoView");
	}
	
	
	// Our testing
	public void testDemoView(){
		Log.d(TAG, "testDemoView");

        toast("Display the layout of 'demoview.xml'");

        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.demoview, null, false);

        this.addSubview(view, 0, 0,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	}
	

	protected void didRunTest()
	{
		// To be implement
		// hideMenu();
	}
}
