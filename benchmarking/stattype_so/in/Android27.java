public class Android27 extends BaseAdapter {

	private Context context;
	private String[] texts = { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "eee", "hhh", "iii" };

	public Android27(Context context) {
		this.context = context;
	}

	public int getCount() {
		return 9;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv;
		if (convertView == null) {
			tv = new TextView(context);
			tv.setLayoutParams(new LayoutParams(85, 85));
		} else {
			tv = (TextView) convertView;
		}

		tv.setText(texts[position]);
		return tv;
	}
}
