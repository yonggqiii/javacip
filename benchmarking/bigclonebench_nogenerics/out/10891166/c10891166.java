class c10891166 {

    public static Image getPluginImage(Object plugin, String name) {
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
