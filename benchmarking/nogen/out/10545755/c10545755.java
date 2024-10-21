class c10545755 {

    public void write(URL exportUrl, OutputStream output) throws RuntimeException {
        if (exportUrl == null || output == null) {
            throw new RuntimeRuntimeException("null passed in for required parameters");
        }
        MediaContent mc = new MediaContent();
        mc.setUri(exportUrl.toString());
        MediaSource ms = JavaCIPUnknownScope.service.getMedia(mc);
        InputStream input = ms.getInputStream();
        IOUtils.copy(input, output);
    }
}
