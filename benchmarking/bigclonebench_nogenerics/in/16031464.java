


class c16031464 {

    private static boolean initLOG4JProperties(String homeDir) {
        String Log4jURL = homeDir + LOG4J_URL;
        try {
            URL log4jurl = getURL(Log4jURL);
            InputStream inStreamLog4j = log4jurl.openStream();
            Properties propertiesLog4j = new Properties();
            try {
                propertiesLog4j.load(inStreamLog4j);
                PropertyConfigurator.configure(propertiesLog4j);
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            logger.info("Failed to initialize LOG4J with properties file.");
            return false;
        }
        return true;
    }

}