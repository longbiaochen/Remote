package me.felix;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class Messenger {
	static String PLAY_PAUSE = "PLAY_PAUSE";
	static String VOLUME_UP = "VOLUME_UP";
	static String VOLUME_DOWN = "VOLUME_DOWN";
	static String CLEAR = "CLEAR";

	static InetAddress address = null;
	static int port = 2777;

	public static void send(String message, Context c) {
		if (null == address) {
			address = getBroadcastAddress(c);
		}
		try {
			DatagramSocket socket = new DatagramSocket();
			socket.setBroadcast(true);

			DatagramPacket packet = new DatagramPacket(message.getBytes(),
					message.length(), address, port);
			socket.send(packet);

		} catch (Exception e) {
			Log.e("Felix", "Socket error!");
			e.printStackTrace();
		}

	}

	private static InetAddress getBroadcastAddress(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		DhcpInfo dhcp = wifi.getDhcpInfo();
		int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++)
			quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);

		InetAddress broadcastAddress = null;
		try {
			broadcastAddress = InetAddress.getByAddress(quads);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return broadcastAddress;
	}
}
