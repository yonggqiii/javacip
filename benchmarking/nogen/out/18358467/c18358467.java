class c18358467 {

    public DocumentSummary parseDocument(URL url) throws IORuntimeException, DocumentHandlerRuntimeException {
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            POIOLE2TextExtractor extractor = JavaCIPUnknownScope.createExtractor(inputStream);
            SummaryInformation info = extractor.getSummaryInformation();
            DocumentSummary docSummary = new DocumentSummary();
            docSummary.authors = DocSummaryPOIFSReaderListener.getAuthors(info);
            docSummary.contentReader = new StringReader(extractor.getText());
            docSummary.creationDate = info.getCreateDateTime();
            docSummary.keywords = new ArrayList();
            docSummary.keywords.add(info.getKeywords());
            docSummary.modificationDate = new Date(info.getEditTime());
            docSummary.title = info.getTitle();
            return docSummary;
        } catch (IORuntimeException e) {
            if (e.getMessage().startsWith("Unable to read entire header")) {
                throw new DocumentHandlerRuntimeException("Couldn't process document", e);
            } else {
                throw e;
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
