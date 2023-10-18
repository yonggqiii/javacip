class c13547845 {

    public static PortalConfig install(File xml, File dir) throws IORuntimeException, ConfigurationRuntimeException {
        if (!dir.exists()) {
            JavaCIPUnknownScope.log.info("Creating directory {}", dir);
            dir.mkdirs();
        }
        if (!xml.exists()) {
            JavaCIPUnknownScope.log.info("Installing default configuration to {}", xml);
            OutputStream output = new FileOutputStream(xml);
            try {
                InputStream input = ResourceLoader.open("res://" + JavaCIPUnknownScope.PORTAL_CONFIG_XML);
                try {
                    IOUtils.copy(input, output);
                } finally {
                    input.close();
                }
            } finally {
                output.close();
            }
        }
        return JavaCIPUnknownScope.create(xml, dir);
    }
}
