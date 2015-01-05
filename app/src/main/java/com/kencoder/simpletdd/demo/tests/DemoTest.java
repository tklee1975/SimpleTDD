package com.kencoder.simpletdd.demo.tests;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.kencoder.simpletdd.core.BaseTestFragment;
import com.kencoder.simpletdd.core.TestHelper;

import java.util.List;

public class DemoTest extends BaseTestFragment {
    public static final String TAG = "SimpleTDD.ATest";

	@Override
	protected void setUp() {
	}
	
	@Override
	protected void tearDown() {
	}

	@Override
	protected void setMenuList(List<String> testList) {
		testList.add("testStatus");
        testList.add("testClearStatus");
        testList.add("testAddSubview");
        testList.add("testClearView");
	}
	
	
	// Our testing
	public void testStatus(){
		Log.d(TAG, "testStatus");

        this.setStatus("add something to status!");

	}

    public void testAddSubview() {
        TextView tv;

        tv = new TextView(getActivity());
        tv.setBackgroundColor(Color.RED);
        addSubview(tv, 10, 10, 200, 200);

        tv = new TextView(getActivity());
        tv.setBackgroundColor(Color.GREEN);
        addSubview(tv, 50, 240, 200, 200);

        tv = new TextView(getActivity());
        tv.setBackgroundColor(Color.BLUE);
        addSubview(tv, 100, 450, 200, 200);

    }

    public void testClearStatus() {
        this.clearStatus();;
    }

    public void testClearView() {
        this.clearViews();
    }

	

	protected void didRunTest()
	{
		// To be implement
		// hideMenu();
	}
}
