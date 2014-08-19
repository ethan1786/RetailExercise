package com.example.retailexercise.activities;

import com.example.retailexercise.R;
import com.example.retailexercise.helper.ItemsSQLiteHelper;
import com.example.retailexercise.helper.ListItemAdapter;
import com.example.retailexercise.model.RetailItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class ShowProductsActivity extends Activity {
	private RetailItem item;

	private ImageButton backBtn;
	private ImageView icon;
	private ListView idname;
	private ListView info;
	private Button update;
	private Button delete;
	private ItemsSQLiteHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		backBtn = (ImageButton) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		delete = (Button) findViewById(R.id.deleteBtn);
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ItemsSQLiteHelper db = new ItemsSQLiteHelper(ShowProductsActivity.this);
				db.deleteItem(item);
				ShowProductsActivity.this.finish();
			}
		});
		update = (Button) findViewById(R.id.updateBtn);
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShowProductsActivity.this, UpdateItemActivity.class);
				i.putExtra("item", item);
				startActivity(i);
			}
		});
		db = new ItemsSQLiteHelper(ShowProductsActivity.this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		int id = this.getIntent().getIntExtra("item", 0);
		item = db.selectItem(id);
		icon = (ImageView) findViewById(R.id.icon);
		final int resID = this.getResources().getIdentifier(item.getImgName(),
				"drawable", this.getPackageName());
		icon.setImageResource(resID);
		icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showBigIcon(resID);
			}
		});
		
		idname = (ListView) findViewById(R.id.idname);
		String[] content = new String[] { "ID:", item.getId() + "", "Name:",
				item.getName() };
		idname.setAdapter(new ListItemAdapter(ShowProductsActivity.this,
				content));
		idname.setClickable(false);
		
		info = (ListView) findViewById(R.id.info);
		String[] infoContent = new String[] { "Description:",
				item.getDesc() + "", "Regular price:", item.getRegPriceStr(),
				"Sale price:", item.getSalPriceStr(), "Available stores",
				item.getStoreInfo(ShowProductsActivity.this) };
		info.setAdapter(new ListItemAdapter(ShowProductsActivity.this,
				infoContent));
	}

	private void showBigIcon(int resID){
		AlertDialog.Builder builder = new AlertDialog.Builder(ShowProductsActivity.this);
		LayoutInflater inflater = (LayoutInflater)
				ShowProductsActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.showicon_dialog,
		        (ViewGroup) findViewById(R.id.dialogLayout));
		builder.setView(layout);

		final AlertDialog alertDialog = builder.create();

		ImageView bigIcon = (ImageView) layout.findViewById(R.id.bigicton);
		bigIcon.setImageResource(resID);
		bigIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
		});
		alertDialog.show();
	}
}
