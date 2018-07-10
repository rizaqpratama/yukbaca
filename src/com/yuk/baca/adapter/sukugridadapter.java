package com.yuk.baca.adapter;

import java.util.ArrayList;

import com.yuk.baca.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class sukugridadapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> sukus= new ArrayList<String>();
	public sukugridadapter(Context context ,ArrayList<String> sukus) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.sukus=sukus;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sukus.size();
		
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return sukus.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View grid_item= arg1;
		itemHolder holder= null;
		if(grid_item==null){
			LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			grid_item= inflater.inflate(R.layout.grid_item, arg2,false);
			holder= new itemHolder();
			holder.tv= (TextView)grid_item.findViewById(R.id.textGridItem);
			grid_item.setTag(holder);
			
		}else{
			holder=(itemHolder)grid_item.getTag();
		}
		holder.tv.setText(sukus.get(arg0));
		return grid_item;
	}
	
	static class itemHolder{
		TextView tv;
	}

}
