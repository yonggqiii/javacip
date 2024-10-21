public class gwt_class_37 implements EntryPoint {

    String url = "http://localhost/phpmyadmin/tesdb3/datauser.php";

    public void loadData() throws RequestException {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, JavaCIPUnknownScope.URL.encode(url));
        builder.sendRequest(null, new A());
    }

    public void data(JsArray data) {
        Widget w = null;
        RootPanel.get().add(new HTML("my data"));
        RootPanel.get().add(w);
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
    }
}
