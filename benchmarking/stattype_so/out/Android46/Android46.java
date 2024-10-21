public class Android46 extends Activity {

    private EditText mTextSearch;

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        JavaCIPUnknownScope.setContentView(R.layout.media_preview);
        mTextSearch = (EditText) JavaCIPUnknownScope.findViewById(R.id.lay);
        Button searchButton = (Button) JavaCIPUnknownScope.findViewById(100);
        searchButton.setOnClickListener(new OnClickListener());
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }
}

class OnClickListener {

    TextSearch mTextSearch = null;

    public void onClick(View v) {
        Intent data = new Intent();
        data.putExtra("TITLE", mTextSearch.getText());
        this.setResult(1, data);
        this.finish();
    }

    public void finish() {
    }

    public void setResult(int x, Intent i) {
    }
}
