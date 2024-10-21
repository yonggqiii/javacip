import java.util.function.Function;

@FunctionalInterface
public interface NettyRouteProvider extends Function<HttpServerRoutes, HttpServerRoutes> {

}
