class c7034888 {

    public void put(IMetaCollection aCollection) throws TransducerRuntimeException {
        if (null != JavaCIPUnknownScope.ioTransducer) {
            try {
                URL urlObj = new URL(JavaCIPUnknownScope.url);
                URLConnection urlConn = urlObj.openConnection();
                OutputStreamWriter sw = new OutputStreamWriter(urlConn.getOutputStream());
                JavaCIPUnknownScope.ioTransducer.setWriter(new BufferedWriter(sw));
                JavaCIPUnknownScope.ioTransducer.put(aCollection);
            } catch (RuntimeException e) {
                throw new TransducerRuntimeException(e);
            }
        } else {
            throw new TransducerRuntimeException("An IIOTransducer instance must first be set on the URLTransducerAdapter.");
        }
    }
}
