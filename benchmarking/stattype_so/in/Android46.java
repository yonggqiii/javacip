public class Android46 extends Activity {
	private EditText mTextSearch; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	 // TODO Auto-generated method stub
	 super.onCreate(savedInstanceState);  
	 setContentView(R.layout.media_preview); 

	 mTextSearch = (EditText)findViewById(R.id.lay);  
	 Button searchButton = (Button)findViewById(100);  

	 searchButton.setOnClickListener(new OnClickListener());
	} 

	@Override
	public void onSaveInstanceState(Bundle outState){
	 super.onSaveInstanceState(outState);  
	}
	@Override
	public void onPause(){
	 super.onPause();

	}
	@Override
	public void onResume(){
	 super.onResume();  
	}

}

class OnClickListener{
    TextSearch mTextSearch = null;
	  public void onClick(View v){    
	   Intent data = new Intent();    
    
	   data.putExtra("TITLE", mTextSearch.getText());   
	   this.setResult(1, data);
	   this.finish();
	  }
    public void finish() { }
    public void setResult(int x, Intent i) { }
	 }
