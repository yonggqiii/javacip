// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract long getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract Map<String, List<String>> getHeaderFields();

    public abstract void setInstanceFollowRedirects(boolean arg0);

    public abstract boolean getOutputStream();

    public abstract boolean getHeaderField(String arg0);
}
