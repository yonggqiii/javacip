public class Android34 extends TabActivity implements TabContentFactory, OnTabChangeListener, OnClickListener {

	private static final String LOG_KEY = "TEST";
	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final TabHost tabHost = getTabHost();

		TabSpec ts = tabHost.newTabSpec("ID_1");
		ts.setIndicator("1");
		ts.setContent(this);
		tabHost.addTab(ts);

		ts = tabHost.newTabSpec("ID_2");
		ts.setIndicator("2");
		ts.setContent(this);
		tabHost.addTab(ts);

		ts = tabHost.newTabSpec("ID_3");
		ts.setIndicator("3");
		ts.setContent(this);
		tabHost.addTab(ts);
		tabHost.setOnClickListener(this);
		tabHost.setOnTabChangedListener(this);
	}

	public void onClick(View v) {
		Log.d(LOG_KEY, "OnClick");
	}

	public void onTabChanged(String tabId) {
		Log.d(LOG_KEY, "OnTabChanged");
	}

	@Override
	public View createTabContent(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
