package com.example.homelibrary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class Books_Database	extends SQLiteOpenHelper
{	
	Cursor cursor;
	SQLiteDatabase sqLiteDatabase;
	
	public static final int DATABASE_VERSION = 5;
	public static final String DATABASE_NAME = "BooksDB";
	public static final String TABLE_NAME = "BooksTB";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_BOOK_NUMBER = "BookNumber";
	public static final String COLUMN_BOOK_STATUS = "BookStatus";
	public static final String COLUMN_BOOK_NAME = "BookName";
	public static final String COLUMN_BOOK_IMAGE_PATH = "BookImagePath";
	public static final String COLUMN_BOOK_VERSION = "BookVersion";
	public static final String COLUMN_AUTHOR_NAME = "AuthorName";
	public static final String COLUMN_AUTHOR_INFO = "AuthorInfo";
	public static final String COLUMN_AUTHOR_EMAIL = "AuthorEmail";
	public static final String COLUMN_AUTHOR_LAND = "AuthorLandLine";
	public static final String COLUMN_AUTHOR_MOBILE = "AuthorMobile";
	public static final String COLUMN_AUTHOR_WEBSITE = "AuthorWebSite";
	public static final String COLUMN_PUBLISHER_NAME = "PublisherName";
	public static final String COLUMN_PUBLISHER_INFO = "PublisherInfo";
	public static final String COLUMN_PUBLISHER_EMAIL = "PublisherEmail";
	public static final String COLUMN_PUBLISHER_LAND = "PublisherLandLine";
	public static final String COLUMN_PUBLISHER_MOBILE = "PublisherMobile";
	public static final String COLUMN_PUBLISHER_WEBSITE = "PublisherWebSite";
	public static final String COLUMN_LINK = "Link";
	public static final String COLUMN_DATE_TIME = "DateTime";
	public static final String COLUMN_NULL = "NullColumn";
	
	String bookNo, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink, bookDateTime;
	int bookID; 
	
	public static final String CREATE_TABLE = "create table "+TABLE_NAME+" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_BOOK_NUMBER+" varchar(15) NOT NULL UNIQUE, "
			+COLUMN_BOOK_STATUS+" VARCHAR(9), "+COLUMN_BOOK_NAME+" STRING(60), "+COLUMN_BOOK_VERSION+" VARCHAR(40), "+COLUMN_BOOK_IMAGE_PATH+" VARCHAR(600), "
			+COLUMN_AUTHOR_NAME+" VARCHAR(25), "+COLUMN_AUTHOR_INFO+" VARCHAR(100), "+COLUMN_AUTHOR_EMAIL+" VARCHAR(45), "
			+COLUMN_AUTHOR_LAND+" INTEGER, "+COLUMN_AUTHOR_MOBILE+" INTEGER, "+COLUMN_AUTHOR_WEBSITE+" VARCHAR(40), "
			+COLUMN_PUBLISHER_NAME+" VARCHAR(25), "+COLUMN_PUBLISHER_INFO+" VARCHAR(100), "+COLUMN_PUBLISHER_EMAIL+" VARCHAR(45), "
			+COLUMN_PUBLISHER_LAND+" INTEGER, "+COLUMN_PUBLISHER_MOBILE+" INTEGER, "+COLUMN_PUBLISHER_WEBSITE+" VARCHAR(40), "
			+COLUMN_LINK+" VARCHAR(60), "+COLUMN_DATE_TIME+" VARCHAR(22), "+COLUMN_NULL+" varchar(7));";
	
	public static final String DROP_TABLE = "drop table if exists "+TABLE_NAME;
	
	public Books_Database(Context context) 
	{
		super(context, TABLE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL(DROP_TABLE);
		
		onCreate(db);
	}

	public long insertValues(String bookNo, String bookStatus, String bookName, String bookImagePath, String bookVersion, String bookAuthorName, String bookAuthorInfo, String bookAuthorEmail, String bookAuthorLandLine, String bookAuthorMobile, String bookAuthorWebsite, String bookPublisherName, String bookPublisherInfo, String bookPublisherEmail, String bookPublisherLandLine, String bookPublisherMobile, String bookPublisherWebsite, String bookLink)
	{
		SQLiteDatabase sqLiteDatabase = getWritableDatabase();
		
		Log.e("Book Name before Inserting", "name "+bookName);
//		Log.e("Book bookVersion", "version "+bookVersion);
//		Log.e("Book bookStatus", "status "+bookStatus);
//		Log.e("Book bookAuthorName", "authorname "+bookAuthorName);
//		Log.e("Book bookImagePath", "imagePath "+bookImagePath);
//		Log.e("Book bookAuthorInfo", "authorInfo "+bookAuthorInfo);
//		Log.e("Book bookAuthorEmail", "authorEmail "+bookAuthorEmail);
//		Log.e("Book bookAuthorLandLine", "authorLand "+bookAuthorLandLine);
//		Log.e("Book bookAuthorMobile", "authorMobile "+bookAuthorMobile);
//		Log.e("Book bookAuthorWebsite", "authorsite "+bookAuthorWebsite);
//		Log.e("Book bookPublisherName", "publishername "+bookPublisherName);
//		Log.e("Book bookPublisherInfo", "publisherInfo "+bookPublisherInfo);
//		Log.e("Book bookPublisherEmail", "publisherEmail "+bookPublisherEmail);
//		Log.e("Book bookPublisherLandLine", "publisherLand "+bookPublisherLandLine);
//		Log.e("Book bookPublisherMobile", "publishermobile "+bookPublisherMobile);
//		Log.e("Book bookPublisherWebsite", "publishersite "+bookPublisherWebsite);
		Log.e("Book bookNo", "BookNum "+bookNo);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date = new Date();
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_BOOK_NUMBER, bookNo);
		contentValues.put(COLUMN_BOOK_STATUS, bookStatus);
		contentValues.put(COLUMN_BOOK_NAME, bookName);
		contentValues.put(COLUMN_BOOK_IMAGE_PATH, bookImagePath);
		contentValues.put(COLUMN_BOOK_VERSION, bookVersion);
		contentValues.put(COLUMN_AUTHOR_NAME, bookAuthorName);
		contentValues.put(COLUMN_AUTHOR_INFO, bookAuthorInfo);
		contentValues.put(COLUMN_AUTHOR_EMAIL, bookAuthorEmail);
		contentValues.put(COLUMN_AUTHOR_LAND, bookAuthorLandLine);
		contentValues.put(COLUMN_AUTHOR_MOBILE, bookAuthorMobile);
		contentValues.put(COLUMN_AUTHOR_WEBSITE, bookAuthorWebsite);
		contentValues.put(COLUMN_PUBLISHER_NAME, bookPublisherName);
		contentValues.put(COLUMN_PUBLISHER_INFO, bookPublisherInfo);
		contentValues.put(COLUMN_PUBLISHER_EMAIL, bookPublisherEmail);
		contentValues.put(COLUMN_PUBLISHER_LAND, bookPublisherLandLine);
		contentValues.put(COLUMN_PUBLISHER_MOBILE, bookPublisherMobile);
		contentValues.put(COLUMN_PUBLISHER_WEBSITE, bookPublisherWebsite);
		contentValues.put(COLUMN_LINK, bookLink);
		contentValues.put(COLUMN_DATE_TIME, simpleDateFormat.format(date));
		
		long i = sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
		
		Log.e("Book Added Successfully", "Book Added Successfully");
		
		sqLiteDatabase.close();
		return i;
//		sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
	}
	
	public ArrayList<BooksInfoClass> getAllBooksDetails()
	{
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		ArrayList<BooksInfoClass> arrayList = new ArrayList<BooksInfoClass>();
		
		String[] columns = {COLUMN_BOOK_NUMBER, COLUMN_BOOK_STATUS, COLUMN_BOOK_NAME, COLUMN_BOOK_VERSION, COLUMN_BOOK_IMAGE_PATH,
							COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_INFO, COLUMN_AUTHOR_EMAIL, COLUMN_AUTHOR_LAND, COLUMN_AUTHOR_MOBILE,
							COLUMN_AUTHOR_WEBSITE, COLUMN_PUBLISHER_NAME, COLUMN_PUBLISHER_INFO, COLUMN_PUBLISHER_EMAIL,
							COLUMN_PUBLISHER_LAND, COLUMN_PUBLISHER_MOBILE, COLUMN_PUBLISHER_WEBSITE, COLUMN_LINK, COLUMN_DATE_TIME};
		
		
		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);

		while(cursor.moveToNext())
		{
//			Log.e("", "");
//			Log.e("GetDetials Name", "name "+cursor.getString(cursor.getColumnIndex(Books_Database.COLUMN_BOOK_NAME)));
//			Log.e("GetDetials Version", "version "+cursor.getString(cursor.getColumnIndex(Books_Database.COLUMN_BOOK_VERSION)));
//			Log.e("GetDetials Author", "author "+cursor.getString(cursor.getColumnIndex(Books_Database.COLUMN_AUTHOR_NAME)));
//			Log.e("GetDetials Image", "image "+cursor.getString(cursor.getColumnIndex(Books_Database.COLUMN_BOOK_IMAGE_PATH)));
//			Log.e("GetDetials Date", "date "+cursor.getString(cursor.getColumnIndex(Books_Database.COLUMN_DATE_TIME)));
//			Log.e("GetDetials Number", "BookNumber "+cursor.getString(cursor.getColumnIndex(Books_Database.COLUMN_BOOK_NUMBER)));
			
			bookNo = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER));
			bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
			bookVersion = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_VERSION));
			bookStatus = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_STATUS));
			bookAuthorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));
			bookAuthorInfo = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_INFO));
			bookAuthorEmail = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_EMAIL));
			bookAuthorLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_LAND));
			bookAuthorMobile = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_MOBILE));
			bookAuthorWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_WEBSITE));
			bookPublisherName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));
			bookPublisherInfo = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_INFO));
			bookPublisherEmail = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_EMAIL));
			bookPublisherLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_LAND));
			bookPublisherMobile = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_MOBILE));
			bookPublisherWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_WEBSITE));
			bookImagePath = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_PATH));
			bookLink = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
			bookDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
			
			arrayList.add(new BooksInfoClass(bookNo, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink, bookDateTime));
			
		}
		
		sqLiteDatabase.close();
//		return sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
		return arrayList;
	}
	
	public int deleteBook(String bookNumber)
	{
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		
		String where = COLUMN_BOOK_NUMBER+" = ?";
		
		String[] whereArgs = {bookNumber};
		
		int i = sqLiteDatabase.delete(TABLE_NAME, where, whereArgs); 
		
		sqLiteDatabase.close();
		
		return i;
	}
	
	public ArrayList<BooksInfoClass> getBooks_SortByDate()
	{
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		
		ArrayList<BooksInfoClass> arrayList = new ArrayList<BooksInfoClass>();
		
		String[] columns = {COLUMN_BOOK_NUMBER, COLUMN_BOOK_STATUS, COLUMN_BOOK_NAME, COLUMN_BOOK_VERSION, COLUMN_BOOK_IMAGE_PATH,
				COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_INFO, COLUMN_AUTHOR_EMAIL, COLUMN_AUTHOR_LAND, COLUMN_AUTHOR_MOBILE,
				COLUMN_AUTHOR_WEBSITE, COLUMN_PUBLISHER_NAME, COLUMN_PUBLISHER_INFO, COLUMN_PUBLISHER_EMAIL,
				COLUMN_PUBLISHER_LAND, COLUMN_PUBLISHER_MOBILE, COLUMN_PUBLISHER_WEBSITE, COLUMN_LINK, COLUMN_DATE_TIME};
		
		String orderBy = COLUMN_DATE_TIME;
		
		
		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, orderBy);
		
		while(cursor.moveToNext())
		{
			bookNo = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER));
			bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
			bookVersion = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_VERSION));
			bookStatus = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_STATUS));
			bookAuthorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));
			bookAuthorInfo = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_INFO));
			bookAuthorEmail = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_EMAIL));
			bookAuthorLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_LAND));
			bookAuthorMobile = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_MOBILE));
			bookAuthorWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_WEBSITE));
			bookPublisherName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));
			bookPublisherInfo = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_INFO));
			bookPublisherEmail = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_EMAIL));
			bookPublisherLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_LAND));
			bookPublisherMobile = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_MOBILE));
			bookPublisherWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_WEBSITE));
			bookImagePath = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_PATH));
			bookLink = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
			bookDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
			
			arrayList.add(new BooksInfoClass(bookNo, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink, bookDateTime));
		}
		
		sqLiteDatabase.close();
		return arrayList;
	}
	
	public ArrayList<BooksInfoClass> getBooks_SortByBookName()
	{
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		
		ArrayList<BooksInfoClass> arrayList = new ArrayList<BooksInfoClass>();
		
		String[] columns = {COLUMN_BOOK_NUMBER, COLUMN_BOOK_STATUS, COLUMN_BOOK_NAME, COLUMN_BOOK_VERSION, COLUMN_BOOK_IMAGE_PATH,
				COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_INFO, COLUMN_AUTHOR_EMAIL, COLUMN_AUTHOR_LAND, COLUMN_AUTHOR_MOBILE,
				COLUMN_AUTHOR_WEBSITE, COLUMN_PUBLISHER_NAME, COLUMN_PUBLISHER_INFO, COLUMN_PUBLISHER_EMAIL,
				COLUMN_PUBLISHER_LAND, COLUMN_PUBLISHER_MOBILE, COLUMN_PUBLISHER_WEBSITE, COLUMN_LINK, COLUMN_DATE_TIME};
		
		String orderBy = COLUMN_BOOK_NAME;
		
		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, orderBy);
		
		while(cursor.moveToNext())
		{
			bookNo = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER));
			bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
			bookVersion = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_VERSION));
			bookStatus = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_STATUS));
			bookAuthorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));
			bookAuthorInfo = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_INFO));
			bookAuthorEmail = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_EMAIL));
			bookAuthorLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_LAND));
			bookAuthorMobile = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_MOBILE));
			bookAuthorWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_WEBSITE));
			bookPublisherName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));
			bookPublisherInfo = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_INFO));
			bookPublisherEmail = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_EMAIL));
			bookPublisherLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_LAND));
			bookPublisherMobile = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_MOBILE));
			bookPublisherWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_WEBSITE));
			bookImagePath = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_PATH));
			bookLink = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
			bookDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
			
			arrayList.add(new BooksInfoClass(bookNo, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink, bookDateTime));
		}
		
		sqLiteDatabase.close();
		return arrayList;
	}
	
	public ArrayList<BooksInfoClass> getBooks_SortByAuthorName()
	{
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		
		ArrayList<BooksInfoClass> arrayList = new ArrayList<BooksInfoClass>();
		
		String[] columns = {COLUMN_BOOK_NUMBER, COLUMN_BOOK_STATUS, COLUMN_BOOK_NAME, COLUMN_BOOK_VERSION, COLUMN_BOOK_IMAGE_PATH,
				COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_INFO, COLUMN_AUTHOR_EMAIL, COLUMN_AUTHOR_LAND, COLUMN_AUTHOR_MOBILE,
				COLUMN_AUTHOR_WEBSITE, COLUMN_PUBLISHER_NAME, COLUMN_PUBLISHER_INFO, COLUMN_PUBLISHER_EMAIL,
				COLUMN_PUBLISHER_LAND, COLUMN_PUBLISHER_MOBILE, COLUMN_PUBLISHER_WEBSITE, COLUMN_LINK, COLUMN_DATE_TIME};
		
		String orderBy = COLUMN_AUTHOR_NAME;
		
		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, orderBy);
		
		while(cursor.moveToNext())
		{
			bookNo = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER));
			bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
			bookVersion = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_VERSION));
			bookStatus = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_STATUS));
			bookAuthorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));
			bookAuthorInfo = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_INFO));
			bookAuthorEmail = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_EMAIL));
			bookAuthorLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_LAND));
			bookAuthorMobile = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_MOBILE));
			bookAuthorWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_WEBSITE));
			bookPublisherName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));
			bookPublisherInfo = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_INFO));
			bookPublisherEmail = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_EMAIL));
			bookPublisherLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_LAND));
			bookPublisherMobile = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_MOBILE));
			bookPublisherWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_WEBSITE));
			bookImagePath = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_PATH));
			bookLink = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
			bookDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
			
			arrayList.add(new BooksInfoClass(bookNo, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink, bookDateTime));
		}
		
		sqLiteDatabase.close();
		return arrayList;
	}
	
	public ArrayList<BooksInfoClass> getBooks_SortByPublisherName()
	{
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		
		ArrayList<BooksInfoClass> arrayList = new ArrayList<BooksInfoClass>();
		
		String[] columns = {COLUMN_BOOK_NUMBER, COLUMN_BOOK_STATUS, COLUMN_BOOK_NAME, COLUMN_BOOK_VERSION, COLUMN_BOOK_IMAGE_PATH,
				COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_INFO, COLUMN_AUTHOR_EMAIL, COLUMN_AUTHOR_LAND, COLUMN_AUTHOR_MOBILE,
				COLUMN_AUTHOR_WEBSITE, COLUMN_PUBLISHER_NAME, COLUMN_PUBLISHER_INFO, COLUMN_PUBLISHER_EMAIL,
				COLUMN_PUBLISHER_LAND, COLUMN_PUBLISHER_MOBILE, COLUMN_PUBLISHER_WEBSITE, COLUMN_LINK, COLUMN_DATE_TIME};
		
		String orderBy = COLUMN_PUBLISHER_NAME;
		
		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, orderBy);
		
		while(cursor.moveToNext())
		{
			bookNo = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER));
			bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
			bookVersion = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_VERSION));
			bookStatus = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_STATUS));
			bookAuthorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));
			bookAuthorInfo = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_INFO));
			bookAuthorEmail = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_EMAIL));
			bookAuthorLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_LAND));
			bookAuthorMobile = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_MOBILE));
			bookAuthorWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_WEBSITE));
			bookPublisherName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));
			bookPublisherInfo = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_INFO));
			bookPublisherEmail = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_EMAIL));
			bookPublisherLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_LAND));
			bookPublisherMobile = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_MOBILE));
			bookPublisherWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_WEBSITE));
			bookImagePath = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_PATH));
			bookLink = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
			bookDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
			
			arrayList.add(new BooksInfoClass(bookNo, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink, bookDateTime));
		}
		
		sqLiteDatabase.close();
		return arrayList;
	}
	
	public ArrayList<BooksInfoClass> getBooks_FilterByAvailable()
	{
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		
		ArrayList<BooksInfoClass> arrayList = new ArrayList<BooksInfoClass>();
		
		String[] columns = {COLUMN_BOOK_NUMBER, COLUMN_BOOK_STATUS, COLUMN_BOOK_NAME, COLUMN_BOOK_VERSION, COLUMN_BOOK_IMAGE_PATH,
				COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_INFO, COLUMN_AUTHOR_EMAIL, COLUMN_AUTHOR_LAND, COLUMN_AUTHOR_MOBILE,
				COLUMN_AUTHOR_WEBSITE, COLUMN_PUBLISHER_NAME, COLUMN_PUBLISHER_INFO, COLUMN_PUBLISHER_EMAIL,
				COLUMN_PUBLISHER_LAND, COLUMN_PUBLISHER_MOBILE, COLUMN_PUBLISHER_WEBSITE, COLUMN_LINK, COLUMN_DATE_TIME};
		
		String where = COLUMN_BOOK_STATUS+" = ?";
		String[] whereCols = {"Available"};
		
		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, where, whereCols, null, null, null);
		
		while(cursor.moveToNext())
		{
			bookNo = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER));
			bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
			bookVersion = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_VERSION));
			bookStatus = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_STATUS));
			bookAuthorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));
			bookAuthorInfo = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_INFO));
			bookAuthorEmail = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_EMAIL));
			bookAuthorLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_LAND));
			bookAuthorMobile = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_MOBILE));
			bookAuthorWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_WEBSITE));
			bookPublisherName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));
			bookPublisherInfo = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_INFO));
			bookPublisherEmail = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_EMAIL));
			bookPublisherLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_LAND));
			bookPublisherMobile = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_MOBILE));
			bookPublisherWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_WEBSITE));
			bookImagePath = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_PATH));
			bookLink = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
			bookDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
			
			arrayList.add(new BooksInfoClass(bookNo, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink, bookDateTime));
		}
		
		sqLiteDatabase.close();
		return arrayList;
	}
	
	public ArrayList<BooksInfoClass> getBooks_FilterByRequired()
	{
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		
		ArrayList<BooksInfoClass> arrayList = new ArrayList<BooksInfoClass>();
		
		String[] columns = {COLUMN_BOOK_NUMBER, COLUMN_BOOK_STATUS, COLUMN_BOOK_NAME, COLUMN_BOOK_VERSION, COLUMN_BOOK_IMAGE_PATH,
				COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_INFO, COLUMN_AUTHOR_EMAIL, COLUMN_AUTHOR_LAND, COLUMN_AUTHOR_MOBILE,
				COLUMN_AUTHOR_WEBSITE, COLUMN_PUBLISHER_NAME, COLUMN_PUBLISHER_INFO, COLUMN_PUBLISHER_EMAIL,
				COLUMN_PUBLISHER_LAND, COLUMN_PUBLISHER_MOBILE, COLUMN_PUBLISHER_WEBSITE, COLUMN_LINK, COLUMN_DATE_TIME};
		
		String where = COLUMN_BOOK_STATUS+" = ?";
		String[] whereCols = {"Required"};
		
		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, where, whereCols, null, null, null);
		
		while(cursor.moveToNext())
		{
			bookNo = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER));
			bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
			bookVersion = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_VERSION));
			bookStatus = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_STATUS));
			bookAuthorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));
			bookAuthorInfo = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_INFO));
			bookAuthorEmail = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_EMAIL));
			bookAuthorLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_LAND));
			bookAuthorMobile = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_MOBILE));
			bookAuthorWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_WEBSITE));
			bookPublisherName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));
			bookPublisherInfo = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_INFO));
			bookPublisherEmail = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_EMAIL));
			bookPublisherLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_LAND));
			bookPublisherMobile = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_MOBILE));
			bookPublisherWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_WEBSITE));
			bookImagePath = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_PATH));
			bookLink = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
			bookDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
			
			arrayList.add(new BooksInfoClass(bookNo, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink, bookDateTime));
		}
		
		sqLiteDatabase.close();
		return arrayList;
	}
	
	public ArrayList<BooksInfoClass> getBooks_FilterByLinked()
	{
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		
		ArrayList<BooksInfoClass> arrayList = new ArrayList<BooksInfoClass>();
		
		String[] columns = {COLUMN_BOOK_NUMBER, COLUMN_BOOK_STATUS, COLUMN_BOOK_NAME, COLUMN_BOOK_VERSION, COLUMN_BOOK_IMAGE_PATH,
				COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_INFO, COLUMN_AUTHOR_EMAIL, COLUMN_AUTHOR_LAND, COLUMN_AUTHOR_MOBILE,
				COLUMN_AUTHOR_WEBSITE, COLUMN_PUBLISHER_NAME, COLUMN_PUBLISHER_INFO, COLUMN_PUBLISHER_EMAIL,
				COLUMN_PUBLISHER_LAND, COLUMN_PUBLISHER_MOBILE, COLUMN_PUBLISHER_WEBSITE, COLUMN_LINK, COLUMN_DATE_TIME};
		
		String where = COLUMN_BOOK_STATUS+" = ?";
		String[] whereCols = {"Linked"};

		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, where, whereCols, null, null, null);
		
		while(cursor.moveToNext())
		{
			bookNo = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER));
			bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
			bookVersion = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_VERSION));
			bookStatus = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_STATUS));
			bookAuthorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));
			bookAuthorInfo = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_INFO));
			bookAuthorEmail = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_EMAIL));
			bookAuthorLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_LAND));
			bookAuthorMobile = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_MOBILE));
			bookAuthorWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_WEBSITE));
			bookPublisherName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));
			bookPublisherInfo = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_INFO));
			bookPublisherEmail = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_EMAIL));
			bookPublisherLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_LAND));
			bookPublisherMobile = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_MOBILE));
			bookPublisherWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_WEBSITE));
			bookImagePath = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_PATH));
			bookLink = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
			bookDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
			
			arrayList.add(new BooksInfoClass(bookNo, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink, bookDateTime));
		}

		
		sqLiteDatabase.close();
		return arrayList;
	}
	
	public ArrayList<BooksInfoClass> getBooks_FilterByNoFilter()
	{
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		
		ArrayList<BooksInfoClass> arrayList = new ArrayList<BooksInfoClass>();
		
		String[] columns = {COLUMN_BOOK_NUMBER, COLUMN_BOOK_STATUS, COLUMN_BOOK_NAME, COLUMN_BOOK_VERSION, COLUMN_BOOK_IMAGE_PATH,
				COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_INFO, COLUMN_AUTHOR_EMAIL, COLUMN_AUTHOR_LAND, COLUMN_AUTHOR_MOBILE,
				COLUMN_AUTHOR_WEBSITE, COLUMN_PUBLISHER_NAME, COLUMN_PUBLISHER_INFO, COLUMN_PUBLISHER_EMAIL,
				COLUMN_PUBLISHER_LAND, COLUMN_PUBLISHER_MOBILE, COLUMN_PUBLISHER_WEBSITE, COLUMN_LINK, COLUMN_DATE_TIME};
		
//		String where = COLUMN_BOOK_STATUS+" = ?";
//		String[] whereCols = {"Available", "Required", "Linked"};

//		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, where, whereCols, null, null, null);
		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
		
		while(cursor.moveToNext())
		{
			bookNo = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NUMBER));
			bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
			bookVersion = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_VERSION));
			bookStatus = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_STATUS));
			bookAuthorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));
			bookAuthorInfo = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_INFO));
			bookAuthorEmail = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_EMAIL));
			bookAuthorLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_LAND));
			bookAuthorMobile = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_MOBILE));
			bookAuthorWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_WEBSITE));
			bookPublisherName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));
			bookPublisherInfo = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_INFO));
			bookPublisherEmail = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_EMAIL));
			bookPublisherLandLine = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_LAND));
			bookPublisherMobile = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_MOBILE));
			bookPublisherWebsite = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_WEBSITE));
			bookImagePath = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_PATH));
			bookLink = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
			bookDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
			
			arrayList.add(new BooksInfoClass(bookNo, bookStatus, bookName, bookImagePath, bookVersion, bookAuthorName, bookAuthorInfo, bookAuthorEmail, bookAuthorLandLine, bookAuthorMobile, bookAuthorWebsite, bookPublisherName, bookPublisherInfo, bookPublisherEmail, bookPublisherLandLine, bookPublisherMobile, bookPublisherWebsite, bookLink, bookDateTime));
		}

		sqLiteDatabase.close();
		return arrayList;
	}
	
	public ArrayList<String> getAllBookNames()
	{
		ArrayList<String> booksNamesList = new ArrayList<String>();
		
		SQLiteDatabase sqLiteDatabase = getReadableDatabase();
		
		String[] columns = {COLUMN_BOOK_NAME};

		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
		
		while(cursor.moveToNext())
		{
			bookName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
			
			booksNamesList.add(bookName);
		}

		sqLiteDatabase.close();
		return booksNamesList;
	}

}