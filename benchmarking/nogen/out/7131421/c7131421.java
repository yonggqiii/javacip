class c7131421 {

    private void getEventHeapsFromWeb() {
        try {
            URL url = JavaCIPUnknownScope.getServersURL();
            InputStream in = url.openStream();
            Document doc = JavaCIPUnknownScope.factory.newDocumentBuilder().parse(in);
            JavaCIPUnknownScope.readFromDocument(doc);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }
}
