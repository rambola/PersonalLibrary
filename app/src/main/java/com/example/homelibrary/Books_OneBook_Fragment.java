package com.example.homelibrary;

import java.io.File;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressWarnings("unused")
public class Books_OneBook_Fragment extends Fragment implements OnClickListener
{
	EditText bookNameET, bookVersionET, authorNameET,  authorEmailET, authorLandlineET, authorMobileET, authorWebSiteET, publisherNameET, publisherEmailET, publisherLandLineET, publisherMobileET, publisherWebSiteET;
	TextView linkTV, authorInfoTV, publisherInfoTV;
	ImageView bookImageView;
	Spinner spinner;
	ScrollView scrollView;
	
	Bitmap bitmap;
	File file;
	ActionBar actionBar;
	ActionBarDrawerToggle actionBarDrawerToggle;
	
	BooksInfoClass booksInfoClass;
	ArrayList<BooksInfoClass> arrayList;
	Books_Database books_Database;
	SharedPreferences sharedPreferences;
	Editor editor;
	
	static int availableCount, requiredCount, linkedCount, totalCount;
	int position;
	String clickedItem;
	String bookNumber, bookStatus = "", bookName, bookImagePath = "", bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink;
	String[] bStatus = {"Book Status","Available","Required","Linked"};
	ArrayList<String> statusList = new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);
		
		Log.e("onCreativeView", "onCreativeView");

		if(getArguments().getString("From").equals("Books_ListFragment"))
			position = getArguments().getInt("Position");
		else
			position = getArguments().getInt("Position");
		
		clickedItem = getArguments().getString("From");
		
		Log.e("getArguments().getInt()", "Position "+position);
		Log.e("getArguments().getString()", "clickedItem "+clickedItem);
		
		View view = inflater.inflate(R.layout.for_books_onebook_fragment, null); 
		
		statusList.add("Book Status");
		statusList.add("Available");
		statusList.add("Required");
		statusList.add("Linked");
		
		scrollView = (ScrollView) view.findViewById(R.id.forOneBookInfo_scrollView);
		spinner = (Spinner) view.findViewById(R.id.forOneBookInfo_status_Spinner);
		bookImageView = (ImageView) view.findViewById(R.id.forOneBookInfo_book_imageView);
		linkTV = (TextView) view.findViewById(R.id.forOneBookInfo_bookLink_textView);
		
		bookNameET = (EditText) view.findViewById(R.id.forOneBookInfo_bookName_editText);
		bookVersionET = (EditText) view.findViewById(R.id.forOneBookInfo_bookVersion_editText);
		authorNameET = (EditText) view.findViewById(R.id.forOneBookInfo_bookAuthor_editText);
		publisherNameET = (EditText) view.findViewById(R.id.forOneBookInfo_bookPublisher_editText);
		authorInfoTV = (TextView) view.findViewById(R.id.forOneBookInfo_authorInfo_textView);
		authorEmailET = (EditText) view.findViewById(R.id.forOneBookInfo_authorMail_editText);
		authorLandlineET = (EditText) view.findViewById(R.id.forOneBookInfo_authorLPhone_editText);
		authorMobileET = (EditText) view.findViewById(R.id.forOneBookInfo_authorMPhone_editText);
		authorWebSiteET = (EditText) view.findViewById(R.id.forOneBookInfo_authorWebsite_editText);
		publisherInfoTV = (TextView) view.findViewById(R.id.forOneBookInfo_publisherInfo_textView);
		publisherEmailET = (EditText) view.findViewById(R.id.forOneBookInfo_publisherMail_editText);
		publisherLandLineET = (EditText) view.findViewById(R.id.forOneBookInfo_publisherLPhone_editText);
		publisherMobileET = (EditText) view.findViewById(R.id.forOneBookInfo_publisherMPhone_editText);
		publisherWebSiteET = (EditText) view.findViewById(R.id.forOneBookInfo_publisherWebsite_editText);

		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		Log.e("onActivityCreated", "onActivityCreated");

		getActivity();
		sharedPreferences = getActivity().getSharedPreferences("count", Activity.MODE_PRIVATE);
		
		position = getArguments().getInt("Position");
		Log.e("getArguments().getInt()", "Position "+position);
		
		clickedItem = getArguments().getString("From");
		Log.e("getArguments().getString()", "clickedItem "+clickedItem);
		
		getActivity().getActionBar().setTitle("Book Info");
		
		statusList.add("Book Status");
		statusList.add("Available");
		statusList.add("Required");
		statusList.add("Linked");
		
		actionBar = getActivity().getActionBar();
		getActivity().invalidateOptionsMenu();
		
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setHorizontalScrollBarEnabled(false);
		
		arrayList = new ArrayList<BooksInfoClass>();
		books_Database = new Books_Database(getActivity());
		
		if(arrayList != null)
			arrayList.clear();
		
		if(clickedItem.trim().equals("Available"))
			arrayList = books_Database.getBooks_FilterByAvailable();
		else if(clickedItem.trim().equals("Required"))
			arrayList = books_Database.getBooks_FilterByRequired();
		else if(clickedItem.trim().equals("Linked"))
			arrayList = books_Database.getBooks_FilterByLinked();
		else if(clickedItem.trim().equals("By Date"))
			arrayList = books_Database.getBooks_SortByDate();
		else if(clickedItem.trim().equals("By Book Name"))
			arrayList = books_Database.getBooks_SortByBookName();
		else if(clickedItem.trim().equals("By Author Name"))
			arrayList = books_Database.getBooks_SortByAuthorName();
		else if(clickedItem.trim().equals("By Publisher Name"))
			arrayList = books_Database.getBooks_SortByPublisherName();
		else
			arrayList = books_Database.getAllBooksDetails();
		
		booksInfoClass = arrayList.get(position);
		
		
		Log.e("Books_OneBook_Fragment", "Books_OneBook_Fragment");
		Log.e("Book Name before Inserting", "name "+booksInfoClass.getBookName());
		Log.e("Book bookVersion", "version "+booksInfoClass.getBookVersion());
		Log.e("Book bookStatus", "status "+booksInfoClass.getBookStatus());
		Log.e("Book bookAuthorName", "authorname "+booksInfoClass.getBookAuthorName());
		Log.e("Book bookImagePath", "imagePath "+booksInfoClass.getBookImagePath());
		Log.e("Book bookAuthorInfo", "authorInfo "+booksInfoClass.getBookAuthorInfo());
		Log.e("Book bookAuthorEmail", "authorEmail "+booksInfoClass.getBookAuthorEmail());
		Log.e("Book bookAuthorLandLine", "authorLand "+booksInfoClass.getBookAuthorLandLine());
		Log.e("Book bookAuthorMobile", "authorMobile "+booksInfoClass.getBookAuthorMobile());
		Log.e("Book bookAuthorWebsite", "authorsite "+booksInfoClass.getBookAuthorWebsite());
		Log.e("Book bookPublisherName", "publishername "+booksInfoClass.getBookPublisherName());
		Log.e("Book bookPublisherInfo", "publisherInfo "+booksInfoClass.getBookPublisherInfo());
		Log.e("Book bookPublisherEmail", "publisherEmail "+booksInfoClass.getBookPublisherEmail());
		Log.e("Book bookPublisherLandLine", "publisherLand "+booksInfoClass.getBookPublisherLandLine());
		Log.e("Book bookPublisherMobile", "publishermobile "+booksInfoClass.getBookPublisherMobile());
		Log.e("Book bookPublisherWebsite", "publishersite "+booksInfoClass.getBookPublisherWebsite());
		
		bookNameET.setText(booksInfoClass.getBookName());
		bookVersionET.setText(booksInfoClass.getBookVersion());
		authorNameET.setText(booksInfoClass.getBookAuthorName());
		authorInfoTV.setText(booksInfoClass.getBookAuthorInfo());
		authorEmailET.setText(booksInfoClass.getBookAuthorEmail());
		authorLandlineET.setText(booksInfoClass.getBookAuthorLandLine());
		authorMobileET.setText(booksInfoClass.getBookAuthorMobile());
		authorWebSiteET.setText(booksInfoClass.getBookAuthorWebsite());
		publisherNameET.setText(booksInfoClass.getBookPublisherName());
		publisherInfoTV.setText(booksInfoClass.getBookPublisherInfo());
		publisherEmailET.setText(booksInfoClass.getBookPublisherEmail());
		publisherLandLineET.setText(booksInfoClass.getBookPublisherLandLine());
		publisherMobileET.setText(booksInfoClass.getBookPublisherMobile());
		publisherWebSiteET.setText(booksInfoClass.getBookPublisherWebsite());
		linkTV.setText(booksInfoClass.getBookLink());
		
		bookStatus = booksInfoClass.getBookStatus();
		spinner.setSelection(statusList.indexOf(bookStatus));
		
		bookImagePath = booksInfoClass.getBookImagePath();
		
		if(bookImagePath.equals(""))
			bookImageView.setImageResource(R.drawable.default_book_cover1);
		else
		{
			bitmap = decodeSampledBitmapFromResource(bookImagePath, 120, 190);
			bookImageView.setImageBitmap(bitmap);
//			bitmap.recycle();
		}
		
		if(!linkTV.getText().toString().equalsIgnoreCase("Link"))
			linkTV.setOnClickListener(this);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.menu_for_books_onebook_fragment, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.delete_Menu:
			
				availableCount = sharedPreferences.getInt("availableCount", 0);
				requiredCount = sharedPreferences.getInt("requiredCount", 0);
				linkedCount = sharedPreferences.getInt("linkedCount", 0);
				
				editor = sharedPreferences.edit();
				
				AlertDialog.Builder builder=new Builder(getActivity());
				builder.setMessage("Confirm Your Deletion !!");
				
				builder.setPositiveButton("Delete", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						int deletedRow = books_Database.deleteBook(booksInfoClass.getBookNumber());
						
						if(deletedRow > 0)
						{
							String spinnerStatus = spinner.getSelectedItem().toString(); 
							
							if(spinnerStatus.equals("Available"))
								editor.putInt("availableCount", --availableCount);

							else if(spinnerStatus.equals("Required"))
								editor.putInt("requiredCount", --requiredCount);

							else if(spinnerStatus.equals("Linked"))
								editor.putInt("linkedCount", --linkedCount);
			
							totalCount = availableCount + requiredCount + linkedCount;
							
							editor.putInt("totalCount", totalCount);
							editor.commit();
							
							callToReceiver();
							
							Toast.makeText(getActivity(), "Book Deleted !!", Toast.LENGTH_SHORT).show();
							
							getActivity().getFragmentManager().popBackStack();
						}
						else
							Toast.makeText(getActivity(), "Book Not Deleted !!", Toast.LENGTH_SHORT).show();

						dialog.dismiss();
					}
				});
				
				builder.setNegativeButton("No", new DialogInterface.OnClickListener()
				{	
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
				
				AlertDialog alertDialog=builder.create();
				alertDialog.show();
				
				Log.e("After Deleting", "After Deleting Book");
				Log.e("Book AvailableCount", ""+availableCount);
				Log.e("Book requiredCount", ""+requiredCount);
				Log.e("Book linkedCount", ""+linkedCount);
				Log.e("Book totalCount", ""+totalCount);

			break;
			
			case R.id.edit_Menu:
				
				getActivity().getFragmentManager().popBackStack();
				
				OneBook_Edit_Fragment oneBook_Edit_Fragment = (OneBook_Edit_Fragment) getFragmentManager().findFragmentByTag("OneBook_Edit_Fragment");
				Bundle bundle = new Bundle();
				bundle.putInt("Position", position);
				bundle.putString("From", "Books_ListFragment");
				
				if(oneBook_Edit_Fragment == null)
				{
					oneBook_Edit_Fragment = new OneBook_Edit_Fragment();
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					oneBook_Edit_Fragment.setArguments(bundle);
					fragmentTransaction.replace(R.id.Container_For_Fragments, oneBook_Edit_Fragment, "OneBook_Edit_Fragment");
					fragmentTransaction.addToBackStack("OneBook_Edit_Fragment");
					fragmentTransaction.commit();
				}
				else
				{
					oneBook_Edit_Fragment = new OneBook_Edit_Fragment();
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					oneBook_Edit_Fragment.setArguments(bundle);
					fragmentTransaction.replace(R.id.Container_For_Fragments, oneBook_Edit_Fragment, "OneBook_Edit_Fragment");
					fragmentTransaction.addToBackStack("OneBook_Edit_Fragment");
					fragmentTransaction.commit();
				}

			break;
	
			default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		menu.findItem(R.id.action_search).setVisible(false);
		
		super.onPrepareOptionsMenu(menu);
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.forOneBookInfo_bookLink_textView:
				
//				String path = linkTV.getText().toString();
//                
//				Log.d("file path", path);
//				
//				File file = new File(path);
//				
//				Intent intent = new Intent();
//                intent.setAction(android.content.Intent.ACTION_VIEW);
//                
//                intent.setData(Uri.fromFile(file));
//                Intent j = Intent.createChooser(intent, "Choose an application to open with:");
////                getActivity().startActivity(intent);
//                getActivity().startActivity(j);

                String path = linkTV.getText().toString();

                File file = new File(path);
                
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
               
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                
                String ext = file.getName().substring(file.getName().indexOf(".")+1);
                String type = mime.getMimeTypeFromExtension(ext);
              
                intent.setDataAndType(Uri.fromFile(file), type);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                Intent j = Intent.createChooser(intent, "Choose an application to open with:");
                startActivity(j);
                
//                getActivity().startActivityFromFragment(getParentFragment(), j, 123456);
//                getActivity().startActivityForResult(intent, 123456);
                
			break;
			
			default:
			break;
		}
	}
	
	private void callToReceiver()
	{
		Intent intent = new Intent("myReceiver");
//		intent.setAction("com.example.homelibrary");
		LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
//		getActivity().sendBroadcast(intent);
		Log.d("callToReceiver, After Deleting Book", "Receiver Called");
	}
	
	public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight)
	{
	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(path, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    
	    return  BitmapFactory.decodeFile(path, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
    
		if (height > reqHeight || width > reqWidth)
		{
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
			{
				inSampleSize *= 2;
			}
		}
		
		return inSampleSize;
	}
	
}