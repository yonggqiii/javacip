// added by JavaCIP
public interface InetAddress {

    public abstract byte[] getAddress();

    public static InetAddress getLocalHost() {
        return null;
    }

    public static InetAddress getByAddress(byte[] arg0) {
        return null;
    }

    public static InetAddress getByName(String arg0) {
        return null;
    }

    public abstract String getHostName();
}
