package com.example.homelibrary;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ForBooksListFragment_Adapter extends BaseAdapter
{
	int resoruceID_forBooksActivity;
	ArrayList<BooksInfoClass> listFragment;
	Context context;
	Bitmap bitmap;
	
	public ForBooksListFragment_Adapter(Activity activity, int forBooksActivity, ArrayList<BooksInfoClass> array_ListFragment)
	{
		this.context = activity;
		this.listFragment = array_ListFragment;
		this.resoruceID_forBooksActivity = forBooksActivity;
	}
	
	@Override
	public int getCount()
	{
		return listFragment.size();
	}
	
	@Override
	public Object getItem(int arg0)
	{
		return listFragment.get(arg0);
	}
	
	@Override
	public long getItemId(int arg0)
	{
		return 0;
	}
	
	public void updateAdapter(Activity activity, int forBooksActivity, ArrayList<BooksInfoClass> array_ListFragment)
	{
		this.context = activity;
		this.listFragment = array_ListFragment;
		this.resoruceID_forBooksActivity = forBooksActivity;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup viewGroup)
	{
		BookHolder bookHolder;
		
		if(view == null)
		{
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			view = layoutInflater.inflate(resoruceID_forBooksActivity, null);
			
			bookHolder = new BookHolder();
			
			bookHolder.bImageIV = (ImageView) view.findViewById(R.id.for_books_activity_imageView);
			bookHolder.bStatusIV = (ImageView) view.findViewById(R.id.for_books_activity_bookStatus_imageView);
			bookHolder.bNameTV = (TextView) view.findViewById(R.id.for_books_activity_bookName_textView);
			bookHolder.bVersionTV = (TextView) view.findViewById(R.id.for_books_activity_bookVersion_textView);
			bookHolder.bAuthorTV = (TextView) view.findViewById(R.id.for_books_activity_bookAuthor_textView);
			bookHolder.bDateTimeTV = (TextView) view.findViewById(R.id.for_books_activity_bookUploaded_Date_Time_textView);
			
			view.setTag(bookHolder);
		}
		else
			bookHolder = (BookHolder) view.getTag();
		
		BooksInfoClass booksInfoClass = listFragment.get(position);
		
		bookHolder.bNameTV.setText(booksInfoClass.getBookName());
		bookHolder.bVersionTV.setText(booksInfoClass.getBookVersion());
		bookHolder.bAuthorTV.setText(booksInfoClass.getBookAuthorName());
		bookHolder.bDateTimeTV.setText(booksInfoClass.getBookDateTime());
		
		Log.e("Adapter Name", ""+booksInfoClass.getBookName());
		Log.e("Adapter Version", ""+booksInfoClass.getBookVersion());
		Log.e("Adapter Author", ""+booksInfoClass.getBookAuthorName());
		Log.e("Adapter Date", ""+booksInfoClass.getBookDateTime());
		Log.e("Adapter Image Path", ""+booksInfoClass.getBookImagePath());
		Log.e("Adapter Status", ""+booksInfoClass.getBookStatus());
		
		if(booksInfoClass.getBookImagePath().equals(""))
			bookHolder.bImageIV.setImageResource(R.drawable.default_book_cover1);
		else
		{
			bitmap = decodeSampledBitmapFromResource(booksInfoClass.getBookImagePath(), 120, 190);
			bookHolder.bImageIV.setImageBitmap(bitmap);
		}
		
		if(booksInfoClass.getBookStatus().equals("Available"))
			bookHolder.bStatusIV.setImageResource(R.drawable.available);
		else if(booksInfoClass.getBookStatus().equals("Required"))
			bookHolder.bStatusIV.setImageResource(R.drawable.required);
		else if(booksInfoClass.getBookStatus().equals("Linked"))
			bookHolder.bStatusIV.setImageResource(R.drawable.page_white_link);
		else
			bookHolder.bStatusIV.setVisibility(View.INVISIBLE);
		
		return view;
	}
	
	class BookHolder
	{
		ImageView bImageIV, bStatusIV;
		TextView bNameTV, bVersionTV, bAuthorTV, bDateTimeTV;
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
