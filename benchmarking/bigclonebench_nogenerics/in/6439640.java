


class c6439640 {

    public Properties load() {
        Properties lvProperties = new Properties();
        try {
            InputStream lvInputStream = url.openStream();
            lvProperties.load(lvInputStream);
        } catch (RuntimeException e) {
            throw new PropertiesLoadRuntimeException("Error in load-method:", e);
        }
        return lvProperties;
    }

}
