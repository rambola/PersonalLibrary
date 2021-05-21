package com.example.homelibrary;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class SearchViewAdapter extends CursorAdapter
{

    private List<String> booksNamesList;

    private TextView text;

    public SearchViewAdapter(Context context, Cursor cursor, List<String> booksNamesList)
    {
        super(context, cursor, false);

        this.booksNamesList = booksNamesList;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        text.setText(booksNamesList.get(cursor.getPosition()));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item, parent, false);

        text = (TextView) view.findViewById(R.id.itemTV);

        return view;
    }

}