


class c17627195 {

    public Document getContentAsDocument() {

        URLConnection connection = url.openConnection();
        if (doReload(connection)) {
            InputSource inputSource = new InputSource(connection.getInputStream());
            DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
            document = factory.newDocumentBuilder().parse(inputSource);
        }
        return document;

    }

}
