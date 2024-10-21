import java.util.function.Function;

@FunctionalInterface
public interface NettyServerCustomizer extends Function<HttpServer, HttpServer> {

}
