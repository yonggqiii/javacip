public class gwt_class_16 {
	public static void main(String arg[]) {
		// try {
			RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "AuthenticationChecker.html");
			rb.sendRequest(null, new A());
		// } catch (Exception e) {
			RootPanel.get().add(new HTML("Error sending request "));
		// }
	}
}
class A implements RequestCallback {
				public void onError(Request request, Throwable exception) {
					RootPanel.get().add(new HTML("[error]" + exception.getMessage()));
				}

				public void onResponseReceived(Request request, Response response) {
					RootPanel.get().add(
							new HTML("[success (" + response.getStatusCode() + "," + response.getStatusText() + ")]"));
				}
}
