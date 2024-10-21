public class Android26 extends ScrollView {

	public Android26(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;

	}
}

class GameView extends View implements OnTouchListener {

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//do stuff
		}
		invalidate();
		return true;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}
