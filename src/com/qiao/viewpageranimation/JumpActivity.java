package com.qiao.viewpageranimation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JumpActivity extends Activity {
	private Button button_jump;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jump);
		button_jump = (Button) findViewById(R.id.button_jump);
		button_jump.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(JumpActivity.this, MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("flag", false);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
	}



}
