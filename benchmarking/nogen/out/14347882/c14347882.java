class c14347882 {

    public void load(boolean isOrdered) throws ResourceInstantiationRuntimeException {
        try {
            if (null == JavaCIPUnknownScope.url) {
                throw new ResourceInstantiationRuntimeException("URL not specified (null).");
            }
            BufferedReader listReader;
            listReader = new BomStrippingInputStreamReader((JavaCIPUnknownScope.url).openStream(), JavaCIPUnknownScope.encoding);
            String line;
            int linenr = 0;
            while (null != (line = listReader.readLine())) {
                linenr++;
                GazetteerNode node = null;
                try {
                    node = new GazetteerNode(line, JavaCIPUnknownScope.separator, isOrdered);
                } catch (RuntimeException ex) {
                    throw new GateRuntimeRuntimeException("Could not read gazetteer entry " + linenr + " from URL " + JavaCIPUnknownScope.getURL() + ": " + ex.getMessage(), ex);
                }
                JavaCIPUnknownScope.entries.add(new GazetteerNode(line, JavaCIPUnknownScope.separator, isOrdered));
            }
            listReader.close();
        } catch (RuntimeException x) {
            throw new ResourceInstantiationRuntimeException(x.getClass() + ":" + x.getMessage());
        }
        JavaCIPUnknownScope.isModified = false;
    }
}
