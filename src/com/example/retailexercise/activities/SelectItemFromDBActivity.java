package com.example.retailexercise.activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.retailexercise.R;
import com.example.retailexercise.helper.ItemsSQLiteHelper;
import com.example.retailexercise.model.RetailItem;

public class SelectItemFromDBActivity extends Activity {
	private ImageButton backBtn;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectfromlist);
		backBtn = (ImageButton) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		list = (ListView) findViewById(R.id.lst);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ItemsSQLiteHelper dataDB = new ItemsSQLiteHelper(
				SelectItemFromDBActivity.this);
		final List<RetailItem> allItems = dataDB.selectAllItems();
		String[] itemNames = new String[allItems.size()];
		for (int i = 0; i < allItems.size(); i++) {
			itemNames[i] = allItems.get(i).getName();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				itemNames);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SelectItemFromDBActivity.this, ShowProductsActivity.class);
				i.putExtra("item", allItems.get(position).getId());
				startActivity(i);
			}
		});

	}

}
