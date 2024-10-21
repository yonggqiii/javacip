public class gwt_class_31 {
	public static void main(String arg[]) throws RequestException {
		String serverUrl = null;
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, URL.encode(serverUrl)); //-- serverUrl is the url to which this call is posted to.
		requestBuilder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		requestBuilder.setHeader("Expires", "0");
		String postData = null;
		requestBuilder.sendRequest(postData, new A());
	}
}
class A implements RequestCallback {
			public void onError(Request request, Throwable exception) {
				//Do nothing
			}

			public void onResponseReceived(Request request, Response response) {
				//sometimes when the server is restarted, I get response.getStatusCode() = 304 and the response.getText() as blank
			}

}
