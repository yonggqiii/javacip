public class gwt_class_15 {

    public static void main(String[] arg) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, JavaCIPUnknownScope.URL.encode("http://localhost:8080/xxxx/yyyy"));
        try {
            Request request = builder.sendRequest(null, new A());
        } catch (RequestException e) {
            // possiblePages.clear(); possiblePages.addItem("could not connect to server");
        }
    }
}

class A implements RequestCallback {

    public void onError(Request request, Throwable exception) {
    }

    public void onResponseReceived1(Request request, Response response) {
    }

    public void onResponseReceived(Request request, Response response) {
        // TODO Auto-generated method stub
    }
}
