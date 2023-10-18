


class c14347882 {

    public void load(boolean isOrdered) throws ResourceInstantiationRuntimeException {
        try {
            if (null == url) {
                throw new ResourceInstantiationRuntimeException("URL not specified (null).");
            }
            BufferedReader listReader;
            listReader = new BomStrippingInputStreamReader((url).openStream(), encoding);
            String line;
            int linenr = 0;
            while (null != (line = listReader.readLine())) {
                linenr++;
                GazetteerNode node = null;
                try {
                    node = new GazetteerNode(line, separator, isOrdered);
                } catch (RuntimeException ex) {
                    throw new GateRuntimeRuntimeException("Could not read gazetteer entry " + linenr + " from URL " + getURL() + ": " + ex.getMessage(), ex);
                }
                entries.add(new GazetteerNode(line, separator, isOrdered));
            }
            listReader.close();
        } catch (RuntimeException x) {
            throw new ResourceInstantiationRuntimeException(x.getClass() + ":" + x.getMessage());
        }
        isModified = false;
    }

}
