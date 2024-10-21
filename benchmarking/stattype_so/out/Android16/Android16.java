public class Android16 extends SeekBar {

    private int oHeight = 320, oWidth = 29;

    private int oProgress = -1, oOffset = -1;

    private float xPos = -1, yPos = -1;

    private int top = -1, bottom = -1, left = -1, right = -1;

    public Android16(Context context) {
        super(context);
    }

    public Android16(Context context, AttributeSet attrs) {
        super(context, attrs);
        oOffset = this.getThumbOffset();
        oProgress = this.getProgress();
    }

    public Android16(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        oHeight = height;
        this.setMeasuredDimension(oWidth, oHeight);
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldw, oldh);
    }

    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        left = l;
        right = r;
        top = t;
        bottom = b;
    }

    public void onDraw(Canvas c) {
        c.rotate(90);
        c.translate(0, -29);
        super.onDraw(c);
    }

    public boolean onTouchEvent(MotionEvent event) {
        xPos = event.getX();
        yPos = event.getY();
        float progress = (yPos - this.getTop()) / (this.getBottom() - this.getTop());
        oOffset = this.getThumbOffset();
        oProgress = this.getProgress();
        Log.d("offset" + System.nanoTime(), new Integer(oOffset).toString());
        Log.d("progress" + System.nanoTime(), new Integer(oProgress).toString());
        float offset;
        offset = progress * (this.getBottom() - this.getTop());
        this.setThumbOffset((int) offset);
        Log.d("offset_postsetprogress" + System.nanoTime(), new Integer(oOffset).toString());
        Log.d("progress_postsetprogress" + System.nanoTime(), new Integer(oProgress).toString());
        this.setProgress((int) (100 * event.getY() / this.getBottom()));
        return true;
    }
}
