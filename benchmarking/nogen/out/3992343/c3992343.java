class c3992343 {

    public File createTemporaryFile() throws IORuntimeException {
        URL url = JavaCIPUnknownScope.clazz.getResource(JavaCIPUnknownScope.resource);
        if (url == null) {
            throw new IORuntimeException("No resource available from '" + JavaCIPUnknownScope.clazz.getName() + "' for '" + JavaCIPUnknownScope.resource + "'");
        }
        String extension = JavaCIPUnknownScope.getExtension(JavaCIPUnknownScope.resource);
        String prefix = "resource-temporary-file-creator";
        File file = File.createTempFile(prefix, extension);
        InputStream input = url.openConnection().getInputStream();
        FileOutputStream output = new FileOutputStream(file);
        JavaCIPUnknownScope.com.volantis.synergetics.io.IOUtils.copyAndClose(input, output);
        return file;
    }
}
