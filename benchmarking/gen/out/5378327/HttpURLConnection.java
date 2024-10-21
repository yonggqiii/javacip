// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public static void setFollowRedirects(boolean arg0) {
    }

    public abstract void setReadTimeout(int arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract InputStream getInputStream();

    public abstract UNKNOWN_77 getHeaderFields();

    public abstract String getContentType();
}
