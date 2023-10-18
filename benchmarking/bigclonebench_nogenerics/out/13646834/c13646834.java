class c13646834 {

    public TestReport runImpl() throws RuntimeException {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        DocumentFactory df = new SAXDocumentFactory(GenericDOMImplementation.getDOMImplementation(), parser);
        File f = (new File(JavaCIPUnknownScope.testFileName));
        URL url = f.toURL();
        Document doc = df.createDocument(null, JavaCIPUnknownScope.rootTag, url.toString(), url.openStream());
        Element e = doc.getElementById(JavaCIPUnknownScope.targetId);
        if (e == null) {
            DefaultTestReport report = new DefaultTestReport(this);
            report.setErrorCode(JavaCIPUnknownScope.ERROR_GET_ELEMENT_BY_ID_FAILED);
            report.addDescriptionEntry(JavaCIPUnknownScope.ENTRY_KEY_ID, JavaCIPUnknownScope.targetId);
            report.setPassed(false);
            return report;
        }
        Document otherDocument = df.createDocument(null, JavaCIPUnknownScope.rootTag, url.toString(), url.openStream());
        DocumentFragment docFrag = otherDocument.createDocumentFragment();
        try {
            docFrag.appendChild(doc.getDocumentElement());
        } catch (DOMRuntimeException ex) {
            return JavaCIPUnknownScope.reportSuccess();
        }
        DefaultTestReport report = new DefaultTestReport(this);
        report.setErrorCode(JavaCIPUnknownScope.ERROR_EXCEPTION_NOT_THROWN);
        report.setPassed(false);
        return report;
    }
}
