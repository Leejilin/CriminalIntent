package com.example.crimeintent;


import java.util.Date;
import java.util.UUID;
import com.example.crimeintent.R.id;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class CrimeFragment extends Fragment {
	public static final String EXTRA_CRIME_ID="criminalintent.crime.id";
	public static final String DIALOG_DATE="date";
	public static final int REQUEST_DATE=0;
	private Crime mCrime;
	private EditText mTitleField; 
	private Button mButton;
	private CheckBox mCheckBox;
	private Button_OnClickListener mButton_OnClickListener = new Button_OnClickListener();
	public void updatebtn(){
		mButton.setText(mCrime.getDate().toString());
	}
	//Inner Class Button_OnClickListener
	private class Button_OnClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FragmentManager fm = getActivity().getSupportFragmentManager();
			DatePickeFragment dialog = DatePickeFragment.newInstance(mCrime.getDate());
			//这里出错，错：dialog
			// 		    对：CrimeFragment.this
			//setTargetFragment返回空，CrimeFragment.this是目标fragment，REQUEST_DATE是标签 
			//在DatePickeFragment中调用getTargetFragment()，返回带有REQUEST_DATE的Fragment给Activity
			//重写onActivityResult（requestCode, resultCode, data)
			//返回的Fragment中的"CrimeFragment->REQUEST_DATE"作为requestCode
			//				  "DatePickeFragment->Activity.RESULT_OK”作为resultCode		
			dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
			dialog.show(fm, DIALOG_DATE);
		}
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		//UUID CrimeId=(UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
		UUID CrimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID) ;
		mCrime=CrimeLab.get(getActivity()).getCrime(CrimeId);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//fragment will reseive a callback-request from onOptionsItemSelected()
		switch(item.getItemId()){
		case android.R.id.home :
			if(NavUtils.getParentActivityName(getActivity())!=null)
				NavUtils.navigateUpFromSameTask(getActivity());
			return true;		
		default :
			return super.onOptionsItemSelected(item);
		}
	}
	public static CrimeFragment newInstance(UUID id){
		Bundle arguments = new Bundle();
		arguments.putSerializable(EXTRA_CRIME_ID, id);
		CrimeFragment mCrimeFragment = new CrimeFragment();
		mCrimeFragment.setArguments(arguments);
		return mCrimeFragment;
	}
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_crime, container,false); 
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
			if(NavUtils.getParentActivityIntent(getActivity())!=null)
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		//Date-Button display date
		mTitleField=(EditText)v.findViewById(R.id.Edit_Crime_Title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				mCrime.setTitle(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		mButton=(Button)v.findViewById(id.Button_Crime_Date); 
		//mButton.setText(mCrime.getDate().toString());
		updatebtn();
		//mButton.setEnabled(false);
		mButton.setOnClickListener(mButton_OnClickListener);
		
		
		
		//CheckBox choosed or unchoose statement
		mCheckBox=(CheckBox)v.findViewById(id.CheckBox_Crime_Solved);
		mCheckBox.setChecked(mCrime.isSolved());
		mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mCrime.setSolved(isChecked);
			}
		});
		return v;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode!=Activity.RESULT_OK)return;
		if(requestCode==REQUEST_DATE)
		{
			Date mDate =(Date) data.getSerializableExtra(DatePickeFragment.EXTRA_DATE);
			mCrime.setDate(mDate);
			updatebtn();
		}
		//super.onActivityResult(requestCode, resultCode, data);
		//getActivity().setResult(Activity.RESULT_OK,null);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		CrimeLab.get(getActivity()).saveCrimes();
		Toast.makeText(getActivity(), R.string.success_write_to_disk, Toast.LENGTH_SHORT).show();
	}
	
}
