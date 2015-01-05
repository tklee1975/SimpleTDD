package com.kencoder.simpletdd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kencoder.simpletdd.core.TestHelper;
import com.kencoder.simpletdd.demo.MyTestFragment;


public class MainActivity extends ActionBarActivity {
//region main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "PlaceholderFragment");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }



    }
//endregion


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
            Log.d("MainActivity", "PlaceholderFragment");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            Log.d("MainActivity", "onCreateView");

            setupGUI(rootView);

            return rootView;
        }

        private void setupGUI(View rootView) {
            Button testButton = (Button) rootView.findViewById(R.id.testButton);

            Log.d("MainActivity", "setupGUI: testButton=" + testButton);
            if(testButton != null) {
                TestHelper.setMainTestFragmentClass(MyTestFragment.class);
                testButton.setOnClickListener(TestHelper.getOnTestListener(this.getActivity()));
            }
        }
    }
}
