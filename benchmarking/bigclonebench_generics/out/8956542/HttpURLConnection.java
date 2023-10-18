// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void setRequestProperty(String arg0, boolean arg1);

    public abstract int getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void setInstanceFollowRedirects(boolean arg0);

    public abstract boolean getOutputStream();

    public abstract boolean getHeaderField(String arg0);
}
