package com.kencoder.simpletdd.demo;


import com.kencoder.simpletdd.core.TestSuiteFragment;

public class MyTestFragment extends TestSuiteFragment {
	@Override
	protected String[] getTestName() {
		return new String[] {
            "ATest",
            "DemoTest",
            "LayoutViewTest",
		};
	}


	@Override
	protected String getPackageName()
	{
		return this.getClass().getPackage().getName() + ".tests";
	}
}
