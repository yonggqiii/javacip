class c13260090 {

    public static URL getComponentXmlFileWith(String name) throws Exception {
        List<URL> all = JavaCIPUnknownScope.getComponentXmlFiles();
        for (URL url : all) {
            InputStream stream = null;
            try {
                stream = url.openStream();
                Element root = JavaCIPUnknownScope.XML.getRootElement(stream);
                for (Element elem : (List<Element>) root.elements()) {
                    String ns = elem.getNamespace().getURI();
                    if (name.equals(elem.attributeValue("name"))) {
                        return url;
                    }
                }
            } finally {
                Resources.closeStream(stream);
            }
        }
        return null;
    }
}
