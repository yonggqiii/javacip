@FunctionalInterface
public interface WebServerFactoryCustomizer<T extends WebServerFactory> {

	void customize(T factory);

}
