class c10176678 {

    public void testLoadHttpGzipped() throws RuntimeException {
        String url = JavaCIPUnknownScope.HTTP_GZIPPED;
        LoadingInfo loadingInfo = Utils.openFileObject(JavaCIPUnknownScope.fsManager.resolveFile(url));
        InputStream contentInputStream = loadingInfo.getContentInputStream();
        byte[] actual = IOUtils.toByteArray(contentInputStream);
        byte[] expected = IOUtils.toByteArray(new GZIPInputStream(new URL(url).openStream()));
        JavaCIPUnknownScope.assertEquals(expected.length, actual.length);
    }
}
