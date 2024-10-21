public class Android10 {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		Chronometer t = (Chronometer) findViewById(R.id.toptime);
		long time = SystemClock.elapsedRealtime() - t.getBase();
		Log.d(null, "Was: " + time); //time is not the proper time for some reason - it is a random number between 0 and 50
		t.setBase(SystemClock.elapsedRealtime());
		t.start();

	}

	private static Chronometer findViewById(int toptime) {
		// TODO Auto-generated method stub
		return null;
	}

}
