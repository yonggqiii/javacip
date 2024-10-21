class c4381116 {

    private void importUrl() throws ExtractaRuntimeException {
        UITools.changeCursor(UITools.WAIT_CURSOR, this);
        try {
            JavaCIPUnknownScope.m_sUrlString = JavaCIPUnknownScope.m_urlTF.getText();
            URL url = new URL(JavaCIPUnknownScope.m_sUrlString);
            Document document = DomHelper.parseHtml(url.openStream());
            JavaCIPUnknownScope.m_inputPanel.setDocument(document);
            Edl edl = new Edl();
            edl.addUrlDescriptor(new UrlDescriptor(JavaCIPUnknownScope.m_sUrlString));
            JavaCIPUnknownScope.m_resultPanel.setContext(new ResultContext(edl, document, url));
            JavaCIPUnknownScope.setModified(true);
        } catch (IORuntimeException ex) {
            throw new ExtractaRuntimeException("Can not open URL!", ex);
        } finally {
            UITools.changeCursor(UITools.DEFAULT_CURSOR, this);
        }
    }
}
