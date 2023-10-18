// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setDoOutput(boolean arg0);

    public abstract void setUseCaches(boolean arg0);

    public abstract void setInstanceFollowRedirects(boolean arg0);

    public abstract void setDoInput(boolean arg0);

    public abstract boolean getOutputStream();

    public abstract String getHeaderField(String arg0);
}
