package com.example.crimeintent;


import java.util.Date;
import java.util.UUID;

public class Crime {
	private UUID mId;
	private String Title;
	private Date mDate;
	private boolean mSolved;
	
	public Crime() {
		mId = UUID.randomUUID();
		mDate=new Date();
	}
	public UUID getId() {
		return mId;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}	
	public Date getDate() {
		
		return mDate;
	}
	public void setDate(Date date) {
		mDate = date;
	}
	public boolean isSolved() {
		return mSolved;
	}
	public void setSolved(boolean solved) {
		mSolved = solved;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Title;
	}
}
