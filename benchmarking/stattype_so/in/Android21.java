public class Android21 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ImageButton b2 = new ImageButton(getApplicationContext());
		b2.setBackgroundResource(R.drawable.img);
		LinearLayout container = (LinearLayout) findViewById(R.id.lay);
		container.addView(b2);
	}

	private static LinearLayout findViewById(int lay) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

}
