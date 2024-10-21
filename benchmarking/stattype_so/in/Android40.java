public class Android40 extends Activity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		SlidingDrawer drawer = (SlidingDrawer) findViewById(R.id.lay);

		ImageView handle = (ImageView) findViewById(R.id.myImage);
		handle.setFocusable(true);

		// handle.setOnClickListener(new OnClickListener() {
		//
		// 	@Override
		// 	public void onClick(View v) {
		// 		// TODO Auto-generated method stub
		// 		Log.i("test", "onClick");
		// 	}
		//
		// });

	}
}
