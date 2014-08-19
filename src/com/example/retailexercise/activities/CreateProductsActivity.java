package com.example.retailexercise.activities;

import com.example.retailexercise.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class CreateProductsActivity extends Activity{
	private ImageButton backBtn;
	private Button createFromJSon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		backBtn = (ImageButton) findViewById(R.id.backBtn);
		createFromJSon = (Button) findViewById(R.id.createfromjson);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		createFromJSon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(CreateProductsActivity.this, SelectJSONActivity.class);
				startActivity(i);
				CreateProductsActivity.this.finish();
			}
		});
	}

}
