// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setDoOutput(boolean arg0);

    public abstract void setDoInput(boolean arg0);

    public abstract OutputStream getOutputStream();

    public abstract boolean getRequestProperty(String arg0);

    public abstract boolean getInputStream();
}
