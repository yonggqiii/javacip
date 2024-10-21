import java.io.Closeable;

@FunctionalInterface
public interface HttpHandlerFactory {

	HttpHandler getHandler(HttpHandler next);

}
