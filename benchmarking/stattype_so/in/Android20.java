public class Android20 extends View {

	private static final int BOARD_X_OFFSET = 10;
	private static final int BOARD_Y_OFFSET = 10;

	public Android20(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private TextView animText;

	private void animText(String text, int color, int xBlocks, int yBlocks) {
		animText.setText(text);
		animText.setTextColor(color);
		animText.setVisibility(View.VISIBLE);
		int xBlockSize = 2;
		final int x = BOARD_X_OFFSET + xBlocks * xBlockSize;
		int yBlockSize = 2;
		final int y = BOARD_Y_OFFSET + yBlocks * yBlockSize;
		final float SCALE_FROM = (float) 0.25;
		final float SCALE_TO = (float) 5.0;
		ScaleAnimation anim = new ScaleAnimation(SCALE_FROM, SCALE_TO, SCALE_FROM, SCALE_TO, x, y);
		anim.setDuration(500);
		animText.setAnimation(anim);
		this.setAnimation(null);
		startAnimation(anim);
	}

}
