package com.wyc.circleimageview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

	private CircleImageView imgview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		imgview=(CircleImageView)this.findViewById(R.id.img);
		Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.img1);
		imgview.setBitmap(bmp);
		
	}

	

}
