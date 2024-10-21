class c17627195 {

    public Document getContentAsDocument() {
        URLConnection connection = JavaCIPUnknownScope.url.openConnection();
        if (JavaCIPUnknownScope.doReload(connection)) {
            InputSource inputSource = new InputSource(connection.getInputStream());
            DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
            JavaCIPUnknownScope.document = factory.newDocumentBuilder().parse(inputSource);
        }
        return JavaCIPUnknownScope.document;
    }
}
