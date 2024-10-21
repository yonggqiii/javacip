class c1611515 {

    public static Image getPluginImage(Object plugin, String name) {
        try {
            try {
                URL url = JavaCIPUnknownScope.getPluginImageURL(plugin, name);
                if (JavaCIPUnknownScope.mURLImageMap.containsKey(url)) {
                    return JavaCIPUnknownScope.mURLImageMap.get(url);
                }
                InputStream is = url.openStream();
                Image image;
                try {
                    image = JavaCIPUnknownScope.getImage(is);
                    JavaCIPUnknownScope.mURLImageMap.put(url, image);
                } finally {
                    is.close();
                }
                return image;
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.LOG.debug("Ignore any exceptions");
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.LOG.debug("Ignore any exceptions");
        }
        return null;
    }
}
