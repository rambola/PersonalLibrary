package com.example.homelibrary;

import java.io.File;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
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
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OneBook_Edit_Fragment extends Fragment implements OnClickListener
{
	EditText bookNameET, bookVersionET, authorNameET, authorEmailET, authorLandlineET, authorMobileET, authorWebSiteET, publisherNameET, publisherEmailET, publisherLandLineET, publisherMobileET, publisherWebSiteET;
	TextView linkTV, authorInfoTV, publisherInfoTV;
	ImageView bookImageView;
	Button updateBookBtn, deleteBookImageBtn;
	Spinner spinner;
	ScrollView scrollView;
	
	Bitmap bitmap, bitmap2;
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
	String bookNumber, noBookStatus, bookStatus = "", bookName, bookImagePath = "", bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink;
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
		
		View view = inflater.inflate(R.layout.for_onebook_edit, null); 
		
		statusList.add("Book Status");
		statusList.add("Available");
		statusList.add("Required");
		statusList.add("Linked");
		
		scrollView = (ScrollView) view.findViewById(R.id.forOneBookEdit_scrollView);
		spinner = (Spinner) view.findViewById(R.id.forOneBookEdit_status_Spinner);
		bookImageView = (ImageView) view.findViewById(R.id.forOneBookEdit_book_imageView);
		linkTV = (TextView) view.findViewById(R.id.forOneBookEdit_bookLink_textView);
		updateBookBtn = (Button) view.findViewById(R.id.forOneBookEdit_updateBtn);
		deleteBookImageBtn = (Button) view.findViewById(R.id.forOneBookEdit_deleteOneBookImage_Btn);
		
		bookNameET = (EditText) view.findViewById(R.id.forOneBookEdit_bookName_editText);
		bookVersionET = (EditText) view.findViewById(R.id.forOneBookEdit_bookVersion_editText);
		authorNameET = (EditText) view.findViewById(R.id.forOneBookEdit_bookAuthor_editText);
		publisherNameET = (EditText) view.findViewById(R.id.forOneBookEdit_bookPublisher_editText);
		authorInfoTV = (TextView) view.findViewById(R.id.forOneBookEdit_authorInfo_textView);
		authorEmailET = (EditText) view.findViewById(R.id.forOneBookEdit_authorMail_editText);
		authorLandlineET = (EditText) view.findViewById(R.id.forOneBookEdit_authorLPhone_editText);
		authorMobileET = (EditText) view.findViewById(R.id.forOneBookEdit_authorMPhone_editText);
		authorWebSiteET = (EditText) view.findViewById(R.id.forOneBookEdit_authorWebsite_editText);
		publisherInfoTV = (TextView) view.findViewById(R.id.forOneBookEdit_publisherInfo_textView);
		publisherEmailET = (EditText) view.findViewById(R.id.forOneBookEdit_publisherMail_editText);
		publisherLandLineET = (EditText) view.findViewById(R.id.forOneBookEdit_publisherLPhone_editText);
		publisherMobileET = (EditText) view.findViewById(R.id.forOneBookEdit_publisherMPhone_editText);
		publisherWebSiteET = (EditText) view.findViewById(R.id.forOneBookEdit_publisherWebsite_editText);
		
		deleteBookImageBtn.setVisibility(View.INVISIBLE);
		
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.BookStatus, android.R.layout.simple_spinner_item);
//	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	    spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				bookStatus = spinner.getItemAtPosition(position).toString().trim();
				//Log.e("SpinnerItem", spinner.getItemAtPosition(position).toString().trim());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				
			}
		});

		return view;

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		Log.e("onActivityCreated", "onActivityCreated");

		position = getArguments().getInt("Position");
		Log.e("getArguments().getInt()", "Position "+position);
		
		clickedItem = getArguments().getString("From");
		Log.e("getArguments().getString()", "clickedItem "+clickedItem);
		
		statusList.add("Book Status");
		statusList.add("Available");
		statusList.add("Required");
		statusList.add("Linked");
		
		actionBar = getActivity().getActionBar();
		getActivity().invalidateOptionsMenu();
		actionBar.setTitle("Update Book");
		
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
		
		Log.e("oneBook_Edit_Fragment", "Books_OneBook_Fragment");
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
		noBookStatus = spinner.getSelectedItem().toString();
		
		bookImagePath = booksInfoClass.getBookImagePath();
		
		if(bookImagePath.equals(""))
			bookImageView.setImageResource(R.drawable.default_book_cover1);
		else
		{
			bitmap2 = decodeSampledBitmapFromResource(bookImagePath, 120, 190);
			bookImageView.setImageBitmap(bitmap2);
			deleteBookImageBtn.setVisibility(View.VISIBLE);
//			bitmap2.recycle();
		}
		
		updateBookBtn.setOnClickListener(this);
		deleteBookImageBtn.setOnClickListener(this);
		bookImageView.setOnClickListener(this);
		linkTV.setOnClickListener(this);
		
		if(!linkTV.getText().toString().equalsIgnoreCase("Link"))
		{
			linkTV.setOnLongClickListener(new OnLongClickListener()
			{		
				@Override
				public boolean onLongClick(View v)
				{
					AlertDialog.Builder builder=new Builder(getActivity());
					builder.setMessage("Is Link not Correct !!");
					builder.setPositiveButton("Delete Link",new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							bookLink = "Link";
							linkTV.setText(bookLink);
							dialog.dismiss();
						}
					});
					
					builder.setNegativeButton("No",new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.dismiss();
						}
					});
					
					AlertDialog alertDialog=builder.create();
					alertDialog.show();
					
					return false;
				}
			});
		}
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		menu.findItem(R.id.action_search).setVisible(false);
//		menu.findItem(R.id.delete_Menu).setVisible(false);
//		menu.findItem(R.id.edit_Menu).setVisible(false);
		
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.forOneBookEdit_updateBtn:
			
				if(bookNameET.getText().toString().equals(""))
					bookName = "";
				else
					bookName = bookNameET.getText().toString().trim();
				
				if(bookVersionET.getText().toString().equals(""))
					bookVersion = "";
				else
					bookVersion = bookVersionET.getText().toString().trim();
				
				if(authorNameET.getText().toString().equals(""))
					bookAuthorName = "";
				else
					bookAuthorName = authorNameET.getText().toString().trim();
				
				if(authorInfoTV.getText().toString().equals(""))
					bookAuthorInfo = "";
				else
					bookAuthorInfo = authorInfoTV.getText().toString().trim();
				
				if(authorEmailET.getText().toString().equals(""))
					bookAuthorEmail = "";
				else
					bookAuthorEmail = authorEmailET.getText().toString().trim();
				
				if(authorLandlineET.getText().toString().equals(""))
					bookAuthorLandLine = "";
				else
					bookAuthorLandLine = authorLandlineET.getText().toString().trim();
				
				if(authorMobileET.getText().toString().equals(""))
					bookAuthorMobile = "";
				else
					bookAuthorMobile = authorMobileET.getText().toString().trim();
				
				if(authorWebSiteET.getText().toString().equals(""))
					bookAuthorWebsite = "";
				else
					bookAuthorWebsite = authorWebSiteET.getText().toString().trim();
				
				if(publisherNameET.getText().toString().equals(""))
					bookPublisherName = "";
				else
					bookPublisherName = publisherNameET.getText().toString().trim();
				
				if(publisherInfoTV.getText().toString().equals(""))
					bookPublisherInfo = "";
				else
					bookPublisherInfo = publisherInfoTV.getText().toString().trim();
				
				if(publisherEmailET.getText().toString().equals(""))
					bookPublisherEmail = "";
				else
					bookPublisherEmail = publisherEmailET.getText().toString().trim();
				
				if(publisherLandLineET.getText().toString().equals(""))
					bookPublisherLandLine = "";
				else
					bookPublisherLandLine = publisherLandLineET.getText().toString().trim();
				
				if(publisherMobileET.getText().toString().equals(""))
					bookPublisherMobile = "";
				else
					bookPublisherMobile = publisherMobileET.getText().toString().trim();
				
				if(publisherWebSiteET.getText().toString().equals(""))
					bookPublisherWebsite = "";
				else
					bookPublisherWebsite = publisherWebSiteET.getText().toString().trim();
				
				bookLink = linkTV.getText().toString().trim();
				bookNumber = booksInfoClass.getBookNumber();
				
				Log.e("OneBook_Edit_Fragment", "Books_OneBook_Fragment");
				Log.e("Book Name before Inserting", "name "+bookName);
				Log.e("Book bookVersion", "version "+bookVersion);
				Log.e("Book bookStatus", "status "+bookStatus);
				Log.e("Book bookAuthorName", "authorname "+bookAuthorName);
				Log.e("Book bookImagePath", "imagePath "+bookImagePath);
				Log.e("Book bookAuthorInfo", "authorInfo "+bookAuthorInfo);
				Log.e("Book bookAuthorEmail", "authorEmail "+bookAuthorEmail);
				Log.e("Book bookAuthorLandLine", "authorLand "+bookAuthorLandLine);
				Log.e("Book bookAuthorMobile", "authorMobile "+bookAuthorMobile);
				Log.e("Book bookAuthorWebsite", "authorsite "+bookAuthorWebsite);
				Log.e("Book bookPublisherName", "publishername "+bookPublisherName);
				Log.e("Book bookPublisherInfo", "publisherInfo "+bookPublisherInfo);
				Log.e("Book bookPublisherEmail", "publisherEmail "+bookPublisherEmail);
				Log.e("Book bookPublisherLandLine", "publisherLand "+bookPublisherLandLine);
				Log.e("Book bookPublisherMobile", "publishermobile "+bookPublisherMobile);
				Log.e("Book bookPublisherWebsite", "publishersite "+bookPublisherWebsite);
				
				long i = books_Database.insertValues(bookNumber, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink);
			
				if(i > 0)
				{
					sharedPreferences = getActivity().getSharedPreferences("count", Activity.MODE_PRIVATE);
					editor = sharedPreferences.edit();
					
					availableCount = sharedPreferences.getInt("availableCount", 0);
					requiredCount = sharedPreferences.getInt("requiredCount", 0);
					linkedCount = sharedPreferences.getInt("linkedCount", 0);
					
					totalCount = sharedPreferences.getInt("totalCount", 0);
					
					
					if(noBookStatus.equals("Available"))
					{
						if(bookStatus.equals("Available"))
							editor.putInt("availableCount", availableCount);
						
						else if(bookStatus.equals("Required"))
						{
							editor.putInt("availableCount", --availableCount);
							editor.putInt("requiredCount", ++requiredCount);
						}
						else if(bookStatus.equals("Linked"))
						{
							editor.putInt("availableCount", --availableCount);
							editor.putInt("linkedCount", ++linkedCount);
						}
					}
					else if(noBookStatus.equals("Required"))
					{
						if(bookStatus.equals("Available"))
						{
							editor.putInt("availableCount", ++availableCount);
							editor.putInt("requiredCount", --requiredCount);
						}
						
						else if(bookStatus.equals("Required"))
							editor.putInt("requiredCount", requiredCount);

						else if(bookStatus.equals("Linked"))
						{
							editor.putInt("requiredCount", --requiredCount);
							editor.putInt("linkedCount", ++linkedCount);
						}
					}
					else if(noBookStatus.equals("Linked"))
					{
						if(bookStatus.equals("Available"))
						{
							editor.putInt("availableCount", ++availableCount);
							editor.putInt("linkedCount", --linkedCount);
						}
						
						else if(bookStatus.equals("Required"))
						{
							editor.putInt("requiredCount", ++requiredCount);
							editor.putInt("linkedCount", --linkedCount);
						}
						
						else if(bookStatus.equals("Linked"))
							editor.putInt("linkedCount", linkedCount);
					}

					editor.commit();
					
					callToReceiver();
					
					Log.e("After Updating", "After Updating Book");
					Log.e("Book AvailableCount", ""+availableCount);
					Log.e("Book requiredCount", ""+requiredCount);
					Log.e("Book linkedCount", ""+linkedCount);
					Log.e("Book totalCount", ""+totalCount);
					
					getActivity().getFragmentManager().popBackStack();					
				}
				else
					Toast.makeText(getActivity(), "Book not Updated !!", Toast.LENGTH_SHORT).show();
				
			break;
			
			case R.id.forOneBookEdit_bookLink_textView:
				
				file = new File("Environment.getExternalStorageDirectory()");
				
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("gagt/sdf");

				Log.d("path", file.toString());
				
				startActivityForResult(Intent.createChooser(intent, "Choose File"), 6);

			break;
			
			case R.id.forOneBookEdit_book_imageView:
				
				AlertDialog.Builder builder = new Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
				builder.setTitle("Choose Image");
				builder.setPositiveButton("Take Picture", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						
						File f = new File(android.os.Environment.getExternalStorageDirectory(), "book.jpg");

						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
						
						startActivityForResult(intent, 2);
					}
				});
				
				builder.setNegativeButton("From Galley", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						 Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		                 startActivityForResult(i, 4);
					}
				});
			
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
				
			break;
		
			case R.id.forOneBookEdit_deleteOneBookImage_Btn:
			
				bookImageView.setImageResource(R.drawable.default_book_cover1);
				deleteBookImageBtn.setVisibility(View.INVISIBLE);
				bookImagePath = "";
				
			break;
			
			default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	
		getActivity();
		if (resultCode == Activity.RESULT_OK)
	    {
			 if (requestCode == 2)
		     {    		
		      	File f = new File(android.os.Environment.getExternalStorageDirectory(), "book.jpg");

               try
               {
            	    bookImagePath = f.getAbsolutePath().toString();
            	    Log.e("Image Path is ", bookImagePath);
                 	
            	    bitmap = decodeSampledBitmapFromResource(f.getAbsolutePath(), 120, 190);
		                    	
		            bookImageView.setImageBitmap(bitmap);
		            deleteBookImageBtn.setVisibility(View.VISIBLE);

		            f.delete();
               }
               catch (Exception e)
		        {
		              e.printStackTrace();
		        }
			}
			else if (requestCode == 4)
	        {
	        	Uri selectedImage = data.getData();
	            String[] filePath = { MediaStore.Images.Media.DATA };
	            
	            Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePath[0]);

	            bookImagePath = cursor.getString(columnIndex);
	            Log.e("Image Path is ", bookImagePath);
	            
	            cursor.close();
	            
	            bitmap = decodeSampledBitmapFromResource(bookImagePath, 120, 190);
	            bookImageView.setImageBitmap(bitmap);
	            deleteBookImageBtn.setVisibility(View.VISIBLE);       
		     }
		     else if(requestCode == 6)
		     {
		         bookLink = data.getData().getPath().toString();
		         linkTV.setText(bookLink);
		     }
		}
	}
	
	private void callToReceiver()
	{
		Intent intent = new Intent("myReceiver");
//		intent.setAction("com.example.homelibrary");
		LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
//		getActivity().sendBroadcast(intent);
		Log.d("callToReceiver, After Updating Book", "Receiver Called");
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