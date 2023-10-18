class c13354644 {

    public void addUrl(URL url) throws IORuntimeException, SAXRuntimeException {
        InputStream inStream = url.openStream();
        String path = url.getPath();
        int slashInx = path.lastIndexOf('/');
        String name = path.substring(slashInx + 1);
        Document doc = JavaCIPUnknownScope.docBuild.parse(inStream);
        Element root = doc.getDocumentElement();
        String rootTag = root.getTagName();
        if (rootTag.equals("graphml")) {
            NodeList graphNodes = root.getElementsByTagName("graph");
            for (int i = 0; i < graphNodes.getLength(); i++) {
                Element elem = (Element) graphNodes.item(i);
                String graphName = elem.getAttribute("id");
                if (graphName == null) {
                    graphName = name;
                }
                JavaCIPUnknownScope.addStructure(new GraphSpec(graphName));
                JavaCIPUnknownScope.urlRefs.put(graphName, url);
            }
        } else if (rootTag.equals("tree")) {
            JavaCIPUnknownScope.addStructure(new TreeSpec(name));
            JavaCIPUnknownScope.urlRefs.put(name, url);
        } else {
            throw new IllegalArgumentRuntimeException("Format of " + url + " not understood.");
        }
        inStream.close();
    }
}
