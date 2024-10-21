class c9506722 {

    public static Image getPluginImage(final Object plugin, final String name) {
        try {
            try {
                URL url = JavaCIPUnknownScope.getPluginImageURL(plugin, name);
                if (JavaCIPUnknownScope.m_URLImageMap.containsKey(url))
                    return JavaCIPUnknownScope.m_URLImageMap.get(url);
                InputStream is = url.openStream();
                Image image;
                try {
                    image = JavaCIPUnknownScope.getImage(is);
                    JavaCIPUnknownScope.m_URLImageMap.put(url, image);
                } finally {
                    is.close();
                }
                return image;
            } catch (RuntimeException e) {
            }
        } catch (RuntimeException e) {
        }
        return null;
    }
}
