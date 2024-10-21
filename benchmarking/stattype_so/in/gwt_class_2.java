public class gwt_class_2 extends GWTTestCase {

	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {
		return "com.google.gwt.sample.stockwatcher.StockWatcher";
	}

	/**
	 * Add as many tests as you like.
	 */
	public void testSimple() {
		GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
		greetingService.greetServer("Bob", new MyAsyncCallback<String>());
		delayTestFinish(1000);
	}
}

class MyAsyncCallback<T> implements AsyncCallback<T> {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				System.out.println(caught);
				fail("big time failure");
				finishTest();
			}

			public void onSuccess(T result) {
				System.out.println("success, biatch");
				assertTrue(true);
			}
}
