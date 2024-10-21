public class Android23 extends Activity {

    ImageView imView;

    ImageView imViewLine;

    String imageUrl = "http://www.web.com/app/";

    String FileType = ".png";

    int imageNum = 0;

    Random r;

    int count = 0;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JavaCIPUnknownScope.setContentView(R.layout.main);
        r = new Random();
        ListView myListView = (ListView) JavaCIPUnknownScope.findViewById(R.id.lay);
        final ArrayList<String> todoItems = new ArrayList<String>();
        final ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.main, todoItems);
        // Bind the listview to the array adapter
        myListView.setAdapter(aa);
        for (count = 0; count < 2; count++) {
            todoItems.add(0, "TEST");
            downloadFile(imageUrl + Integer.toString(count) + FileType);
            aa.notifyDataSetChanged();
        }
    }

    Bitmap bmImg;

    void downloadFile(String fileUrl) {
        URL myFileUrl = null;
        // try {
        myFileUrl = new URL(fileUrl);
        // } catch (MalformedURLException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // try {
        HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
        conn.setDoInput(true);
        conn.connect();
        int length = conn.getContentLength();
        // java.io.InputStream is = conn.getInputStream();
        InputStream is = conn.getInputStream();
        bmImg = BitmapFactory.decodeStream(is);
        imViewLine.setImageBitmap(bmImg);
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }
}
