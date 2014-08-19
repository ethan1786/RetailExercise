package com.example.retailexercise.activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.example.retailexercise.Global;
import com.example.retailexercise.R;
import com.example.retailexercise.helper.ItemsSQLiteHelper;
import com.example.retailexercise.model.RetailItem;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SelectJSONActivity extends Activity{
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

		final AssetManager fileManager = this.getAssets();
		final Gson gson = new Gson();
		final ItemsSQLiteHelper dataDB = new ItemsSQLiteHelper(SelectJSONActivity.this);
		try {
			final String[] allFiles = fileManager.list(Global.ASSETS_SAMPLE_DATA_PATH);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		              android.R.layout.simple_list_item_1, android.R.id.text1, allFiles);
            list.setAdapter(adapter); 
            list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					InputStream is;
					try {
						is = fileManager.open(Global.ASSETS_SAMPLE_DATA_PATH + File.separator + allFiles[position]);
						Reader r = new BufferedReader(new InputStreamReader(is));
						RetailItem item = gson.fromJson(r, RetailItem.class);
						dataDB.insertItem(item);
						Toast.makeText(SelectJSONActivity.this, "Add item: \n" + item.getName(), Toast.LENGTH_LONG).show();
						SelectJSONActivity.this.finish();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
