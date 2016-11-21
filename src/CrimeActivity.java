package com.example.crimeintent;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import java.util.UUID;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;



public class CrimeActivity extends FragmentActivity{


	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		UUID id= (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		return CrimeFragment.newInstance(id);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crime);
		//Creating fragment manager.
		FragmentManager fragmentmanager = this.getSupportFragmentManager();
		/*
		 * Creating a fragment and commit it to manager.
		 *"findFragmentById()" is find an exist fragment in query.
		 *If the fragment is not in fragment-query,then creating a fragment.
		 */ 
		Fragment fragment = fragmentmanager.findFragmentById(R.id.fragmentContainer);
		if(fragment==null){
			fragment=createFragment();
		//fragmentmanager response to transaction back-stack which contains fragment.
			fragmentmanager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}

	
}
