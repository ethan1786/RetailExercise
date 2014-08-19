package com.example.retailexercise.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.retailexercise.model.RetailItem;
import com.example.retailexercise.model.RetailStore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StoreDictSQLiteHelper extends SQLiteOpenHelper {
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "StoresDB";
	// Stores table name
	private static final String TABLE_STORES = "stores";

	// Stores Table Columns names
	private static final String KEY_STORE_ID = "id";
	private static final String KEY_STORE_NAME = "name";
	private static final String KEY_STORE_LOC = "loc";

	private static final String[] STORE_DB_COLUMNS = { KEY_STORE_ID, KEY_STORE_NAME, KEY_STORE_LOC};
		
	public StoreDictSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d("DATABASE", "Create stores db");

		String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_STORES + " ( "
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
				+ "loc TEXT )";
		// create items table
		db.execSQL(CREATE_ITEMS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d("DATABASE", "Upgrade stores db");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORES);

		// create fresh books table
		this.onCreate(db);
	}

	public void insertStore(RetailStore store) {
		Log.d("INSERT_STORE", "Store : " + store.getName());

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STORE_ID, store.getId());
		values.put(KEY_STORE_NAME, store.getName());
		values.put(KEY_STORE_LOC, store.getLoc());

		db.insert(TABLE_STORES, null, values);
		db.close();
	}

	public RetailStore selectStore(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.query(TABLE_STORES, STORE_DB_COLUMNS, "id = ?", new String[] { id
				+ "" }, null, null, null);
		if (c != null)
			c.moveToFirst();
		RetailStore store = new RetailStore(id, c.getString(1), c.getString(2));
		db.close();

		Log.d("SELECT_STORE", "id:" + id + " name:" + c.getString(1));
		return store;
	}

	public List<RetailStore> selectAllStores() {
		ArrayList<RetailStore> stores = new ArrayList<RetailStore>();

		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM " + TABLE_STORES;
		Cursor c = db.rawQuery(query, null);

		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				stores.add(new RetailStore(c.getInt(0), c.getString(1), c.getString(2)));
				c.moveToNext();
			}
		}
		db.close();

		Log.d("SELECT_All", "Find stores: " + stores.size());

		return stores;
	}
	
	public void deleteItStore(RetailStore store){
		deleteItStore(store.getId());
	}
	public void deleteItStore(int id){
		Log.d("DELETE_STORE", "Delete store:" + id);
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STORES, KEY_STORE_ID + " = ?", new String[]{id+""});
		db.close();
	}

	public int updateItem(RetailStore store) {
		Log.d("UPDATE_STORE", "Update store: (id:" + store.getId() + " name:"
				+ store.getName() + ")");

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_STORE_ID, store.getId());
		values.put(KEY_STORE_NAME, store.getName());
		values.put(KEY_STORE_LOC, store.getLoc());

		int i = db.update(TABLE_STORES, values, KEY_STORE_ID + " = ?",
				new String[] { store.getId() + "" });
		db.close();

		return i;
	}
}
