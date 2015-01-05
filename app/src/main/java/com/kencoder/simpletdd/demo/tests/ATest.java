package com.kencoder.simpletdd.demo.tests;

import android.util.Log;


import com.kencoder.simpletdd.core.BaseTestFragment;
import com.kencoder.simpletdd.core.TestHelper;

import java.util.List;

public class ATest extends BaseTestFragment {
    public static final String TAG = "SimpleTDD.ATest";

	@Override
	protected void setUp() {
	}
	
	@Override
	protected void tearDown() {
	}

	@Override
	protected void setMenuList(List<String> testList) {
		testList.add("test1");
	}
	
	
	// Our testing
	public void test1(){
		Log.d(TAG, "test");

        TestHelper.toast(this.getActivity(), "Try to add some sub test!");

	}
	

	protected void didRunTest()
	{
		// To be implement
		// hideMenu();
	}
}
