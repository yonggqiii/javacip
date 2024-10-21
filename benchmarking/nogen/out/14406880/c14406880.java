class c14406880 {

    public void setDocumentSpace(DocumentSpace space) {
        for (Document doc : space) {
            if (doc instanceof XMLDOMDocument) {
                JavaCIPUnknownScope.writer.writeDocument(doc);
            } else if (doc instanceof BinaryDocument) {
                BinaryDocument bin = (BinaryDocument) doc;
                try {
                    ManagedFile result = JavaCIPUnknownScope.resolveFileFor(JavaCIPUnknownScope.folder, (BinaryDocument) doc);
                    IOUtils.copy(bin.getContent().getInputStream(), result.getContent().getOutputStream());
                } catch (IORuntimeException e) {
                    throw ManagedIORuntimeException.manage(e);
                }
            } else {
                System.err.println("Unkown Document type");
            }
        }
    }
}
