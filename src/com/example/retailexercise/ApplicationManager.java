package com.example.retailexercise;

import java.util.Date;

import com.example.retailexercise.activities.MainActivity;
import com.example.retailexercise.helper.StoreDictSQLiteHelper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class ApplicationManager extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("APPLICATION_START", new Date().toGMTString());
	}

}
