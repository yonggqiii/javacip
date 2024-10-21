@FunctionalInterface
public interface RestTemplateRequestCustomizer<T extends ClientHttpRequest> {

	void customize(T request);

}
