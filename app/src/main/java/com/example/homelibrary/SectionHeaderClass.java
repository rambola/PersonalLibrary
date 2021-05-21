package com.example.homelibrary;

public class SectionHeaderClass implements Interface_DrawerLayout
{

	String header;
	
	public SectionHeaderClass(String header)
	{
		this.header = header;
	}
	
	public String getHeader()
	{
		return this.header;
	}
	
	@Override
	public boolean isHeader()
	{
		return true;
	}

}
