class c17450712 {

    public Map readMap(String filename) throws RuntimeException {
        JavaCIPUnknownScope.xmlPath = filename.substring(0, filename.lastIndexOf(File.separatorChar) + 1);
        String xmlFile = JavaCIPUnknownScope.makeUrl(filename);
        URL url = new URL(xmlFile);
        InputStream is = url.openStream();
        if (filename.endsWith(".gz")) {
            is = new GZIPInputStream(is);
        }
        Map unmarshalledMap = JavaCIPUnknownScope.unmarshal(is);
        unmarshalledMap.setFilename(filename);
        JavaCIPUnknownScope.map = null;
        return unmarshalledMap;
    }
}
