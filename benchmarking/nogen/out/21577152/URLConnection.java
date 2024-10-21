// added by JavaCIP
public class URLConnection extends HttpURLConnection {

    public boolean getOutputStream() {
        return false;
    }

    URLConnection() {
        super();
    }
}
