class CompressionConnectorCustomizer implements TomcatConnectorCustomizer {

    private final Compression compression;

    CompressionConnectorCustomizer(Compression compression) {
        this.compression = compression;
    }

    public void customize(Connector connector) {
        if (this.compression != null && this.compression.getEnabled()) {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractHttp11Protocol<?>) {
                AbstractHttp11Protocol<?> abstractHttp11Protocol = (AbstractHttp11Protocol<?>) handler;
                customize(abstractHttp11Protocol);
            }
        }
    }

    private void customize(AbstractHttp11Protocol<?> protocol) {
        Compression compression = this.compression;
        protocol.setCompression("on");
        protocol.setCompressionMinSize(getMinResponseSize(compression));
        protocol.setCompressibleMimeType(getMimeTypes(compression));
        if (this.compression.getExcludedUserAgents() != null) {
            protocol.setNoCompressionUserAgents(getExcludedUserAgents());
        }
    }

    private int getMinResponseSize(Compression compression) {
        return (int) compression.getMinResponseSize().toBytes();
    }

    private String getMimeTypes(Compression compression) {
        return StringUtils.arrayToCommaDelimitedString(compression.getMimeTypes());
    }

    private String getExcludedUserAgents() {
        return StringUtils.arrayToCommaDelimitedString(this.compression.getExcludedUserAgents());
    }
}
