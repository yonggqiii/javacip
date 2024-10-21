import java.io.Closeable;

public interface HttpHandlerFactory {

    HttpHandler getHandler(HttpHandler next);
}
