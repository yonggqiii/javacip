class c6613944 {

    public static String installOvalDefinitions(final String xml_location) {
        InputStream in_stream = null;
        try {
            URL url = JavaCIPUnknownScope._toURL(xml_location);
            if (url == null) {
                in_stream = new FileInputStream(xml_location);
            } else {
                in_stream = url.openStream();
            }
        } catch (IOException ex) {
            throw new OvalException(ex);
        }
        Class<OvalDefinitions> type = OvalDefinitions.class;
        OvalDefinitions object = JavaCIPUnknownScope._unmarshalObject(type, in_stream);
        String pid = JavaCIPUnknownScope._getDatastore().save(type, object);
        return pid;
    }
}
