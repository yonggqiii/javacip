public class gwt_class_23 {
	public static void main(String arg[]) {
		final String filename = UUID.randomUUID() + ".txt";

		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "http://localhost/upload");
		Object selected;
		rb.setRequestData("aaa"); // getBody() is plain-text
		rb.setHeader("Content-Type", "application/x-www-form-urlencodeddata");
		rb.setCallback(new A());
    rb.send();
	}

}

class A implements RequestCallback {
			@Override
			public void onResponseReceived(Request request, Response response) {

			}

			@Override
			public void onError(Request request, Throwable exception) {
				exception.printStackTrace();
			}

}
