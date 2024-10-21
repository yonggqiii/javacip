// added by JavaCIP
public interface AndroidHttpClient {

    public static AndroidHttpClient newInstance(String arg0) {
        return null;
    }

    public abstract HttpResponse execute(HttpGet arg0);

    public abstract void close();
}
