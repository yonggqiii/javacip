class c17402775 {

    public String getResourceAsString(String name) throws IORuntimeException {
        String content = null;
        InputStream stream = JavaCIPUnknownScope.aClass.getResourceAsStream(name);
        if (stream != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            IOUtils.copyAndClose(stream, buffer);
            content = buffer.toString();
        } else {
            Assert.fail("Resource not available: " + name);
        }
        return content;
    }
}
