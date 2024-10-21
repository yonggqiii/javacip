class c4381118 {

    private void loadFile(File file) throws RuntimeException {
        Edl edl = new Edl("file:///" + file.getAbsolutePath());
        URL url = ExtractaHelper.retrieveUrl(edl.getUrlRetrievalDescriptor());
        String sUrlString = url.toExternalForm();
        if (sUrlString.startsWith("file:/") && (sUrlString.charAt(6) != '/')) {
            sUrlString = sUrlString.substring(0, 6) + "//" + sUrlString.substring(6);
        }
        Document document = DomHelper.parseHtml(url.openStream());
        JavaCIPUnknownScope.m_inputPanel.setDocument(document);
        JavaCIPUnknownScope.m_resultPanel.setContext(new ResultContext(edl, document, url));
        JavaCIPUnknownScope.initNameCounters(edl.getItemDescriptors());
        JavaCIPUnknownScope.m_outputFile = file;
        JavaCIPUnknownScope.m_sUrlString = sUrlString;
        JavaCIPUnknownScope.m_urlTF.setText(JavaCIPUnknownScope.m_sUrlString);
        JavaCIPUnknownScope.updateHistroy(JavaCIPUnknownScope.m_outputFile);
        JavaCIPUnknownScope.setModified(false);
    }
}
