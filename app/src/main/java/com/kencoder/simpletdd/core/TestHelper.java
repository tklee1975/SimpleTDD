package com.kencoder.simpletdd.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class TestHelper {
	private static final String PREFS_NAME = "simpletdd.prefs";
	private static final String PREF_NAME_FILTER_TEXT = "filter.text";
	private static TestSupportDelegate supportDelegate = null;
	private static Class mainTestFragment = null;
	
	private static String mFilterText = "";
	
	public static void setMainTestFragmentClass(Class cls){
		mainTestFragment = cls;
	}
	
	public static void saveFilter(Activity activity, String text) {
		SharedPreferences setting = activity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.putString(PREF_NAME_FILTER_TEXT, text);
		editor.commit();
		
		mFilterText = text;
	}
	
	public static String loadFilter(Activity activity) {
		SharedPreferences setting = activity.getSharedPreferences(PREFS_NAME, 0);
		
		mFilterText = setting.getString(PREF_NAME_FILTER_TEXT, "");

		return getFilter();
	}
	
	public static String getFilter() {
		return mFilterText;
	}
	
	public static void setSupportDelegate(TestSupportDelegate delegate)
	{
		supportDelegate = delegate;
	}
	
	public static void showTest(Fragment fragment)
	{
		showFragment(fragment);
	}
	
	public static void showFragment(Fragment fragment)
	{
		if(supportDelegate == null) {
			Log.d("MyTest", "supportDelegate undefined");
			return;
		}
		supportDelegate.showFragment(fragment);
	}
	
	public static void popTest()
	{
		if(supportDelegate == null) {
			Log.d("MyTest", "supportDelegate undefined");
			return;
		}
		supportDelegate.popFragment();
	}
	
	@SuppressWarnings("rawtypes")
	public static Fragment getFragmentByClassname(String className)
	{
		try {
			Class cls = Class.forName(className);
			
			return getFragmentByClass(cls);
		} catch (ClassNotFoundException e) {
			Log.d("TDDandroid", "getFragmentByClassname: cls=" + className);
		    return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Fragment getFragmentByClass(Class classObj)
	{
		try {
			
		    Object obj = classObj.newInstance();
		    if((obj instanceof Fragment) == false) {
		    	Log.d("TDDandroid", "getFragmentByClass: Given class isn't Fragment");
		    	return null;
		    }

		    return (Fragment) obj;
		} catch (Exception e) {
			Log.d("TDDandroid", "getFragmentByClass: exception: cls=" + classObj.getName() + " e=" + e);
		    return null;
		}
	}
	
	public static OnClickListener getOnTestListener(final Activity mainActivity) {
		
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Test", "Button is clicked");
				
				Intent intent = new Intent(mainActivity, TestSuiteActivity.class);
				intent.setAction(Intent.ACTION_MAIN);

				mainActivity.startActivity(intent);	
				
			}
			
		};
	}

    public static void toast(Context context, String msg)
    {
        Toast toast = Toast.makeText(context, msg, 3000);
        toast.show();
    }



	public static Class getMainTestFragment() {
		return mainTestFragment;
	}
}
