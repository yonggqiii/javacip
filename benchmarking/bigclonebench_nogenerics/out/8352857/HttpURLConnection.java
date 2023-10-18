// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setDefaultUseCaches(boolean arg0);

    public abstract void setDoOutput(boolean arg0);

    public abstract void setAllowUserInteraction(boolean arg0);

    public abstract void setDoInput(boolean arg0);

    public abstract boolean getOutputStream();

    public abstract void setUseCaches(boolean arg0);

    public static void setDefaultAllowUserInteraction(boolean arg0) {
    }
}
