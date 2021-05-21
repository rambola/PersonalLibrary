package com.example.homelibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class HomeLibrary_MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
//		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		
		setContentView(R.layout.activity_home_library__main);
		
		new Thread(new Runnable()
		{	
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(2000);
					Intent intent = new Intent(HomeLibrary_MainActivity.this, Books_Activity.class);
					startActivity(intent);
//					overridePendingTransition(R.anim.from_home2books_in, R.anim.from_home2books_out);
					overridePendingTransition(R.anim.entering, R.anim.exit);
					
					HomeLibrary_MainActivity.this.finish();
				}
				catch(Exception e)
				{
					Log.e("From HomeLibrary_MainActivity", e.getMessage());
				}
			}
		}).start();		
	}

}