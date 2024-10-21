


class c13247325 {

    @Override
    public String getPath() {
        InputStream in = null;
        OutputStream out = null;
        File file = null;
        try {
            file = File.createTempFile("java-storage_" + RandomStringUtils.randomAlphanumeric(32), ".tmp");
            file.deleteOnExit();
            out = new FileOutputStream(file);
            in = openStream();
            IOUtils.copy(in, out);
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        if (file != null && file.exists()) {
            return file.getPath();
        }
        return null;
    }

}
