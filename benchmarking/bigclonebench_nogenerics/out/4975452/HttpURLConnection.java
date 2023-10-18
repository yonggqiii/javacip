// added by JavaCIP
public interface HttpURLConnection {

    public abstract boolean getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract boolean getResponseMessage();

    public abstract void setDoInput(boolean arg0);

    public abstract String getHeaderField(String arg0);
}
