public class gwt_class_14 {

    public void main(String[] arg) {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "http://myweb.com/test.php?id=65472");
        // try {
        rb.sendRequest(null, new A());
        // } catch (final Exception e) {
        Window.alert("");
        // }
    }
}

class A implements RequestCallback {

    public void onError1(final Request request, final Throwable exception) {
        Window.alert(exception.getMessage());
    }

    public void onResponseReceived1(final Request request, final Response response) {
        // do something
        JavaCIPUnknownScope.GWT.log("response: (" + response.getText() + ")", null);
    }

    public void onResponseReceived(Request request, Response response) {
        // TODO Auto-generated method stub
    }

    public void onError(Request request, Throwable exception) {
        // TODO Auto-generated method stub
    }
}
