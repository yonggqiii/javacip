public class gwt_class_42 implements EntryPoint {

    String url = "http://localhost/phpmyadmin/tesdb3/datauser.php";

    public void loadData() throws RequestException {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, JavaCIPUnknownScope.URL.encode(url));
        builder.sendRequest(null, new A());
    }

    public void onModuleLoad() {
        try {
            loadData();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }
}

class A implements RequestCallback {

    public void onError(Request request, Throwable exception) {
        Window.alert("error " + exception);
    }

    public void onResponseReceived(Request request, Response response) {
        if (200 == response.getStatusCode()) {
            Window.alert("ok -" + response.getText() + "-" + response.getStatusCode());
        } else {
            Window.alert("error2 -" + response.getText() + response.getStatusText() + "-" + response.getStatusCode());
        }
    }
}
