package com.example.crimeintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;


public class DatePickeFragment extends DialogFragment {
	public static final String EXTRA_DATE="criminalintent.date";
	
	public static DatePickeFragment newInstance(Date date){
		Bundle argument = new Bundle();
		argument.putSerializable(EXTRA_DATE, date);
		DatePickeFragment fragment = new DatePickeFragment();
		fragment.setArguments(argument);
		return fragment;
	}
	private Date mDate;
	
	private Dialog mAlertDialog_Builder(View v,Date mDate ){
		Dialog mAlertDialog_Builder;
		PositiveButton mPositiveButton = new PositiveButton();
		
		mAlertDialog_Builder = new AlertDialog.Builder(getActivity())
												.setTitle(null)
												.setPositiveButton(android.R.string.ok,mPositiveButton)
												.setView(v)
												.create();
		return mAlertDialog_Builder;
	}
//Inner class PositiveButton
private class PositiveButton implements OnClickListener{
		
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		if(getTargetFragment()==null)
			return;
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		//						getTargetRequestCode()
		//								||
		//								||
		//dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE)
		getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
	}
	
}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = getActivity().getLayoutInflater().inflate(R.layout.datepicker, null);
		mDate=(Date)getArguments().getSerializable(EXTRA_DATE);
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTime(mDate);
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH);
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);
		
		DatePicker mDatePicker = (DatePicker)v.findViewById(R.id.date_picker);
		mDatePicker.init(year, month, day, new OnDateChangedListener(){

			@Override
			public void onDateChanged(DatePicker view, int year, int month, int day) {
				// TODO Auto-generated method stub
				mDate=new GregorianCalendar(year, month, day).getTime();
				getArguments().putSerializable(EXTRA_DATE, mDate);
			}
			
		});
		return mAlertDialog_Builder(v,mDate);
	}
}
