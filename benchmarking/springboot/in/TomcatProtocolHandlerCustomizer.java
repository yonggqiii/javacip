@FunctionalInterface
public interface TomcatProtocolHandlerCustomizer<T extends ProtocolHandler> {

	void customize(T protocolHandler);

}
