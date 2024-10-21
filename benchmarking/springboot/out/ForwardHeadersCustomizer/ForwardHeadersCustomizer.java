class ForwardHeadersCustomizer implements JettyServerCustomizer {

    public void customize(Server server) {
        ForwardedRequestCustomizer customizer = new ForwardedRequestCustomizer();
        for (Connector connector : server.getConnectors()) {
            for (ConnectionFactory connectionFactory : connector.getConnectionFactories()) {
                if (connectionFactory instanceof ConnectionFactory) {
                    ConnectionFactory jettyConnectionFactory = (ConnectionFactory) connectionFactory;
                    jettyConnectionFactory.getHttpConfiguration().addCustomizer(customizer);
                }
            }
        }
    }
}
