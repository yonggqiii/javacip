public class gwt_class_23 {

    public static void main(String[] arg) {
        final String filename = JavaCIPUnknownScope.UUID.randomUUID() + ".txt";
        RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "http://localhost/upload");
        Object selected;
        // getBody() is plain-text
        rb.setRequestData("aaa");
        rb.setHeader("Content-Type", "application/x-www-form-urlencodeddata");
        rb.setCallback(new A());
        rb.send();
    }
}

class A implements RequestCallback {

    public void onResponseReceived(Request request, Response response) {
    }

    public void onError(Request request, Throwable exception) {
        exception.printStackTrace();
    }
}
