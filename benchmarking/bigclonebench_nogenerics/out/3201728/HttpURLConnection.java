// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract InputStream getInputStream();

    public abstract String getHeaderField(String arg0);

    public abstract double getHeaderFieldInt(String arg0, int arg1);
}
