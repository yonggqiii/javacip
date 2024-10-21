public class Android12 extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout l = new LinearLayout(this);
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		LayoutParams mlp = new LayoutParams(new MarginLayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mlp.setMargins(0, 0, 2, 0);

		for (int i = 0; i < 10; i++) {
			TextView t = new TextView(this);
			t.setText("Hello");
			t.setBackgroundColor(Color.RED);
			t.setSingleLine(true);
			l.addView(t, mlp);
		}

		setContentView(l, lp);
	}
}
