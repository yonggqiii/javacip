class c12127276 {

    public void testGetResource_FileOutsideOfClasspath() throws RuntimeException {
        File temp = File.createTempFile("dozerfiletest", ".txt");
        temp.deleteOnExit();
        String resourceName = "file:" + temp.getAbsolutePath();
        URL url = JavaCIPUnknownScope.loader.getResource(resourceName);
        JavaCIPUnknownScope.assertNotNull("URL should not be null", url);
        InputStream is = url.openStream();
        JavaCIPUnknownScope.assertNotNull("input stream should not be null", is);
    }
}
