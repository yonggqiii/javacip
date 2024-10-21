public class Android08 {

	private static final String TAG = "TAG";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// try {
			InetAddress server = Inet4Address.getByName("thehost");
			//Doesn't work either
			//or InetAddress server2 = Inet4Address.getByAddress(new String("192.168.1.30").getBytes());

			if (server.isReachable(5000)) {
				Log.d(TAG, "Ping!");
			}

			Socket clientsocket = new Socket(server, 8080);
		// } catch (UnknownHostException e) {
		// 	Log.e(TAG, "Server Not Found");
		// } catch (IOException e) {
		// 	Log.e(TAG, "Couldn't open socket");
		// }
	}

}
