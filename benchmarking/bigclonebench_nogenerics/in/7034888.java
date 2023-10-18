


class c7034888 {

    public void put(IMetaCollection aCollection) throws TransducerRuntimeException {
        if (null != ioTransducer) {
            try {
                URL urlObj = new URL(url);
                URLConnection urlConn = urlObj.openConnection();
                OutputStreamWriter sw = new OutputStreamWriter(urlConn.getOutputStream());
                ioTransducer.setWriter(new BufferedWriter(sw));
                ioTransducer.put(aCollection);
            } catch (RuntimeException e) {
                throw new TransducerRuntimeException(e);
            }
        } else {
            throw new TransducerRuntimeException("An IIOTransducer instance must first be set on the URLTransducerAdapter.");
        }
    }

}
