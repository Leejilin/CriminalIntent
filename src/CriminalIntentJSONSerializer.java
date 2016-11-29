package com.example.crimeintent;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

/**
 *Serializer ：串行化器--将并行数据变成串行数据的寄存器 
 *JSON Serializer--将指定对象转换为JSON格式--MAP<key.value>
 */
public class CriminalIntentJSONSerializer {
	private Context mContext;
	private String mfilename;
	
	/**
	 *Constructor(Context,String)
	 * 
	 * @param Context On purpuse to use the method of context.
	 * @param String A file name ,but not an absolute path,save and load both use it.
	 */
	public CriminalIntentJSONSerializer(Context context, String mfilename) {
		super();
		mContext = context;
		this.mfilename = mfilename;
	}
	/**
	 * savecrimes(arraylist) :get an array ,change to jsonarray,put in disk
	 */
	public void saveCrimes(ArrayList<Crime> crimes)throws JSONException,IOException{
		JSONArray array = new JSONArray();
		for(Crime c:crimes){
			array.put(c.toJSON());
		}
		Writer writer =null;
		try{
			OutputStream outputStream=mContext.openFileOutput(mfilename, Context.MODE_PRIVATE);
			writer=new OutputStreamWriter(outputStream);
			writer.write(array.toString());
		}finally{
			if(writer!=null){
				writer.close();
			}
				
		}
			
		
		
	}
	/**
	 * loadcrimes:open a file use filename save data to crimes
	 * @return crimes 
	 */
	public ArrayList<Crime> loadCrimes()throws JSONException,IOException{
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader=null;;
		try{
			InputStream inputStream = mContext.openFileInput(mfilename);
			reader=new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder jsonString=new StringBuilder();
			String line = null;
			while ((line=reader.readLine())!=null){
				jsonString.append(line);				
			}
			JSONArray array = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();
			for(int a=0;a<array.length();a++){
				crimes.add(new Crime(array.getJSONObject(a)));
			}
		}catch(FileNotFoundException e){
			//
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		return crimes;
	}
}
