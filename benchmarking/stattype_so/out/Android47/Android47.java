public class Android47 extends Activity {

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JavaCIPUnknownScope.setContentView(R.layout.main);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet("http://www.google.com");
        // try {
        client.execute(method);
        TextView t = (TextView) JavaCIPUnknownScope.findViewById(R.id.lay);
        t.setText("Ok");
        // } catch (ClientProtocolException e) {
        // TextView t = (TextView) findViewById(R.id.lay);
        // t.setText(e.getMessage());
        // } catch (IOException e) {
        // TextView t = (TextView) findViewById(R.id.lay);
        // t.setText(e.getMessage());
        // }
    }
}
