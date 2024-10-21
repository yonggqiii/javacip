public class gwt_class_8 {

    public void main() {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, "j_spring_security_check");
        requestBuilder.setHeader("Content-Type", "application/x-www-form-urlencoded");
        // -- sending the username and the password in designated fields.
        // Hard coding values for testing reasons:
        requestBuilder.setRequestData("j_username=test1" + "&j_password=abc");
        requestBuilder.setCallback(new A());
        requestBuilder.send();
    }
}

class A implements RequestCallback {

    public void onError1(Request request, Throwable exception) {
        Window.alert("ERROR !!!" + exception.getMessage());
    }

    public void onResponseReceived1(Request request, Response response) {
        if (response.getStatusCode() != Response.SC_UNAUTHORIZED && response.getStatusCode() != Response.SC_OK) {
            onError1(request, new RequestException(response.getStatusText() + ":\n" + response.getText()));
            return;
        }
        if (response.getStatusCode() == Response.SC_UNAUTHORIZED) {
            // This code is never encountered !! :((
            Window.alert("You have entered an incorrect username or password. Please try again.");
        } else {
            String height = 800 + "";
            String width = 600 + "";
            Window.alert("Authorisation succeeded, you may enter....");
            Window.open("MainScreen.html", "Main screen!!", "height=" + height + ",width=" + width + ",scrollbars=yes,resizable=yes,titlebar=no,toolbar=no,status=yes,close=no,left=0,top=0");
        }
    }

    public void onResponseReceived(Request request, Response response) {
        // TODO Auto-generated method stub
    }

    public void onError(Request request, Throwable exception) {
        // TODO Auto-generated method stub
    }
}
