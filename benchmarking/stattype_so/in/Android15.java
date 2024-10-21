public class Android15 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setType("image/jpeg");
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Photo");
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://sdcard/dcim/Camera/filename.jpg"));
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Enjoy the photo");
		startActivity(Intent.createChooser(sendIntent, "Email:"));
	}

	private static void startActivity(Intent createChooser) {
		// TODO Auto-generated method stub

	}

}
