package com.example.homelibrary;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerLayoutAdapter extends BaseAdapter
{
	Context context;
	ArrayList<Interface_DrawerLayout> list_DrawerLayout;
	LayoutInflater layoutInflater;
	SharedPreferences sharedPreferences;
	
	int booksCount;
	
	@SuppressWarnings("static-access")
	public DrawerLayoutAdapter(Books_Activity books_Activity, ArrayList<Interface_DrawerLayout> list_DrawerLayout)
	{
		this.context = books_Activity;
		this.list_DrawerLayout = list_DrawerLayout;
		
		layoutInflater = LayoutInflater.from(books_Activity);
		
		sharedPreferences = books_Activity.getSharedPreferences("count", books_Activity.MODE_PRIVATE);
	}

	@Override
	public int getCount()
	{
		return list_DrawerLayout.size();
	}

	@Override
	public Object getItem(int position)
	{
		return list_DrawerLayout.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@SuppressWarnings("static-access")
	public void updateAdapter(Books_Activity books_Activity, ArrayList<Interface_DrawerLayout> list_DrawerLayout)
	{
		this.context = books_Activity;
		this.list_DrawerLayout = list_DrawerLayout;
		
		sharedPreferences = books_Activity.getSharedPreferences("count", books_Activity.MODE_PRIVATE);
		
		notifyDataSetChanged();
		
		Log.e("From updateAdapter", "updateAdapter");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		
		Interface_DrawerLayout interface_DrawerLayout = list_DrawerLayout.get(position);
		
		if(interface_DrawerLayout != null)
		{
			if(interface_DrawerLayout.isHeader())
			{
				SectionHeaderClass sectionHeaderClass = (SectionHeaderClass) interface_DrawerLayout;
				
				convertView = layoutInflater.inflate(R.layout.sectionheader, null);
				convertView.setClickable(false);
				convertView.setOnClickListener(null);
				convertView.setLongClickable(false);
				convertView.setOnLongClickListener(null);
				
				TextView sectionTextView = (TextView) convertView.findViewById(R.id.sectionHeader);
				sectionTextView.setText(sectionHeaderClass.getHeader());
			}
			else
			{
				Items_SectionHeaderClass items_SectionHeaderClass = (Items_SectionHeaderClass) interface_DrawerLayout;
				
				convertView = layoutInflater.inflate(R.layout.items_sectionheader, null);
				
				TextView itemsTextView = (TextView) convertView.findViewById(R.id.itemSectionHeader_textView);
				TextView itemsCountTextView = (TextView) convertView.findViewById(R.id.itemSectionHeader_booksCount_textView);
				ImageView itemsImageView = (ImageView) convertView.findViewById(R.id.itemSectionHeader_imageView);
				
				itemsTextView.setText(items_SectionHeaderClass.getItems_Header());
				
				if(items_SectionHeaderClass.getItems_Header().equals("Add New Book"))
				{
					itemsImageView.setImageResource(R.drawable.add_book);
					itemsCountTextView.setVisibility(View.INVISIBLE);
				}
				else if(items_SectionHeaderClass.getItems_Header().equals("Books List"))
				{
					itemsImageView.setImageResource(R.drawable.libraryicon2);
					
					int count = sharedPreferences.getInt("totalCount", 0);
					
					if(count == 0)
						itemsCountTextView.setVisibility(View.INVISIBLE);
					else
						itemsCountTextView.setText(""+sharedPreferences.getInt("totalCount", count));
				}
				else if(items_SectionHeaderClass.getItems_Header().equals("By Date"))
				{
					itemsImageView.setImageResource(R.drawable.date_and_time1);
					itemsCountTextView.setVisibility(View.INVISIBLE);
				}
				else if(items_SectionHeaderClass.getItems_Header().equals("By Book Name"))
				{
					itemsImageView.setImageResource(R.drawable.name);
					itemsCountTextView.setVisibility(View.INVISIBLE);
				}
				else if(items_SectionHeaderClass.getItems_Header().equals("By Author Name"))
				{
					itemsImageView.setImageResource(R.drawable.name);
					itemsCountTextView.setVisibility(View.INVISIBLE);
				}
				else if(items_SectionHeaderClass.getItems_Header().equals("By Publisher Name"))
				{
					itemsImageView.setImageResource(R.drawable.name);
					itemsCountTextView.setVisibility(View.INVISIBLE);
				}
				else if(items_SectionHeaderClass.getItems_Header().trim().equals("Available"))
				{
					itemsImageView.setImageResource(R.drawable.available);
					
					int count = sharedPreferences.getInt("availableCount", 0);
					
					if(count == 0)
						itemsCountTextView.setVisibility(View.INVISIBLE);
					else
						itemsCountTextView.setText(""+sharedPreferences.getInt("availableCount", 0));
				}
				else if(items_SectionHeaderClass.getItems_Header().trim().equals("Required"))
				{
					itemsImageView.setImageResource(R.drawable.required);
					
					int count = sharedPreferences.getInt("requiredCount", 0);
					
					if(count == 0)
						itemsCountTextView.setVisibility(View.INVISIBLE);
					else
						itemsCountTextView.setText(""+sharedPreferences.getInt("requiredCount", 0));
				}
				else if(items_SectionHeaderClass.getItems_Header().trim().equals("Linked"))
				{
					itemsImageView.setImageResource(R.drawable.page_white_link);
					
					int count = sharedPreferences.getInt("linkedCount", 0);
					
					if(count == 0)
						itemsCountTextView.setVisibility(View.INVISIBLE);
					else
						itemsCountTextView.setText(""+sharedPreferences.getInt("linkedCount", 0));
				}
				else if(items_SectionHeaderClass.getItems_Header().equals("No Filter"))
				{
					itemsImageView.setImageResource(R.drawable.nofilter);
					itemsCountTextView.setVisibility(View.INVISIBLE);
				}
				else
				{
					itemsImageView.setVisibility(View.INVISIBLE);
					itemsCountTextView.setVisibility(View.INVISIBLE);
				}
			}
		}
		
		return convertView;
	}

}