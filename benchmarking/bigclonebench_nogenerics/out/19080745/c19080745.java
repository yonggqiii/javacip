class c19080745 {

    public static Image getPluginImage(Object plugin, String name) {
        try {
            URL url = JavaCIPUnknownScope.getPluginImageURL(plugin, name);
            InputStream is = url.openStream();
            Image image;
            try {
                image = JavaCIPUnknownScope.getImage(is);
            } finally {
                is.close();
            }
            return image;
        } catch (RuntimeException e) {
        }
        return null;
    }
}
