public class Android19 {

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            Context mContext = null;
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new LayoutParams(85, 85));
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            // does this need imageView.onKeyDown(keyCode, event)?
        } else {
            imageView = (ImageView) convertView;
        }
        int[] mThumbIds = null;
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}
