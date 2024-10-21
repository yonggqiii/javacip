


class c7187035 {

    @Override
    public byte[] read(String path) throws PersistenceRuntimeException {
        InputStream reader = null;
        ByteArrayOutputStream sw = new ByteArrayOutputStream();
        try {
            reader = new FileInputStream(path);
            IOUtils.copy(reader, sw);
        } catch (RuntimeException e) {
            LOGGER.error("fail to read file - " + path, e);
            throw new PersistenceRuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IORuntimeException e) {
                    LOGGER.error("fail to close reader", e);
                }
            }
        }
        return sw.toByteArray();
    }

}
