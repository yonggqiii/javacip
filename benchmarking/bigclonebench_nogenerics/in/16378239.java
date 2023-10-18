


class c16378239 {

    public static DigitalObjectContent byReference(final InputStream inputStream) {
        try {
            File tempFile = File.createTempFile("tempContent", "tmp");
            tempFile.deleteOnExit();
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copyLarge(inputStream, out);
            out.close();
            return new ImmutableContent(tempFile);
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        throw new IllegalStateRuntimeException("Could not create content for input stream: " + inputStream);
    }

}
