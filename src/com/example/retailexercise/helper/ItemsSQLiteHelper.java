package com.example.retailexercise.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.retailexercise.model.RetailItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ItemsSQLiteHelper extends SQLiteOpenHelper {
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "ItemsDB";
	// Items table name
	private static final String TABLE_ITEMS = "items";

	// Items Table Columns names
	private static final String KEY_ITEM_ID = "id";
	private static final String KEY_ITEM_NAME = "name";
	private static final String KEY_ITEM_DESC = "desc";
	private static final String KEY_ITEM_REGPRICE = "regp";
	private static final String KEY_ITEM_SALEPRICE = "salp";
	private static final String KEY_ITEM_IMG = "img";
	private static final String KEY_ITEM_COLOR = "colo";
	private static final String KEY_ITEM_STORES = "stor";

	private static final String[] ITEMDB_COLUMNS = { KEY_ITEM_ID, KEY_ITEM_NAME, KEY_ITEM_DESC,
			KEY_ITEM_REGPRICE, KEY_ITEM_SALEPRICE, KEY_ITEM_IMG, KEY_ITEM_COLOR, KEY_ITEM_STORES };

	public ItemsSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d("DATABASE", "Create db");

		String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + " ( "
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
				+ "desc TEXT, " + "regp TEXT, " + "salp TEXT, " + "img TEXT, "
				+ "colo TEXT, " + "stor TEXT )";
		// create items table
		db.execSQL(CREATE_ITEMS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d("DATABASE", "Upgrade items db");

		db.execSQL("DROP TABLE IF EXISTS items");

		// create fresh books table
		this.onCreate(db);
	}

	public void insertItem(RetailItem item) {
		Log.d("INSERT_ITEM", item.toString());

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ITEM_ID, item.getId());
		values.put(KEY_ITEM_NAME, item.getName());
		values.put(KEY_ITEM_DESC, item.getDesc());
		values.put(KEY_ITEM_REGPRICE, item.getRegPriceStr());
		values.put(KEY_ITEM_SALEPRICE, item.getSalPriceStr());
		values.put(KEY_ITEM_IMG, item.getImgName());
		values.put(KEY_ITEM_COLOR, item.getColorString());
		values.put(KEY_ITEM_STORES, item.getStoreString());

		db.insert(TABLE_ITEMS, null, values);
		db.close();
	}

	public RetailItem selectItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.query(TABLE_ITEMS, ITEMDB_COLUMNS, "id = ?", new String[] { id
				+ "" }, null, null, null);
		if (c != null)
			c.moveToFirst();
		RetailItem item = new RetailItem(id, c.getString(1));
		item.setDesc(c.getString(2));
		item.setRegPrice(c.getString(3));
		item.setSalPrice(c.getString(4));
		item.setImgName(c.getString(5));
		item.setColor(c.getString(6));
		item.setStore(c.getString(7));
		db.close();

		Log.d("SELECT_ITEM", "id:" + id + " name:" + c.getString(1));
		return item;
	}

	public List<RetailItem> selectAllItems() {
		ArrayList<RetailItem> items = new ArrayList<RetailItem>();

		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM " + TABLE_ITEMS;
		Cursor c = db.rawQuery(query, null);

		RetailItem item = null;
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				item = new RetailItem(c.getInt(0), c.getString(1));
				item.setDesc(c.getString(2));
				item.setRegPrice(c.getString(3));
				item.setSalPrice(c.getString(4));
				item.setImgName(c.getString(5));
				item.setColor(c.getString(6));
				item.setStore(c.getString(7));

				items.add(item);
				c.moveToNext();
			}
		}
		db.close();

		Log.d("SELECT_All", "Find items: " + items.size());

		return items;
	}
	
	public void deleteItem(RetailItem item){
		deleteItem(item.getId());
	}
	public void deleteItem(int id){
		Log.d("DELETE_ITEM", "Delete id:" + id);
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ITEMS, KEY_ITEM_ID + " = ?", new String[]{id+""});
		db.close();
	}

	public int updateItem(RetailItem item) {
		Log.d("UPDATE_ITEM", "Update item: (id:" + item.getId() + " name:"
				+ item.getName() + ")");

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ITEM_ID, item.getId());
		values.put(KEY_ITEM_NAME, item.getName());
		values.put(KEY_ITEM_DESC, item.getDesc());
		values.put(KEY_ITEM_REGPRICE, item.getRegPriceStr());
		values.put(KEY_ITEM_SALEPRICE, item.getSalPriceStr());
		values.put(KEY_ITEM_IMG, item.getImgName());
		values.put(KEY_ITEM_COLOR, item.getColorString());
		values.put(KEY_ITEM_STORES, item.getStoreString());

		int i = db.update(TABLE_ITEMS, values, KEY_ITEM_ID + " = ?",
				new String[] { item.getId() + "" });
		db.close();

		return i;
	}
}
