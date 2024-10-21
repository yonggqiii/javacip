// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract InputStream getErrorStream();

    public abstract void setRequestProperty(String arg0, Object arg1);

    public abstract int getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void setDoInput(boolean arg0);

    public abstract OutputStream getOutputStream();

    public abstract String getHeaderField(String arg0);
}
