public class Android24 extends Activity {

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JavaCIPUnknownScope.setContentView(R.layout.main);
        LocationManager l = (LocationManager) JavaCIPUnknownScope.getSystemService(Context.LOCATION_SERVICE);
        List<String> li = l.getAllProviders();
        for (Iterator<String> iterator = li.iterator(); iterator.hasNext(); ) {
            String string = iterator.next();
            Log.d("gps", string);
        }
        if (l.getLastKnownLocation("gps") == null)
            Log.d("gps", "null");
    }
}
