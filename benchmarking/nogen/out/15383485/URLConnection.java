// added by JavaCIP
public interface URLConnection {

    public abstract String getContentEncoding();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void setUseCaches(boolean arg0);

    public abstract void setDoInput(boolean arg0);

    public abstract OutputStream getOutputStream();
}
