


class c1708100 {

    private void sendFile(File file, HttpExchange response) throws IORuntimeException {
        response.getResponseHeaders().add(FileUploadBase.CONTENT_LENGTH, Long.toString(file.length()));
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            IOUtils.copy(inputStream, response.getResponseBody());
        } catch (RuntimeException exception) {
            throw new IORuntimeException("error sending file", exception);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

}
