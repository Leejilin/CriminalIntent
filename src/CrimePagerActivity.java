package com.example.crimeintent;

import java.util.ArrayList;
import java.util.UUID;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class CrimePagerActivity extends FragmentActivity {
	public static final String PAGER="pageractivity.title";
	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mViewPager=new ViewPager(this);
		mViewPager.setId(R.id.viewpager);
		setContentView(mViewPager);
		
		mCrimes = CrimeLab.get(this).getCrimes();
		
		FragmentManager fm = getSupportFragmentManager();
/*		int pos=(int)getIntent().getIntExtra(PAGER, 0);
		String title="";
		title=mCrimes.get(pos).getTitle();		
		setTitle(title.toString());*/
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mCrimes.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				Crime mCrime = mCrimes.get(arg0);
				return CrimeFragment.newInstance(mCrime.getId()) ;
			}
		});
		
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for(int i =0 ;i<mCrimes.size();i++)
		{	if(mCrimes.get(i).getId().equals(crimeId))
			{
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				// TODO Auto-generated method stub
				Crime mCrime=mCrimes.get(pos);
				if(mCrime.getTitle()!=null){
					setTitle(mCrime.getTitle());
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
