package com.example.retailexercise.activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.example.retailexercise.Global;
import com.example.retailexercise.R;
import com.example.retailexercise.R.layout;
import com.example.retailexercise.helper.ItemsSQLiteHelper;
import com.example.retailexercise.helper.StoreDictSQLiteHelper;
import com.example.retailexercise.model.RetailItem;
import com.example.retailexercise.model.RetailStore;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity{
	private Button show;
	private Button create;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		show = (Button) findViewById(R.id.showproduct);
		create = (Button) findViewById(R.id.createproduct);
		show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, SelectItemFromDBActivity.class);
				startActivity(i);
			}
		});
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, CreateProductsActivity.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new CheckAsyncTask().execute();
	}

	class CheckAsyncTask extends AsyncTask<Void, Void, Void>{
		ProgressDialog dialog = null;
		ItemsSQLiteHelper dataDB = new ItemsSQLiteHelper(MainActivity.this);

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			AssetManager fileManager = MainActivity.this.getAssets();
			try {
				SharedPreferences sp = MainActivity.this.getSharedPreferences(Global.SP_DATA_KEY, Context.MODE_PRIVATE);				
				int storeVersion  = sp.getInt(Global.KEY_STORENAMES_VERSION, -1);
				if(storeVersion < Global.storeNamesVersion){
					Editor editor = sp.edit();
					editor.putInt(Global.KEY_STORENAMES_VERSION, Global.storeNamesVersion);
					editor.commit();
					String[] allFiles = fileManager.list(Global.ASSETS_DEFAULT_STORES_PATH);
					Gson gson = new Gson();
					InputStream is;
					Reader r;
					StoreDictSQLiteHelper storeDB = new StoreDictSQLiteHelper(MainActivity.this);
					for(String file:allFiles){
						is = fileManager.open(Global.ASSETS_DEFAULT_STORES_PATH + File.separator + file);
						r = new BufferedReader(new InputStreamReader(is));
						RetailStore[] stores = gson.fromJson(r, RetailStore[].class);
						for(RetailStore store:stores){
							storeDB.insertStore(store);
						}
					} 
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(MainActivity.this, "", 
					"Checking store information...");
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(dialog != null)
				dialog.dismiss();
			List<RetailItem> items = dataDB.selectAllItems();
			if(items.size() == 0)
				show.setEnabled(false);
			else
				show.setEnabled(true);
		}

	}
}
