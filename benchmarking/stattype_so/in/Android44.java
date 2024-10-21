public class Android44 extends Service {
	public volatile Thread runner;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		startThread();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		android.util.Log.v("@@@@@@@@@@@@@@@@@@@@", "DoScan.onDestroy");
		stopThread();
	}

	public synchronized void startThread() {
		if (runner == null) {
			android.util.Log.v("@@@@@@@@@@@@@@@@@@@@", "DoScan.startthread");
			runner.start();
		}
	}
	/*
	 * use a handler in a loop cycling through most of oncreate. the
	 * scanningthread does the work, then notifies the svc's uithread
	 */

	public synchronized void stopThread() {
		if (runner != null) {
			android.util.Log.v("@@@@@@@@@@@@@@@@@@@@", "DoScan.stopthread");
			Thread moribund = runner;
			runner = null;
			moribund.interrupt();
			android.util.Log.v("@@@@@@@@@@@@@@@@@@@@", "interrupted?" + moribund.isInterrupted());
		}
	}
}
