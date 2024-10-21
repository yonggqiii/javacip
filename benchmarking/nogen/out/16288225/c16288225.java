class c16288225 {

    public TestReport runImpl() throws RuntimeException {
        DocumentFactory df = new SAXDocumentFactory(GenericDOMImplementation.getDOMImplementation(), JavaCIPUnknownScope.parserClassName);
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
        e.setAttribute(JavaCIPUnknownScope.targetAttribute, JavaCIPUnknownScope.targetValue);
        if (JavaCIPUnknownScope.targetValue.equals(e.getAttribute(JavaCIPUnknownScope.targetAttribute))) {
            return JavaCIPUnknownScope.reportSuccess();
        }
        DefaultTestReport report = new DefaultTestReport(this);
        report.setErrorCode(TestReport.ERROR_TEST_FAILED);
        report.setPassed(false);
        return report;
    }
}
