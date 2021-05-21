package com.example.homelibrary;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ForSortFilter_Fragment extends Fragment
{
	static long back_pressed;
	String clickedItem= "";
	
	ArrayList<BooksInfoClass> array_ListFragment;
	ListView listView;
	Books_Database books_Database;
	Books_ListFragment books_ListFragment;
	ForBooksListFragment_Adapter forBooksListFragment_Adapter;
	Bundle bundle;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
//		if(savedInstanceState != null)
//			clickedItem = savedInstanceState.getString("clickedItem");
		
		clickedItem = getArguments().getString("clickedItem");
		Log.e("onCreateView", clickedItem);
		
		return inflater.inflate(R.layout.for_sortfilter_fragment, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
//		if(savedInstanceState != null)
//			clickedItem = savedInstanceState.getString("clickedItem");
		Log.e("onActivityCreated", clickedItem);
		
//		books_ListFragment = (Books_ListFragment) getFragmentManager().findFragmentByTag("BooksListFragment");
//		books_ListFragment.getView().setFocusableInTouchMode(true);
//		books_ListFragment.getView().setOnKeyListener( new OnKeyListener()
//		{
//		    @Override
//		    public boolean onKey( View v, int keyCode, KeyEvent event)
//		    {
//		        if( keyCode == KeyEvent.KEYCODE_BACK)
//		        {
//		        	if (back_pressed + 2000 > System.currentTimeMillis()) 
//			        {
//		        	   Intent intent = new Intent(Intent.ACTION_MAIN);
//					   intent.addCategory(Intent.CATEGORY_HOME);
//					   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					   startActivity(intent);
//			        }
//			        else 
//			            Toast.makeText(getActivity(), "From Books_ListFragment, Press once again to exit !!",Toast.LENGTH_SHORT).show();
//
//			        back_pressed = System.currentTimeMillis();
//
//		        	return true;
//		        }
//		        return false;  
//		    }
//		});
		

		books_Database = new Books_Database(getActivity());
		listView =  (ListView) getActivity().findViewById(R.id.for_sortfilter_fragment_listView);
		listView.setVerticalScrollBarEnabled(false);
		listView.setHorizontalFadingEdgeEnabled(false);
		array_ListFragment = new ArrayList<BooksInfoClass>();
				
		array_ListFragment = books_Database.getAllBooksDetails();
		forBooksListFragment_Adapter = new ForBooksListFragment_Adapter(getActivity(), R.layout.for_books_activity, array_ListFragment);
		
		Log.e("array_ListFragment", "array_ListFragment "+array_ListFragment);
		Log.e("array_ListFragment", "array_ListFragment size "+array_ListFragment.size());
		
		if(array_ListFragment.size() > 0)
			((ListView) listView).setAdapter(forBooksListFragment_Adapter);
		else
			Toast.makeText(getActivity(), "No Books To Show !!", Toast.LENGTH_SHORT).show();

		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
			{
				Books_OneBook_Fragment books_OneBook_Fragment = (Books_OneBook_Fragment) getFragmentManager().findFragmentByTag("BookInfoFragment");
				bundle = new Bundle();
				bundle.putInt("Position", position);
				bundle.putString("From", clickedItem);
				
				if(books_OneBook_Fragment != null)
				{
					books_OneBook_Fragment = new Books_OneBook_Fragment();
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					books_OneBook_Fragment.setArguments(bundle);
					fragmentTransaction.replace(R.id.Container_For_Fragments, books_OneBook_Fragment, "BookInfoFragment");
					fragmentTransaction.addToBackStack("BookInfoFragment");
					fragmentTransaction.commit();
				}
				else
				{
					books_OneBook_Fragment = new Books_OneBook_Fragment();
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					books_OneBook_Fragment.setArguments(bundle);
					fragmentTransaction.replace(R.id.Container_For_Fragments, books_OneBook_Fragment, "BookInfoFragment");
					fragmentTransaction.addToBackStack("BookInfoFragment");
					fragmentTransaction.commit();
				}
			}
		});
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		Log.e("onResume", "clickedItem"+clickedItem);
		
		if(array_ListFragment != null)
			array_ListFragment.clear();

		if(clickedItem.equals("By Date"))
			array_ListFragment = books_Database.getBooks_SortByDate();
		else if(clickedItem.equals("By Book Name"))
			array_ListFragment = books_Database.getBooks_SortByBookName();
		else if(clickedItem.equals("By Author Name"))
			array_ListFragment = books_Database.getBooks_SortByAuthorName();
		else if(clickedItem.equals("By Publisher Name"))
			array_ListFragment = books_Database.getBooks_SortByPublisherName();
		else if(clickedItem.equals("Available"))
			array_ListFragment = books_Database.getBooks_FilterByAvailable();
		else if(clickedItem.equals("Required"))
			array_ListFragment = books_Database.getBooks_FilterByRequired();
		else if(clickedItem.equals("Linked"))
			array_ListFragment = books_Database.getBooks_FilterByLinked();
		else
			array_ListFragment = books_Database.getAllBooksDetails();
		
		Log.e("onResume array_ListFragment", "array_ListFragment "+array_ListFragment);
		Log.e("onResume array_ListFragment", "array_ListFragment size "+array_ListFragment.size());
		
		forBooksListFragment_Adapter.updateAdapter(getActivity(), R.layout.for_books_activity, array_ListFragment);
		
//		if(array_ListFragment.size() > 0)
//			((ListView) listView).setAdapter(new ForBooksListFragment_Adapter(getActivity(), R.layout.for_books_activity, array_ListFragment));
//		else
//			Toast.makeText(getActivity(), "No Books To Show !!", Toast.LENGTH_SHORT).show();
	}

}