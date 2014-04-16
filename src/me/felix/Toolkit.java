package me.felix;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Vibrator;
import android.util.Log;

public class Toolkit {

	public static void vibrate(Context context) {
		Vibrator v = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);

		// Vibrate in a Pattern with 500ms on, 300ms off for 5 times
		long[] pattern = { 200, 100 };
		v.vibrate(pattern, 1);
	}

	public static void beep(Context context, int beep) {
		// setup media volume
		AudioManager am = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		int volume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
		// play beep
		try {
			MediaPlayer mp = MediaPlayer.create(context, beep);
			mp.setLooping(false);
			mp.start();
			mp.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer arg0) {
				}
			});
		} catch (Exception e) {
			Log.e("Felix", "Error playing beep sound.");
			e.printStackTrace();
		}
	}

	public static void messageBox(String title, String message,
			Context context, OnClickListener listener) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setPositiveButton(android.R.string.ok, listener)
				.setCancelable(false).create().show();

	}

}
