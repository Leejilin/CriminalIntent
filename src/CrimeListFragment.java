package com.example.crimeintent;



import java.util.ArrayList;

import com.example.crimeintent.R.id;
import com.example.crimeintent.R.string;

import android.support.v4.app.ListFragment;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CrimeListFragment extends ListFragment {
	private ArrayList<Crime> mCrimes;
	private static final int REQUSET_CRIME=1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		//通知fragmentManager对于这个fragment调用onCreateOptionsMenu（）和相关方法来填充“操作菜单”
		getActivity().setTitle(R.string.crimes_title);
		mCrimes=CrimeLab.get(getActivity()).getCrimes();
		//Toast.makeText(getActivity(), R.string.success_open_the_file, Toast.LENGTH_SHORT).show();
/*		ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(this.getActivity(),
					layout.simple_list_item_1, 
					mCrimes);
		setListAdapter(adapter);*/
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);
		
	}
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v ;
		v=super.onCreateView(inflater, container, savedInstanceState);
		ListView listView=(ListView)v.findViewById(android.R.id.list);
		//一开始写的是Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB，可以删除单个item
		//if(Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB){
		registerForContextMenu(listView);
		/*}else{
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
				
				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// TODO 自动生成的方法存根
					return false;
				}
				
				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// TODO 自动生成的方法存根
					
				}
				
				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					// TODO 自动生成的方法存根
					MenuInflater inflater=mode.getMenuInflater();
					inflater.inflate(com.example.crimeintent.R.menu.crime_list_item_context, menu);
					return true;
				}
				
				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					// TODO 自动生成的方法存根
					switch(item.getItemId()){
					case id.menu_item_delete_crime:
						CrimeAdapter adapter=(CrimeAdapter)getListAdapter();
						CrimeLab crimeLab = CrimeLab.get(getActivity());
						for(int i=adapter.getCount()-1;i>=0;i--){
							if(getListView().isItemChecked(i)){
								crimeLab.deleteCrime(adapter.getItem(i));
							}
						}
						mode.finish();
						adapter.notifyDataSetChanged();
						return true;
					default:
					return false;
					}
				}			
				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
					// TODO 自动生成的方法存根
					
				}
			});
		}*/
		return v;
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Crime c=(Crime)(this.getListAdapter()).getItem(position);
		//start activity
		Intent intent=new Intent(getActivity(),CrimePagerActivity.class);
		intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		intent.putExtra(CrimePagerActivity.PAGER, position);
		//startActivity(intent);
		startActivityForResult(intent, REQUSET_CRIME);
	}
	
	private class CrimeAdapter extends ArrayAdapter<Crime>{

		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView=getActivity().getLayoutInflater().inflate(
						R.layout.list_item_crime, null);				
			}
			
			Crime c=getItem(position);
			TextView titleTextView;
			TextView dateTextView;
			CheckBox solvedCheckBox;
			
			solvedCheckBox=(CheckBox)convertView.findViewById(R.id.crime_list_item_checkbox);
			dateTextView=(TextView)convertView.findViewById(R.id.crime_list_title_dateview);
			titleTextView=(TextView)convertView.findViewById(R.id.crime_list_title_textview);
			
			titleTextView.setText(c.getTitle());
			dateTextView.setText(c.getDate().toString());
			solvedCheckBox.setChecked(c.isSolved());
			
			return convertView;
		}
		
	}
	@Override
	public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
		}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUSET_CRIME){
			//
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		
	}
	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.menu_item_new_crime:
			Crime crime = new Crime();
			CrimeLab.get(getActivity()).addCrime(crime);
			Intent i = new Intent(getActivity(),CrimePagerActivity.class);
			i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
			startActivity(i);
			return true;
		case R.id.menu_item_show_subtitle:
			if(getActivity().getActionBar().getSubtitle()==null)
			{	getActivity().getActionBar().setSubtitle(R.string.subtitle);
				item.setTitle(R.string.hide_subtitle);
				}else{
					getActivity().getActionBar().setSubtitle(null);
					item.setTitle(R.string.subtitle);
				}
		default:	
			return super.onOptionsItemSelected(item);
	}
		}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO 自动生成的方法存根
		super.onCreateContextMenu(menu, v, menuInfo);
		getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
		
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO 自动生成的方法存根
		AdapterContextMenuInfo info=(AdapterContextMenuInfo)item.getMenuInfo();
		int position=info.position;
		CrimeAdapter adapter=(CrimeAdapter)getListAdapter();
		Crime crime=adapter.getItem(position);
		
		switch(item.getItemId()){
		case R.id.menu_item_01:
			//CrimeLab.get(getActivity()).deleteCrime(crime);
			//adapter.notifyDataSetChanged();
			Toast.makeText(getActivity(), string.delete_crime, Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_item_02:
			Toast.makeText(getActivity(), string.subtitle, Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onContextItemSelected(item);
		
	}

}
