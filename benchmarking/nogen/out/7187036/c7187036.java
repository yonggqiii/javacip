class c7187036 {

    public void write(String path, InputStream is) throws PersistenceRuntimeException {
        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(path), "utf-8");
            IOUtils.copy(is, out);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.LOGGER.error("fail to write file", e);
            throw new PersistenceRuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IORuntimeException e) {
                    out = null;
                }
            }
        }
    }
}
