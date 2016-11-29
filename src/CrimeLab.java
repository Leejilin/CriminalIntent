package com.example.crimeintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class CrimeLab {
	private static CrimeLab sCrimeLab;
	private ArrayList<Crime> mCrimes;
	private CriminalIntentJSONSerializer mSerializer;
	private static final String FILNAME="crimes.json";
	private static final String TAG="CrimeLab";
	private Context mContext;
	
	private CrimeLab(Context appContext){
		//the sequence of mcontext,mserializer,mcrimes must in order,otherwise it will report Nullofpointer
		mContext=appContext;
		mSerializer=new CriminalIntentJSONSerializer(mContext, FILNAME);		
		try{
		mCrimes=mSerializer.loadCrimes();
			}catch(Exception e){
		mCrimes=new ArrayList<Crime>();
		Log.d(TAG, "Eorror loading crimes",e);
			}
		
		
	}

	public static CrimeLab get(Context c){
		if(sCrimeLab==null){
			sCrimeLab=new CrimeLab(c.getApplicationContext());
		}
		return sCrimeLab;		
	}

	public Crime getCrime(UUID id){
		for(Crime c:mCrimes){
			if(c.getId().equals(id)){
				return c;
			}
		}
		return null;
	}
	
	public ArrayList<Crime> getCrimes(){
		return mCrimes;
	}
	
	public void addCrime(Crime c){
		mCrimes.add(c);
	}
	
	public boolean saveCrimes(){
		try{
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "crimes has saved to file");
			return true;
		}catch(Exception e){
			Log.d(TAG, "error saving crimes",e);
			return false;
		}
	}
	
	public void deleteCrime(Crime c){
		mCrimes.remove(c);
		
	}
}

