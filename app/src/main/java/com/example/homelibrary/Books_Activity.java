package com.example.homelibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

@SuppressWarnings("unused")
public class Books_Activity extends Activity implements OnQueryTextListener
{
	SearchManager searchManager;
	SearchView searchView;
	ArrayList<String> booksNamesList;
	Books_Database books_Database;
	
	DrawerLayoutAdapter drawerLayoutAdapter;
	private Books_ListFragment selectedFragment;
	
	ActionBar actionBar = null;
	ListView drawerListView = null, listView = null;;
	DrawerLayout drawerLayout;
	ActionBarDrawerToggle actionBarDrawerToggle;
	
	ArrayList<Interface_DrawerLayout> list_DrawerLayout;
	ArrayList<BooksInfoClass> booksInfoList;
	FragmentManager fragmentManager;
	Books_ListFragment booksListFragment;
	ForSortFilter_Fragment forSortFilter_Fragment;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private long back_pressed;
	private boolean calledOnResume = true;
	
	private String filePath = "";
	
//	private ArrayList<NavDrawerItem> navDrawerItems;
//	private NavDrawerListAdapter adapter;

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_books__main);

//		mTitle = mDrawerTitle = getTitle();
//
//		// load slide menu items
////		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
//
//		// nav drawer icons from resources
////		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
//
//		list_DrawerLayout = new ArrayList<Interface_DrawerLayout>();
//		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//		mDrawerList = (ListView) findViewById(R.id.books_activity_drawerListView);
//
//		fragmentManager = getFragmentManager();
//		
//		list_DrawerLayout.add(new SectionHeaderClass(""));
//		list_DrawerLayout.add(new Items_SectionHeaderClass("","Add"));
//		
//		list_DrawerLayout.add(new SectionHeaderClass("Sort"));
//		list_DrawerLayout.add(new Items_SectionHeaderClass("Sort", "By Date"));
//		list_DrawerLayout.add(new Items_SectionHeaderClass("Sort", "By Book Name"));
//		list_DrawerLayout.add(new Items_SectionHeaderClass("Sort", "By Author Name"));
//		list_DrawerLayout.add(new Items_SectionHeaderClass("Sort", "By Publisher Name"));
//		
//		list_DrawerLayout.add(new SectionHeaderClass("Filter"));
//		list_DrawerLayout.add(new Items_SectionHeaderClass("Filter", "Available"));
//		list_DrawerLayout.add(new Items_SectionHeaderClass("Filter", "Required"));
//		list_DrawerLayout.add(new Items_SectionHeaderClass("Filter", "Linked"));
//		list_DrawerLayout.add(new Items_SectionHeaderClass("Filter", "No Filter"));
//		
//		mDrawerList.setAdapter(new DrawerLayoutAdapter(Books_Activity.this, list_DrawerLayout));
//		
////		navDrawerItems = new ArrayList<NavDrawerItem>();
//
//		// adding nav drawer items to array
////		 Home
////		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
////		 Find People
////		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
////		 Photos
////		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
////		 Communities, Will add a counter here
////		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
////		 Pages
////		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
////		 What's hot, We  will add a counter here
////		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
//		
//
//		// Recycle the typed array
////		navMenuIcons.recycle();
//
//		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
//
//		// setting the nav drawer list adapter
////		adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
////		mDrawerList.setAdapter(adapter);
//
//		// enabling action bar app icon and behaving it as toggle button
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);
//
//		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//				R.drawable.ic_drawer, //nav menu toggle icon
//				R.string.app_name, // nav drawer open - description for accessibility
//				R.string.app_name // nav drawer close - description for accessibility
//		) {
//			public void onDrawerClosed(View view) {
//				getActionBar().setTitle(mTitle);
//				// calling onPrepareOptionsMenu() to show action bar icons
//				invalidateOptionsMenu();
//			}
//
//			public void onDrawerOpened(View drawerView) {
//				getActionBar().setTitle(mDrawerTitle);
//				// calling onPrepareOptionsMenu() to hide action bar icons
//				invalidateOptionsMenu();
//			}
//		};
//		mDrawerLayout.setDrawerListener(mDrawerToggle);
//
//		if (savedInstanceState == null) {
//			// on first time display view for first nav item
//			displayView(0);
//		}
//		
//		
//		Books_ListFragment books_ListFragment = new Books_ListFragment();
//		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//		fragmentTransaction.add(R.id.Container_For_Fragments, books_ListFragment, "BooksListFragment");
//		fragmentTransaction.addToBackStack("BooksListFragment");
//		fragmentTransaction.commit();
//	}
//
//	/**
//	 * Slide menu item click listener
//	 * */
//	private class SlideMenuClickListener implements
//			ListView.OnItemClickListener {
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//		{
////			displayView(position);
//			
//			if(mDrawerList.getItemAtPosition(position).toString().trim().equals("Add"))
//			{
//				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
//					
//				if(books_ListFragment != null)
//				{
//					AddNewBook_Fragment addNewBook_Fragment = new AddNewBook_Fragment();
//					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
////				fragmentTransaction.add(R.id.Container_For_Fragments, addNewBook_Fragment, "AddBookFragment");
//					fragmentTransaction.replace(R.id.Container_For_Fragments, addNewBook_Fragment, "AddBookFragment");
//					fragmentTransaction.addToBackStack("AddBookFragment");
//					fragmentTransaction.commit();
//				}
//			}
//		}
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.books__main, menu);
//		return true;
//	}
//
////	@Override
////	public boolean onOptionsItemSelected(MenuItem item) {
////		// toggle nav drawer on selecting action bar app icon/title
////		if (mDrawerToggle.onOptionsItemSelected(item)) {
////			return true;
////		}
////		// Handle action bar actions click
////		switch (item.getItemId()) {
////		case R.id.action_settings:
////			return true;
////		default:
////			return super.onOptionsItemSelected(item);
////		}
////	}
//
//	/* *
//	 * Called when invalidateOptionsMenu() is triggered
//	 */
//	
////	@Override
////	public boolean onPrepareOptionsMenu(Menu menu) {
////		// if nav drawer is opened, hide the action items
////		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
////		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
////		return super.onPrepareOptionsMenu(menu);
////	}
//
//	/**
//	 * Diplaying fragment view for selected nav drawer list item
//	 * */
//	private void displayView(int position) {
//		// update the main content by replacing fragments
//		Fragment fragment = null;
//		switch (position) {
//		case 0:
//			fragment = new AddNewBook_Fragment();
//			break;
////		case 1:
////			fragment = new FindPeopleFragment();
////			break;
////		case 2:
////			fragment = new PhotosFragment();
////			break;
////		case 3:
////			fragment = new CommunityFragment();
////			break;
////		case 4:
////			fragment = new PagesFragment();
////			break;
////		case 5:
////			fragment = new WhatsHotFragment();
////			break;
//
//		default:
//			break;
//		}
//
//		if (fragment != null) {
//			FragmentManager fragmentManager = getFragmentManager();
//			fragmentManager.beginTransaction().replace(R.id.Container_For_Fragments, fragment).commit();
//
//			// update selected item and title, then close the drawer
//			mDrawerList.setItemChecked(position, true);
//			mDrawerList.setSelection(position);
////			setTitle(navMenuTitles[position]);
//			mDrawerLayout.closeDrawer(mDrawerList);
//		} else {
//			// error in creating fragment
//			Log.e("MainActivity", "Error in creating fragment");
//		}
//	}
//
//	@Override
//	public void setTitle(CharSequence title) {
//		mTitle = title;
//		getActionBar().setTitle(mTitle);
//	}
//
//	/**
//	 * When using the ActionBarDrawerToggle, you must call it during
//	 * onPostCreate() and onConfigurationChanged()...
//	 */
//
//	@Override
//	protected void onPostCreate(Bundle savedInstanceState) {
//		super.onPostCreate(savedInstanceState);
//		// Sync the toggle state after onRestoreInstanceState has occurred.
//		mDrawerToggle.syncState();
//	}
//
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//		// Pass any configuration change to the drawer toggls
//		mDrawerToggle.onConfigurationChanged(newConfig);
//	}

	
//	ActionBar actionBar = null;
//	ListView drawerListView = null, listView = null;;
//	DrawerLayout drawerLayout;
//	ActionBarDrawerToggle actionBarDrawerToggle;
//	
//	ArrayList<Interface_DrawerLayout> list_DrawerLayout;
//	ArrayList<BooksInfoClass> booksInfoList;
//	FragmentManager fragmentManager;
	
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
	{	
		@Override
		public void onReceive(Context context, Intent intent)
		{
			Log.d("From Books_Activity", "BraodcastReceiver is called");
			
			drawerLayoutAdapter.updateAdapter(Books_Activity.this, list_DrawerLayout);
		}
	};
	
	@Override
    protected void attachBaseContext(Context base)
	{
        super.attachBaseContext(base);
    
//        MultiDex.install(this);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
//		MultiDex.install(getTargetContext());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_books__main);
		
		//If you want to show the three dots, irrespective of device menu button! then you can call this method in your application class' onCreate method....
		getOverflowMenu();
		
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
//		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
		
		booksNamesList = new ArrayList<String>();
		books_Database = new Books_Database(Books_Activity.this);
		booksNamesList = books_Database.getAllBookNames();
		
		list_DrawerLayout = new ArrayList<Interface_DrawerLayout>();
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		drawerListView = (ListView) findViewById(R.id.books_activity_drawerListView);
		drawerListView.setVerticalScrollBarEnabled(false);
		drawerListView.setHorizontalScrollBarEnabled(false);
		
		fragmentManager = getFragmentManager();
		
		list_DrawerLayout.add(new SectionHeaderClass("Books"));
		list_DrawerLayout.add(new Items_SectionHeaderClass("Books","Books List"));
		
		list_DrawerLayout.add(new SectionHeaderClass("Add"));
		list_DrawerLayout.add(new Items_SectionHeaderClass("Add","Add New Book"));
		
		list_DrawerLayout.add(new SectionHeaderClass("Sort"));
		list_DrawerLayout.add(new Items_SectionHeaderClass("Sort", "By Date"));
		list_DrawerLayout.add(new Items_SectionHeaderClass("Sort", "By Book Name"));
		list_DrawerLayout.add(new Items_SectionHeaderClass("Sort", "By Author Name"));
		list_DrawerLayout.add(new Items_SectionHeaderClass("Sort", "By Publisher Name"));
		
		list_DrawerLayout.add(new SectionHeaderClass("Filter"));
		list_DrawerLayout.add(new Items_SectionHeaderClass("Filter", "Available  "));
		list_DrawerLayout.add(new Items_SectionHeaderClass("Filter", "Required  "));
		list_DrawerLayout.add(new Items_SectionHeaderClass("Filter", "Linked      "));
		list_DrawerLayout.add(new Items_SectionHeaderClass("Filter", "No Filter"));
		
		drawerLayoutAdapter = new DrawerLayoutAdapter(Books_Activity.this, list_DrawerLayout);
		drawerListView.setAdapter(drawerLayoutAdapter);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		
		actionBarDrawerToggle = new ActionBarDrawerToggle(Books_Activity.this, drawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name)
		{
			@Override
			public void onDrawerOpened(View drawerView)
			{
				super.onDrawerOpened(drawerView);
				
				invalidateOptionsMenu();
			}
			
			@Override
			public void onDrawerClosed(View drawerView)
			{
				super.onDrawerClosed(drawerView);
				
				invalidateOptionsMenu();
			}
		};
		
		drawerLayout.setDrawerListener(actionBarDrawerToggle);
        
		Bundle bundle = new Bundle();
		bundle.putString("clickedItem", "Nothing");

		Books_ListFragment books_ListFragment = new Books_ListFragment();
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		books_ListFragment.setArguments(bundle);
		fragmentTransaction.add(R.id.Container_For_Fragments, books_ListFragment, "BooksListFragment");
		fragmentTransaction.addToBackStack("BooksListFragment");
		fragmentTransaction.commit();
		
		drawerListView.setOnItemClickListener(new SlideMenuClickListener());
	}
	
	private class SlideMenuClickListener implements	ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{			
			Items_SectionHeaderClass items_SectionHeaderClass = (Items_SectionHeaderClass) list_DrawerLayout.get(position);
			
			Log.e("drawerlistview onitem clicked", items_SectionHeaderClass.getItems_Header());
			
			if(items_SectionHeaderClass.getItems_Header().trim().equals("Add New Book"))
			{
				drawerLayout.closeDrawers();
				
				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
			
				if(books_ListFragment != null)
				{
					AddNewBook_Fragment addNewBook_Fragment = new AddNewBook_Fragment();
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					fragmentTransaction.replace(R.id.Container_For_Fragments, addNewBook_Fragment, "AddBookFragment");
					fragmentTransaction.addToBackStack("AddBookFragment");
					fragmentTransaction.commit();
				}
			}
			else if(items_SectionHeaderClass.getItems_Header().trim().equals("Books List"))
			{
				drawerLayout.closeDrawers();
				
				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
				Bundle bundle = new Bundle();
				bundle.putString("clickedItem", "Books List");
				Log.e("drawerlistview onitem clicked", items_SectionHeaderClass.getItems_Header());
				if(books_ListFragment != null)
				{
//					books_ListFragment = null;
				}
				booksListFragment = new Books_ListFragment();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				booksListFragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.Container_For_Fragments, booksListFragment, "BooksListFragment");
				fragmentTransaction.addToBackStack("BooksListFragment");
				fragmentTransaction.commit();
					
			}
			else if(items_SectionHeaderClass.getItems_Header().trim().equals("By Date"))
			{
				drawerLayout.closeDrawers();
				
				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
				Bundle bundle = new Bundle();
				bundle.putString("clickedItem", "By Date");
				Log.e("drawerlistview onitem clicked", items_SectionHeaderClass.getItems_Header());
				if(books_ListFragment != null)
				{
//					books_ListFragment = null;
				}
				forSortFilter_Fragment = new ForSortFilter_Fragment();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				forSortFilter_Fragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.Container_For_Fragments, forSortFilter_Fragment, "ForSortFilter_Fragment");
				fragmentTransaction.addToBackStack("ForSortFilter_Fragment");
				fragmentTransaction.commit();
				
//				booksListFragment = new Books_ListFragment();
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				booksListFragment.setArguments(bundle);
//				fragmentTransaction.replace(R.id.Container_For_Fragments, booksListFragment, "Books_ListFragment");
//				fragmentTransaction.addToBackStack("Books_ListFragment");
//				fragmentTransaction.commit();
			}
			else if(items_SectionHeaderClass.getItems_Header().trim().equals("By Book Name"))
			{
				drawerLayout.closeDrawers();
				
				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
				Bundle bundle = new Bundle();
				bundle.putString("clickedItem", "By Book Name");
				Log.e("drawerlistview onitem clicked", items_SectionHeaderClass.getItems_Header());
				if(books_ListFragment != null)
				{
//					books_ListFragment = null;
				}
				forSortFilter_Fragment = new ForSortFilter_Fragment();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				forSortFilter_Fragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.Container_For_Fragments, forSortFilter_Fragment, "ForSortFilter_Fragment");
				fragmentTransaction.addToBackStack("ForSortFilter_Fragment");
				fragmentTransaction.commit();
				
//				booksListFragment = new Books_ListFragment();
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				booksListFragment.setArguments(bundle);
//				fragmentTransaction.replace(R.id.Container_For_Fragments, booksListFragment, "Books_ListFragment");
//				fragmentTransaction.addToBackStack("Books_ListFragment");
//				fragmentTransaction.commit();
			}
			else if(items_SectionHeaderClass.getItems_Header().trim().equals("By Author Name"))
			{
				drawerLayout.closeDrawers();
				
				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
				Bundle bundle = new Bundle();
				bundle.putString("clickedItem", "By Author Name");
				Log.e("drawerlistview onitem clicked", items_SectionHeaderClass.getItems_Header());
				if(books_ListFragment != null)
				{
//					books_ListFragment = null;
				}
				forSortFilter_Fragment = new ForSortFilter_Fragment();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				forSortFilter_Fragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.Container_For_Fragments, forSortFilter_Fragment, "ForSortFilter_Fragment");
				fragmentTransaction.addToBackStack("ForSortFilter_Fragment");
				fragmentTransaction.commit();
				
//				booksListFragment = new Books_ListFragment();
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				booksListFragment.setArguments(bundle);
//				fragmentTransaction.replace(R.id.Container_For_Fragments, booksListFragment, "Books_ListFragment");
//				fragmentTransaction.addToBackStack("Books_ListFragment");
//				fragmentTransaction.commit();
			}
			else if(items_SectionHeaderClass.getItems_Header().trim().equals("By Publisher Name"))
			{
				drawerLayout.closeDrawers();
				
				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
				Bundle bundle = new Bundle();
				bundle.putString("clickedItem", "By Publisher Name");
				Log.e("drawerlistview onitem clicked", items_SectionHeaderClass.getItems_Header());
				if(books_ListFragment != null)
				{
//					books_ListFragment = null;
				}
				forSortFilter_Fragment = new ForSortFilter_Fragment();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				forSortFilter_Fragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.Container_For_Fragments, forSortFilter_Fragment, "ForSortFilter_Fragment");
				fragmentTransaction.addToBackStack("ForSortFilter_Fragment");
				fragmentTransaction.commit();
				
//				booksListFragment = new Books_ListFragment();
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				booksListFragment.setArguments(bundle);
//				fragmentTransaction.replace(R.id.Container_For_Fragments, booksListFragment, "Books_ListFragment");
//				fragmentTransaction.addToBackStack("Books_ListFragment");
//				fragmentTransaction.commit();
			}
			else if(items_SectionHeaderClass.getItems_Header().trim().equals("Available"))
			{
				drawerLayout.closeDrawers();
				
//				Books_Activity.this.getFragmentManager().popBackStack();
				
				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
				Bundle bundle = new Bundle();
				bundle.putString("clickedItem", "Available");
				Log.e("drawerlistview onitem clicked", items_SectionHeaderClass.getItems_Header());
				if(books_ListFragment != null)
				{
//					books_ListFragment = null;
				}
				forSortFilter_Fragment = new ForSortFilter_Fragment();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				forSortFilter_Fragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.Container_For_Fragments, forSortFilter_Fragment, "ForSortFilter_Fragment");
				fragmentTransaction.addToBackStack("ForSortFilter_Fragment");
				fragmentTransaction.commit();
				
//				booksListFragment = new Books_ListFragment();
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				booksListFragment.setArguments(bundle);
//				fragmentTransaction.replace(R.id.Container_For_Fragments, booksListFragment, "Books_ListFragment");
//				fragmentTransaction.addToBackStack("Books_ListFragment");
//				fragmentTransaction.commit();
			}
			else if(items_SectionHeaderClass.getItems_Header().trim().equals("Required"))
			{
				drawerLayout.closeDrawers();
				
				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
				Bundle bundle = new Bundle();
				bundle.putString("clickedItem", "Required");
				Log.e("drawerlistview onitem clicked", items_SectionHeaderClass.getItems_Header());
				if(books_ListFragment != null)
				{
//					books_ListFragment = null;
				}
				forSortFilter_Fragment = new ForSortFilter_Fragment();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				forSortFilter_Fragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.Container_For_Fragments, forSortFilter_Fragment, "ForSortFilter_Fragment");
				fragmentTransaction.addToBackStack("ForSortFilter_Fragment");
				fragmentTransaction.commit();
				
//				booksListFragment = new Books_ListFragment();
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				booksListFragment.setArguments(bundle);
//				fragmentTransaction.replace(R.id.Container_For_Fragments, booksListFragment, "Books_ListFragment");
//				fragmentTransaction.addToBackStack("Books_ListFragment");
//				fragmentTransaction.commit();
			}
			else if(items_SectionHeaderClass.getItems_Header().trim().equals("Linked"))
			{
				drawerLayout.closeDrawers();
				
				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
				Bundle bundle = new Bundle();
				bundle.putString("clickedItem", "Linked");
				Log.e("drawerlistview onitem clicked", items_SectionHeaderClass.getItems_Header());
				if(books_ListFragment != null)
				{
//					books_ListFragment = null;
				}
				forSortFilter_Fragment = new ForSortFilter_Fragment();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				forSortFilter_Fragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.Container_For_Fragments, forSortFilter_Fragment, "ForSortFilter_Fragment");
				fragmentTransaction.addToBackStack("ForSortFilter_Fragment");
				fragmentTransaction.commit();
				
//				booksListFragment = new Books_ListFragment();
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				booksListFragment.setArguments(bundle);
//				fragmentTransaction.replace(R.id.Container_For_Fragments, booksListFragment, "Books_ListFragment");
//				fragmentTransaction.addToBackStack("Books_ListFragment");
//				fragmentTransaction.commit();
			}
			else if(items_SectionHeaderClass.getItems_Header().trim().equals("No Filter"))
			{
				drawerLayout.closeDrawers();
				
				Books_ListFragment books_ListFragment = (Books_ListFragment) fragmentManager.findFragmentByTag("BooksListFragment");
				Bundle bundle = new Bundle();
				bundle.putString("clickedItem", "No Filter");
				
				Log.e("drawerlistview onitem clicked", items_SectionHeaderClass.getItems_Header());
				
				if(books_ListFragment != null)
				{
//					books_ListFragment = null;
				}
				
				forSortFilter_Fragment = new ForSortFilter_Fragment();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				forSortFilter_Fragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.Container_For_Fragments, forSortFilter_Fragment, "ForSortFilter_Fragment");
				fragmentTransaction.addToBackStack("ForSortFilter_Fragment");
				fragmentTransaction.commit();
				
//				booksListFragment = new Books_ListFragment();
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				booksListFragment.setArguments(bundle);
//				fragmentTransaction.replace(R.id.Container_For_Fragments, booksListFragment, "Books_ListFragment");
//				fragmentTransaction.addToBackStack("Books_ListFragment");
//				fragmentTransaction.commit();
			}
		}
	}
	
	private void getOverflowMenu()
	{
	     try
	     {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey"); //Should give the same string only
	        
	        if(menuKeyField != null)
	        {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.for_searchview, menu);
 
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        
        /*searchView.setOnQueryTextListener(new OnQueryTextListener()
        {	
			@Override
			public boolean onQueryTextSubmit(String arg0)
			{
				Log.d("onQueryTextSubmit", "onQueryTextSubmit");
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String arg0)
			{
				Log.d("onQueryTextChange", "onQueryTextChange");
				
				loadSuggestion();
				
				return true;
			}
		});*/
        
        return super.onCreateOptionsMenu(menu);
    }
	
	/*public void loadSuggestion()
	{
		String[] columns = new String[] { "_id", "text" };
        Object[] temp = new Object[] { 0, "default" };

        MatrixCursor cursor = new MatrixCursor(columns);

        for(int i = 0; i < booksNamesList.size(); i++)
        {
            temp[0] = i;
            temp[1] = booksNamesList.get(i); // replaced s with i as s not used anywhere.

            cursor.addRow(temp);
        }
        
        searchView.setSuggestionsAdapter(new SearchViewAdapter(this, cursor, booksNamesList));
	}*/
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		boolean isDrawerOpen = drawerLayout.isDrawerOpen(drawerListView);
		menu.findItem(R.id.action_search).setVisible(!isDrawerOpen);
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(actionBarDrawerToggle.onOptionsItemSelected(item))
			return true;
		
		switch (item.getItemId())
		{
			case R.id.export_Menu:
			
				
				
			break;
			
			case R.id.import_Menu:
			
				File file = new File("/storage/emulated/0/");
				file = new File("Environment.getExternalStorageDirectory()");
				
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				//intent.setType("gagt/sdf");
				Uri imgUri = Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
				intent.setDataAndType(imgUri, "*/*"); 
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				
				Log.d("path", file.toString());
				
				startActivityForResult(Intent.createChooser(intent, "Choose File"), 2);
			
			break;
		
			case R.id.action_search:
				
			break;
			
			case R.id.add_Menu:
			
			break;
			
			case R.id.sort_Menu:
				
//				View menuOverFlowItem = findViewById(R.id.sort_Menu);
				PopupMenu popup_sort_Menu = new PopupMenu(getApplicationContext(), findViewById(R.id.sort_Menu));
//				PopupMenu popup_sort_Menu = new PopupMenu(getApplicationContext(), this.getCurrentFocus());
				popup_sort_Menu.getMenuInflater().inflate(R.menu.for_books_sort_items, popup_sort_Menu.getMenu());
			
				popup_sort_Menu.setOnMenuItemClickListener(new OnMenuItemClickListener()
				{	
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch (item.getItemId())
						{
							case R.id.byBookName_Menu:
							
							break;
							
							case R.id.byAuthorName_Menu:
								
							break;
							
							case R.id.byPublisher_Menu:
								
							break;
							
							case R.id.byDate_Menu:
								
							break;
							
							case R.id.byDontSort_Menu:
								
							break;
						}
						
						return false;
					}
				});
				popup_sort_Menu.show();
				
			break;
			
			case R.id.filter_Menu:
				
				PopupMenu popup_filter_Menu = new PopupMenu(getApplicationContext(), findViewById(R.id.filter_Menu));
				popup_filter_Menu.getMenuInflater().inflate(R.menu.for_books_filter_items, popup_filter_Menu.getMenu());
				
				popup_filter_Menu.setOnMenuItemClickListener(new OnMenuItemClickListener()
				{	
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch (item.getItemId())
						{
							case R.id.availableBooks_Menu:
							
							break;
							
							case R.id.requiredBooks_Menu:
								
							break;
							
							case R.id.linkedBooks_Menu:
								
							break;
							
							case R.id.dontFilter_Menu:
								
							break;
							
						}
						
						return false;
					}
				});
				popup_filter_Menu.show();
				
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		actionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		LocalBroadcastManager.getInstance(Books_Activity.this).registerReceiver(broadcastReceiver, new IntentFilter("myReceiver"));
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		
		LocalBroadcastManager.getInstance(Books_Activity.this).unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 2 && data != null)
			filePath = data.getData().getEncodedPath();
		
		Log.e("From onActivityResult", "filePath: "+filePath);
		
//		createTempFile(filePath);
		
//		File destFile = new File(filePath);
//		renameFileToXls(destFile);
		
		File file = new File(filePath);
		
		/*if(".xlsx".equalsIgnoreCase(filePath.substring(filePath.indexOf("."), filePath.length())))
		{
//			renameFileToXls(file);
			
			try
			{
				readXLSXFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(".xls".equalsIgnoreCase(filePath.substring(filePath.indexOf("."), filePath.length())))
		{
			try
			{
				readXLSFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}*/
	}

	@Override
	public boolean onQueryTextChange(String arg0)
	{
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0)
	{
		return false;
	}

	/*private void createTempFile(String filePath)
	{	
		String fileExtension = filePath.substring(filePath.indexOf("."));
		String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()"/sdcard/DCIM/Library.xlsx";
		Log.d("destPath", "destPath: "+destPath);
		
		File destFile = new File(destPath+"/tempFile.xlsx");
//		File destFile = new File(destPath+"/tempFile.xls");
		
		if(".xlsx".equalsIgnoreCase(fileExtension))
		{
			File file = new File(filePath);
			
			if(file.exists())
			{
				InputStream inputStream = null;
				OutputStream outputStream = null;
				
				try
				{
					inputStream = new FileInputStream(file);
					outputStream = new FileOutputStream(destFile);
					
					byte[] buffer = new byte[1024];
					int length;
					
					while((length = inputStream.read(buffer)) > 0)
					{
						outputStream.write(buffer, 0, length);
					}
					
					renameFileToXls(destFile);
//					readExcelFile(destFile);
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						inputStream.close();
						outputStream.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		else if(".xlsx".equalsIgnoreCase(fileExtension))
		{
			
		}
		else
			Toast.makeText(Books_Activity.this, "Invalid File Type !!", Toast.LENGTH_SHORT).show();
	}*/
	
	/*private void renameFileToXls(File destFile)
	{
		String filePath = destFile.getAbsolutePath();
		String newFileExtension = ".xls";
		
		String oldFileExtension = filePath.substring(filePath.indexOf("."));
		Log.d("oldFileExtension", "oldFileExtension: "+oldFileExtension);
		
		if(".xlsx".equalsIgnoreCase(oldFileExtension))
		{
			File file = new File(filePath);
			
			if(file.exists())
			{
				String newFilePath = file.getAbsolutePath();
				String fileName = file.getName();
				fileName = fileName.substring(0, fileName.indexOf("."));
				
				Log.d("fileName", "fileName: "+fileName);
				Log.d("newFilePath", "newFilePath: "+newFilePath);
				Log.d("newFilePath.substring(newFilePath.lastIndexOf(/))", "newFilePath.substring(newFilePath.lastIndexOf(/)): "+newFilePath.substring(0, newFilePath.lastIndexOf("/")+1));
				
				filePath = filePath.replace(oldFileExtension, newFileExtension);
				Log.d("filePath", "filePath: "+filePath);
				
				File file2 = new File(filePath);
				
				file.renameTo(file2);
				
				Log.d("file.getAbsolutePath()", "file.getAbsolutePath(): "+file.getAbsolutePath());
				Log.d("file2.getAbsolutePath()", "file2.getAbsolutePath(): "+file2.getAbsolutePath());
				
//				readExcelFile(file2);
			}
		}
		else
			Toast.makeText(Books_Activity.this, "Invalid file !!", Toast.LENGTH_SHORT).show();
	}*/
	
//	private void readExcelFile(File file)
//	{
//		String filePath = file.getAbsolutePath();
//		
//		Log.d("filePath.indexOf(\".\")", "filePath.indexOf(\".\"): "+filePath.indexOf("."));
//		Log.d("filePath.indexOf(\".\")", "filePath.indexOf(\".\"): "+filePath.substring(filePath.indexOf("."), filePath.length()));
//		
////		File file = new File(filePath);
//		
//		Workbook workbook;
//		
//		try
//		{
//			workbook = Workbook.getWorkbook(file);
//			
//			Sheet sheet = workbook.getSheet(0);
//			
//			for (int j = 0; j < sheet.getColumns(); j++)
//			{
//		        for (int i = 0; i < sheet.getRows(); i++)
//		        {
//		          Cell cell = sheet.getCell(j, i);
//		          CellType type = cell.getType();
//		          
//		          if (type == CellType.LABEL)
//		          {
//		            System.out.println("I got a label " + cell.getContents());
//		          }
//		          
//		          if (type == CellType.NUMBER)
//		          {
//		            System.out.println("I got a number "+ cell.getContents());
//		          }
//		          
//		          if (type == CellType.STRING_FORMULA)
//		          {
//		            System.out.println("I got a Stirng formula "+ cell.getContents());
//		          }
//		        }
//			}
//			
//			file.delete();
//			
//			if(file.exists())
//				Log.e("inside if file.exists()", "file.getAbsolutePath(): "+file.getAbsolutePath());
//		}
//		catch (BiffException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//		
//		
//		/*if(".xlsx".equalsIgnoreCase(filePath.substring(filePath.indexOf("."), filePath.length())))
//		{
//			try
//			{
//				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
//				
//				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
//				
//				Iterator<Row> rowIterator = xssfSheet.iterator();
//				
//				while(rowIterator.hasNext())
//				{
//					Row row = rowIterator.next();
//					
//					Iterator<Cell> cellIterator = row.iterator();
//					
//					while(cellIterator.hasNext())
//					{
//						Cell cell = cellIterator.next();
//						
//						switch (cell.getCellType())
//						{
//	                    	case Cell.CELL_TYPE_STRING:
//	                    		System.out.print(cell.getStringCellValue() + "\t");
//	                        break;
//	                    	
//	                    	case Cell.CELL_TYPE_NUMERIC:
//	                    		System.out.print(cell.getNumericCellValue() + "\t");
//	                        break;
//	                    
//	                    	case Cell.CELL_TYPE_BOOLEAN:
//	                    		System.out.print(cell.getBooleanCellValue() + "\t");
//	                        break;
//	                    
//	                    	default :
//	                    	break;
//	                    }
//	                }
//	                	System.out.println("");
//				}
//				
//				//xssfWorkbook.close();
//			}
//			catch (FileNotFoundException e)
//			{
//				e.printStackTrace();
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		}
//		else if(".xls".equalsIgnoreCase(filePath.substring(filePath.indexOf("."), filePath.length())))
//		{
//			InputStream excelFileToRead = null;
//			
//			try
//			{
//				excelFileToRead = new FileInputStream(filePath);
//			}
//			catch (FileNotFoundException e)
//			{
//				e.printStackTrace();
//			}
//			
//			HSSFWorkbook wb = null;
//			
//			try
//			{
//				wb = new HSSFWorkbook(excelFileToRead);
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//				
//				try
//				{
//					excelFileToRead.close();
//				}
//				catch (IOException e1)
//				{
//					e1.printStackTrace();
//				}
//			}
//			 
//			HSSFSheet sheet = wb.getSheetAt(0);
//			
//			HSSFRow row;
//			HSSFCell cell;
//			 
//			Iterator rows = sheet.rowIterator();
//			 
//			while (rows.hasNext())
//			{
//				row = (HSSFRow) rows.next();
//			
//				Iterator cells = row.cellIterator();
//			
//				while (cells.hasNext())
//				{
//					cell=(HSSFCell) cells.next();
//			
//					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
//					{
//						System.out.print(cell.getStringCellValue()+" ");
//					}
//					else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
//					{
//						System.out.print(cell.getNumericCellValue()+" ");
//					}
//					else
//					{
//						//U Can Handel Boolean, Formula, Errors
//					}
//				}
//				System.out.println();
//			}
//			
//			try
//			{
////				wb.close();
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		}
//		else
//			Toast.makeText(Books_Activity.this, "Invalid File Type", Toast.LENGTH_SHORT).show();*/
//	}
	
	/*public void readXLSXFile() throws IOException
	{
		InputStream ExcelFileToRead = new FileInputStream(filePath);
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFWorkbook test = new XSSFWorkbook(); 
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
		
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
				}
				else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}
	}
	
	public void readXLSFile() throws IOException
	{
		Log.e("From readXLSFile", "filePath: "+filePath);
		
		InputStream ExcelFileToRead = new FileInputStream(filePath);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			
			while (cells.hasNext())
			{
				cell=(HSSFCell) cells.next();
		
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}
	
	}*/

}