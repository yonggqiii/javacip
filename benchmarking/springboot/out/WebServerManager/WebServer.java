// added by JavaCIP
public interface WebServer {

    public abstract void start();

    public abstract void shutDownGracefully(GracefulShutdownCallback arg0);

    public abstract void stop();
}
