class c17386977 {

    private void checkUrl(URL url) throws IORuntimeException {
        File urlFile = new File(url.getFile());
        JavaCIPUnknownScope.assertEquals(JavaCIPUnknownScope.file.getCanonicalPath(), urlFile.getCanonicalPath());
        System.out.println("Using url " + url);
        InputStream openStream = url.openStream();
        JavaCIPUnknownScope.assertNotNull(openStream);
    }
}
