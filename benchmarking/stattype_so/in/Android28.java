public class Android28 extends Application {

	private static Android28 instance;

	public Android28() {
		instance = this;
	}

	public static Context getContext() {
		return instance;
	}

}
