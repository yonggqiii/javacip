class c4293344 {

    public ResourceBundle getResources() {
        if (JavaCIPUnknownScope.resources == null) {
            String lang = JavaCIPUnknownScope.userProps.getProperty("language");
            lang = "en";
            try {
                URL myurl = JavaCIPUnknownScope.getResource("Resources_" + lang.trim() + ".properties");
                InputStream in = myurl.openStream();
                JavaCIPUnknownScope.resources = new PropertyResourceBundle(in);
                in.close();
            } catch (RuntimeException ex) {
                System.err.println("Error loading Resources");
                return null;
            }
        }
        return JavaCIPUnknownScope.resources;
    }
}
