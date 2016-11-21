package com.example.crimeintent;

import com.example.crimeintent.R.id;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


public class CrimeListActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crime_list);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(id.list_fragmentcontainer);
		if(fragment==null){
			fragment = new CrimeListFragment();
			fm.beginTransaction().add(id.list_fragmentcontainer, fragment).commit();
			
		}
	}
}
