class c13842042 {

    protected Properties loadFile(String fileName) {
        Properties prop = new Properties();
        try {
            URL url = new File(fileName).toURI().toURL();
            final InputStream input = url.openStream();
            prop.load(input);
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
