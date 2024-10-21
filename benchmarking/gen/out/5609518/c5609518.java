class c5609518 {

    private void init() {
        synchronized (JavaCIPUnknownScope.cachedProperties) {
            if (JavaCIPUnknownScope.firstTime) {
                try {
                    Enumeration<URL> configFiles;
                    configFiles = JavaCIPUnknownScope.classloader.getResources(JavaCIPUnknownScope.CONFIG_LOCATION);
                    if (configFiles == null) {
                        JavaCIPUnknownScope.logger.info("No configuration file ({}) found in the classpath.", JavaCIPUnknownScope.CONFIG_LOCATION);
                        return;
                    }
                    JavaCIPUnknownScope.firstTime = false;
                    boolean alreadyLoaded = false;
                    while (configFiles.hasMoreElements()) {
                        final URL url = configFiles.nextElement();
                        if (!alreadyLoaded) {
                            final InputStream is = url.openStream();
                            JavaCIPUnknownScope.cachedProperties.load(is);
                            is.close();
                            JavaCIPUnknownScope.logger.info("XmlFieldFactory configuration loaded from the file {}", url);
                        } else {
                            JavaCIPUnknownScope.logger.info("An other XmlFieldFactory configuration file is found in the classpath. This file won't be loaded {}", url);
                        }
                    }
                } catch (IOException e) {
                    JavaCIPUnknownScope.logger.error("An error occur during the XmlFieldFActory initialization", e);
                }
            }
        }
    }
}
