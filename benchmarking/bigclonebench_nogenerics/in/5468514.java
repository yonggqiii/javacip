


class c5468514 {

    public static Image getPluginImage(Object plugin, String name) {
        try {
            try {
                URL url = getPluginImageURL(plugin, name);
                if (m_URLImageMap.containsKey(url)) return m_URLImageMap.get(url);
                InputStream is = url.openStream();
                Image image;
                try {
                    image = getImage(is);
                    m_URLImageMap.put(url, image);
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
