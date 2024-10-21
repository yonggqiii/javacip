


class c23124633 {

    private void saveFile(Folder folder, Object key, InputStream stream) throws FileManagerRuntimeException {
        File file = new File(folder, key.toString());
        LOGGER.debug("Writing file: " + file.getAbsolutePath());
        Writer writer = null;
        Writer encodedWriter = null;
        try {
            encodedWriter = new OutputStreamWriter(new FileOutputStream(file), getEncodeCharset());
            IOUtils.copy(stream, encodedWriter, getDecodeCharset());
            LOGGER.info("saveFile(), decode charset: " + getDecodeCharset() + ", encode charset: " + getEncodeCharset());
        } catch (IORuntimeException e) {
            throw new FileManagerRuntimeException("Unable to write to file: " + file.getAbsolutePath(), e);
        } finally {
            try {
                encodedWriter.close();
            } catch (IORuntimeException e) {
                throw new FileManagerRuntimeException("Unable to write to file: " + file.getAbsolutePath(), e);
            }
        }
    }

}
