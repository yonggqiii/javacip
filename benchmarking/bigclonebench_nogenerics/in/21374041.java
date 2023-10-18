


class c21374041 {

    public void writeToStream(OutputStream out) throws IORuntimeException {
        InputStream result = null;
        if (tempFile != null) {
            InputStream input = new BufferedInputStream(new FileInputStream(tempFile));
            IOUtils.copy(input, out);
            IOUtils.closeQuietly(input);
        } else if (tempBuffer != null) {
            out.write(tempBuffer);
        }
    }

}
