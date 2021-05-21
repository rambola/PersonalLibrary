package com.example.homelibrary;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
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

public class AddNewBook_Fragment extends Fragment implements OnClickListener
{
	EditText bookNameET, bookVersionET, authorNameET, authorEmailET, authorLandlineET, authorMobileET, authorWebSiteET, publisherNameET, publisherEmailET, publisherLandLineET, publisherMobileET, publisherWebSiteET;
	TextView linkTV, authorInfoTV, publisherInfoTV;
	ImageView bookImageView;
	Spinner spinner;
	Button addBookBtn, deleteBookImageBtn;
	ScrollView scrollView;
	
	static int bookNo = 0;
	String book, bookStatus = "", bookName, bookImagePath = "", bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink;
	Bitmap bitmap;
	byte[] bookImage;
	
	
	SharedPreferences sharedPreferences;
	Editor editor;
	Books_Database books_Database;
	File file;
	FragmentManager fragmentManager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);
		
		View view = inflater.inflate(R.layout.adding_new_book, null); 
		
		scrollView = (ScrollView) view.findViewById(R.id.addBook_scrollView);
		spinner = (Spinner) view.findViewById(R.id.addBook_status_Spinner);
		bookImageView = (ImageView) view.findViewById(R.id.addBook_imageView);
		linkTV = (TextView) view.findViewById(R.id.addBook_bookLink_textView);
		addBookBtn = (Button) view.findViewById(R.id.addBook_addBookBtn);
		deleteBookImageBtn = (Button) view.findViewById(R.id.deleteBookImage_Btn);
		
		bookNameET = (EditText) view.findViewById(R.id.addBook_bookName_editText);
		bookVersionET = (EditText) view.findViewById(R.id.addBook_bookVersion_editText);
		authorNameET = (EditText) view.findViewById(R.id.addBook_bookAuthor_editText);
		publisherNameET = (EditText) view.findViewById(R.id.addBook_bookPublisher_editText);
		authorInfoTV = (TextView) view.findViewById(R.id.addBook_authorInfo_textView);
		authorEmailET = (EditText) view.findViewById(R.id.addBook_authorMail_editText);
		authorLandlineET = (EditText) view.findViewById(R.id.addBook_authorLPhone_editText);
		authorMobileET = (EditText) view.findViewById(R.id.addBook_authorMPhone_editText);
		authorWebSiteET = (EditText) view.findViewById(R.id.addBook_authorWebsite_editText);
		publisherInfoTV = (TextView) view.findViewById(R.id.addBook_publisherInfo_textView);
		publisherEmailET = (EditText) view.findViewById(R.id.addBook_publisherMail_editText);
		publisherLandLineET = (EditText) view.findViewById(R.id.addBook_publisherLPhone_editText);
		publisherMobileET = (EditText) view.findViewById(R.id.addBook_publisherMPhone_editText);
		publisherWebSiteET = (EditText) view.findViewById(R.id.addBook_publisherWebsite_editText);
		
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
		
		getActivity().invalidateOptionsMenu();
		
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setHorizontalScrollBarEnabled(false);
		
		fragmentManager = getFragmentManager();
		books_Database = new Books_Database(getActivity());
		addBookBtn.setOnClickListener(this);
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
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.addBook_addBookBtn:
			
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
				
				Log.e("", "");
				Log.e("Book Name before Inserting", "name "+bookName);
//				Log.e("Book bookVersion", "version "+bookVersion);
//				Log.e("Book bookStatus", "status "+bookStatus);
//				Log.e("Book bookAuthorName", "authorname "+bookAuthorName);
//				Log.e("Book bookImagePath", "imagePath "+bookImagePath);
//				Log.e("Book bookAuthorInfo", "authorInfo "+bookAuthorInfo);
//				Log.e("Book bookAuthorEmail", "authorEmail "+bookAuthorEmail);
//				Log.e("Book bookAuthorLandLine", "authorLand "+bookAuthorLandLine);
//				Log.e("Book bookAuthorMobile", "authorMobile "+bookAuthorMobile);
//				Log.e("Book bookAuthorWebsite", "authorsite "+bookAuthorWebsite);
//				Log.e("Book bookPublisherName", "publishername "+bookPublisherName);
//				Log.e("Book bookPublisherInfo", "publisherInfo "+bookPublisherInfo);
//				Log.e("Book bookPublisherEmail", "publisherEmail "+bookPublisherEmail);
//				Log.e("Book bookPublisherLandLine", "publisherLand "+bookPublisherLandLine);
//				Log.e("Book bookPublisherMobile", "publishermobile "+bookPublisherMobile);
//				Log.e("Book bookPublisherWebsite", "publishersite "+bookPublisherWebsite);
				
				sharedPreferences = getActivity().getSharedPreferences("count", Activity.MODE_PRIVATE);
				
				bookNo = sharedPreferences.getInt("bookNum", 0);
				editor = sharedPreferences.edit();
				
				bookNo++;
				book = "Book"+bookNo;
				
				editor.putInt("bookNum", bookNo);
				
				Log.e("Before inserting", "Book Number : "+book);
				Log.e("After keeping in sharedPreference", "Book Number : "+bookNo);
				
				
				long i = books_Database.insertValues(book, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink);
			
				if(i > 0)
				{					
//					getActivity().getFragmentManager().beginTransactio().remove(this).commit();

					
					int availableCount = sharedPreferences.getInt("availableCount", 0);
					int requiredCount = sharedPreferences.getInt("requiredCount", 0);
					int linkedCount = sharedPreferences.getInt("linkedCount", 0);
					
					if(bookStatus.equals("Available"))
						editor.putInt("availableCount", ++availableCount);
					else if(bookStatus.equals("Required"))
						editor.putInt("requiredCount", ++requiredCount);
					else if(bookStatus.equals("Linked"))
						editor.putInt("linkedCount", ++linkedCount);
					
					int totalCount = availableCount + requiredCount + linkedCount;
					editor.putInt("totalCount", totalCount);
					editor.commit();
					
					Log.e("After Adding Book", "After Adding Book");
					Log.e("Book AvailableCount", ""+availableCount);
					Log.e("Book requiredCount", ""+requiredCount);
					Log.e("Book linkedCount", ""+linkedCount);
					Log.e("Book totalCount", ""+totalCount);
					
					callToReceiver();
					
					getActivity().getFragmentManager().popBackStack();
				}
				
			break;
			
			case R.id.addBook_bookLink_textView:
				
//				file = new File("/storage/emulated/0/");
				file = new File("Environment.getExternalStorageDirectory()");
				
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("gagt/sdf");
//				File file = new File(Environment.getExternalStorageDirectory(),"");
				Log.d("path", file.toString());
//				intent.setDataAndType(Uri.fromFile(file), "*/*");
				
				startActivityForResult(Intent.createChooser(intent, "Choose File"), 6);

			break;
			
			case R.id.addBook_imageView:
				
				AlertDialog.Builder builder = new Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
				builder.setTitle("Choose Image");
//				builder.setIcon(R.drawable.familiaicon);
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
//						Intent i=new Intent();
//						i.setType("image/*");
//						i.setAction(Intent.ACTION_GET_CONTENT);
//						startActivityForResult(Intent.createChooser(i, "Select Picture"), 4);
						
						 Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		                 startActivityForResult(i, 4);
					}
				});
			
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
				
			break;
		
			case R.id.deleteBookImage_Btn:
			
				bookImageView.setImageResource(R.drawable.default_book_cover1);
				deleteBookImageBtn.setVisibility(View.INVISIBLE);
				bookImagePath = "";
				
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
		Log.d("callToReceiver", "Receiver Called");
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
		      	File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

               try
               {
            	    bookImagePath = f.getAbsolutePath().toString();
            	    Log.e("Image Path is ", bookImagePath);
                 	
            	    bitmap = decodeSampledBitmapFromResource(f.getAbsolutePath(), 120, 190);
		                    	
		            bookImageView.setImageBitmap(bitmap);
		            deleteBookImageBtn.setVisibility(View.VISIBLE);
		            
//		            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//			        bookImage= baos.toByteArray();
//			        encodedImage = Base64.encodeToString(bookImage, Base64.DEFAULT);
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
	            // h=1;
	        	//imgui = selectedImage;
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
	            
//	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//	            bookImage= baos.toByteArray();
//	            encodedImage = Base64.encodeToString(bookImage, Base64.DEFAULT);         
		     }
		     else if(requestCode == 6)
		     {
//		    	 Uri filePathUri = data.getData();
//		    	 
//		    	 ContentResolver cr = getActivity().getContentResolver();
//		    	 Uri uri = MediaStore.Files.getContentUri("external");
//
//		    	 // every column, although that is huge waste, you probably need
//		    	 // BaseColumns.DATA (the path) only.
//		    	 String[] projection = {MediaStore.Files.FileColumns.DATA};
//
//		    	 // exclude media files, they would be here also.
//		    	 String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + " = ?" ;
//		    	 String[] selectionArgs = {""+MediaStore.Files.FileColumns.MEDIA_TYPE_NONE}; // there is no ? in selection so null here
//
//		    	 String sortOrder = null; // unordered
//		    	 
//		    	 Cursor allNonMediaFiles = cr.query(uri, projection, selection, selectionArgs, sortOrder);
//
//		    	 String[] filePathUri = allNonMediaFiles.getColumnNames();
//		    	 
//		    	 for(int i = 0; i<filePathUri.length; i++)
//		    		 Log.e("Uri is "+i, ""+filePathUri[i]);
//		    	 
//		    	 Log.e("filePathUri is ", ""+filePathUri);
//		    	 Log.e("allNonMediaFiles is ", ""+allNonMediaFiles);
//		    	 Log.e("uri is ", ""+uri);
//		    	 Log.e("filePathUri is ", ""+allNonMediaFiles.get(allNonMediaFiles.getColumnIndex(projection[0])));
//		    	 
//		    	 String [] proj={MediaStore.Files.FileColumns.DATA};
//		         Cursor cursor = cr.query( filePathUri,
//		                         proj, // Which columns to return
//		                         null,       // WHERE clause; which rows to return (all rows)
//		                         null,       // WHERE clause selection arguments (none)
//		                         null); // Order-by clause (ascending by name)
//		         int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//		         cursor.moveToFirst();
//
//		         Log.e("cursor.getString(column_index)", ""+cursor.getString(column_index));
//		         Log.e("cursor.getString(column_index)", ""+data.getData().getPath());
		         
		         bookLink = data.getData().getPath().toString();
		         linkTV.setText(bookLink);
		     }
		}
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
	
	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{	
		menu.findItem(R.id.action_search).setVisible(false);
		
		super.onPrepareOptionsMenu(menu);
	}
	
}