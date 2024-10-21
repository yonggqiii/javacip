public class gwt_class_32 {
	public static void main(String arg[]) {
		String url = "http: gd.geobytes.com/gd?after=-1&variables=GeobytesCountry,GeobytesCity";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
		// try {
			Request request = builder.sendRequest(null, new A());
		// } catch (RequestException e) {
		// 	e.printStackTrace();
		// }
	}
}
class A implements RequestCallback {

				public void onError(Request request, Throwable exception) {
				}

				public void onResponseReceived(Request request, Response response) {

					System.out.println(response.getText() + "Response");

					if (200 == response.getStatusCode()) {
						Window.alert(response.getText());
					} else {
						Window.alert(response.getText());
					}
				}
}
