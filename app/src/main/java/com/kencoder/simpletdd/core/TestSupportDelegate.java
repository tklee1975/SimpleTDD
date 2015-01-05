package com.kencoder.simpletdd.core;

import android.support.v4.app.Fragment;

public interface TestSupportDelegate {
	public void showFragment(Fragment newTest);
	public void popFragment();
}
