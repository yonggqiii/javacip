public class gwt_class_29 {

    // private static final String SERVER_URL = "http://localhost:8094/data/view/";
    private static final String SERVER_URL = "http://www.google.com/calendar/feeds/developer-calendar@google.com/public/full?alt=json-in-script&callback=insertAgenda&orderby=starttime&max-results=15&singleevents=true&sortorder=ascending&futureevents=true";

    private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " + "connection and try again.";

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        JsonpRequestBuilder requestBuilder = new JsonpRequestBuilder();
        // requestBuilder.setTimeout(10000);
        // requestBuilder.requestObject(SERVER_URL, new Jazz10RequestCallback());
    }
}

class Jazz10RequestCallback implements AsyncCallback<Article> {

    public void onFailure(Throwable caught) {
        Window.alert("Failed to send the message: " + caught.getMessage());
    }

    public void onSuccess(Article result) {
        // TODO Auto-generated method stub
        Window.alert(result.toString());
    }
}
