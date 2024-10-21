public interface ConfigurableJettyWebServerFactory extends ConfigurableWebServerFactory {

	void setAcceptors(int acceptors);

	void setThreadPool(ThreadPool threadPool);

	void setSelectors(int selectors);

	void setUseForwardHeaders(boolean useForwardHeaders);

	void addServerCustomizers(JettyServerCustomizer... customizers);

	void setMaxConnections(int maxConnections);

}
