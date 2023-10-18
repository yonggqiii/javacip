// added by JavaCIP
public interface URLConnection {

    public abstract int getContentLength();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract OutputStream getOutputStream();
}
