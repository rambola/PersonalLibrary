package com.example.homelibrary;

public class Items_SectionHeaderClass implements Interface_DrawerLayout
{

	String header, items_header;
	
	public Items_SectionHeaderClass(String header, String items_header)
	{
		this.header = header;
		this.items_header = items_header;
	}
	
	public String getItems_Header()
	{
		return this.items_header;
	}
	
	@Override
	public boolean isHeader()
	{
		return false;
	}

}
