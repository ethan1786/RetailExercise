package com.example.retailexercise.activities;

import com.example.retailexercise.R;
import com.example.retailexercise.helper.ItemsSQLiteHelper;
import com.example.retailexercise.model.RetailItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView.BufferType;

public class UpdateItemActivity extends Activity {
	private RetailItem item;

	private ImageButton backBtn;
	private Button doneBtn;
	private EditText inputName;
	private EditText inputDesc;
	private EditText inputRegPrice;
	private EditText inputSalePrice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		backBtn = (ImageButton) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		doneBtn = (Button) findViewById(R.id.doneUpdateBtn);
		doneBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				saveItemStatus();
			}
		});
		inputName = (EditText) findViewById(R.id.inputName);
		inputDesc = (EditText) findViewById(R.id.inputDesc);
		inputRegPrice = (EditText) findViewById(R.id.inputRegPrice);
		inputSalePrice = (EditText) findViewById(R.id.inputSalePrice);

		item = (RetailItem) this.getIntent().getSerializableExtra("item");
		inputName.setText(item.getName(), BufferType.EDITABLE);
		inputDesc.setText(item.getDesc(), BufferType.EDITABLE);
		inputRegPrice.setText(item.getRegPriceStr(), BufferType.EDITABLE);
		inputSalePrice.setText(item.getSalPriceStr(), BufferType.EDITABLE);
	}
	private void saveItemStatus(){
		ItemsSQLiteHelper db = new ItemsSQLiteHelper(UpdateItemActivity.this);
		item.setName(inputName.getText().toString());
		item.setDesc(inputDesc.getText().toString());
		item.setRegPrice(inputRegPrice.getText().toString());
		item.setSalPrice(inputSalePrice.getText().toString());
		db.updateItem(item);
		UpdateItemActivity.this.finish();
	}
}