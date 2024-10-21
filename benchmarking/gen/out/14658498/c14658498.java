class c14658498 {

    private Properties loadPropertiesFromURL(String propertiesURL, Properties defaultProperties) {
        Properties properties = new Properties(defaultProperties);
        URL url;
        try {
            url = new URL(propertiesURL);
            URLConnection urlConnection = url.openConnection();
            properties.load(urlConnection.getInputStream());
        } catch (MalformedURLRuntimeException e) {
            System.out.println("Error while loading url " + propertiesURL + " (" + e.getClass().getName() + ")");
            e.printStackTrace();
        } catch (IORuntimeException e) {
            System.out.println("Error while loading url " + propertiesURL + " (" + e.getClass().getName() + ")");
            e.printStackTrace();
        }
        return properties;
    }
}
