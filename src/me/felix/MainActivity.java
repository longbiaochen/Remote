package me.felix;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView msgView;
	ProgressDialog progressDialog;
	String waitText;
	Activity activity;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		context = this;
		final ImageButton iv = (ImageButton) findViewById(R.id.statusImage);
		iv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent m) {
				switch (m.getAction()) {
				case MotionEvent.ACTION_DOWN:
					iv.setImageResource(R.drawable.icon_pressed);
					Toolkit.vibrate(context);
					Messenger.send(Messenger.PLAY_PAUSE, context);
					break;
				case MotionEvent.ACTION_UP:
					iv.setImageResource(R.drawable.icon);
				default:
					break;
				}

				return false;
			}

		});

		// TextView tv = (TextView) findViewById(R.id.statusText);
		// iv.setImageResource(R.drawable.button);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// kill process
			Log.d("Felix", "Back key pressed, will kill the process.");
			android.os.Process.killProcess(android.os.Process.myPid());

		} else if (keyCode == KeyEvent.KEYCODE_MENU
				&& event.getRepeatCount() == 0) {
			Messenger.send(Messenger.CLEAR, context);

		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			Messenger.send(Messenger.VOLUME_UP, context);

		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			Messenger.send(Messenger.VOLUME_DOWN, context);

		}

		return true;

	}

}